package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Comune;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Indirizzo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoClienti;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoIndirizzo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewClienteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private IndirizzoService indirizzoService;

    public Page<Cliente> getClienti(int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return clienteRepository.findAll(pageable);
    }

    public Cliente findByRagioneSociale(String ragioneSociale) {
        return clienteRepository.findByRagioneSociale(ragioneSociale).orElseThrow(() -> new NotFoundException("Cliente non trovato!"));
    }

    public Cliente save(NewClienteDTO body) {
        Comune comuneClienteLegale = comuneService.trovaConNome(body.nomeComuneSedeLegale());
        Indirizzo indirizzoSedeLegale = new Indirizzo(body.viaSedeLegale(), body.civicoSedeLegale(), body.capSedeLegale(), comuneClienteLegale, TipoIndirizzo.SEDE_LEGALE);
        indirizzoSedeLegale.setLocalita(comuneClienteLegale.getProvincia().getNome());
        Comune comuneClienteOperativo = comuneService.trovaConNome(body.nomeComuneSedeOperativa());
        Indirizzo indirizzoOperativo = new Indirizzo(body.viaSedeOperativa(), body.civicoSedeOperativa(), body.capSedeOperativa(), comuneClienteOperativo, TipoIndirizzo.SEDE_OPERATIVA);
        indirizzoOperativo.setLocalita(comuneClienteOperativo.getProvincia().getNome());

        LocalDate dataUltimoContatto = null;
        if (body.dataUltimoContatto() != null && !body.dataUltimoContatto().isEmpty()) {
            try {
                dataUltimoContatto = LocalDate.parse(body.dataUltimoContatto(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data di ultimo contatto non valida");
            }
        }

        Cliente nuovoCliente = new Cliente(body.ragioneSociale(),
                TipoClienti.getTipoClienti(body.tipoCliente().toUpperCase()),
                body.partitaIva(),
                body.fatturatoAnnuale(),
                body.pec(),
                body.telefono(),
                body.emailContatto(),
                body.nomeContatto(),
                body.cognomeContatto(),
                body.telefonoContatto(),
                indirizzoService.save(indirizzoSedeLegale),
                indirizzoService.save(indirizzoOperativo)
        );

        nuovoCliente.setDataUltimoContatto(dataUltimoContatto);


        return clienteRepository.save(nuovoCliente);
    }

    public Page<Cliente> ordinaTuttiIClientiPerProvincia(int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return clienteRepository.findAllByOrderByIndirizzoLegaleLocalitaAsc(pageable);
    }

    public Page<Cliente> trovaTuttiConFiltri(String nome,
                                             Double fatturatoAnnualeMin,
                                             Double fatturatoAnnualeMax,
                                             LocalDate dataInserimentoMin,
                                             LocalDate dataInserimentoMax,
                                             LocalDate dataUltimoContattoMin,
                                             LocalDate dataUltimoContattoMax,
                                             int pageNumber,
                                             int pageSize,
                                             String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = cq.from(Cliente.class);

        List<Predicate> predicates = new ArrayList<>();

        if (fatturatoAnnualeMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(cliente.get("fatturatoAnnuale"), fatturatoAnnualeMin));
        }

        if (fatturatoAnnualeMax != null) {
            predicates.add(cb.lessThanOrEqualTo(cliente.get("fatturatoAnnuale"), fatturatoAnnualeMax));
        }

        if (dataInserimentoMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(cliente.get("dataInserimento"), dataInserimentoMin));
        }

        if (dataInserimentoMax != null) {
            predicates.add(cb.lessThanOrEqualTo(cliente.get("dataInserimento"), dataInserimentoMax));
        }

        if (dataUltimoContattoMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(cliente.get("dataUltimoContatto"), dataUltimoContattoMin));
        }

        if (dataUltimoContattoMax != null) {
            predicates.add(cb.lessThanOrEqualTo(cliente.get("dataUltimoContatto"), dataUltimoContattoMax));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Cliente> query = entityManager.createQuery(cq);
        List<Cliente> resultList = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList, pageable, query.getResultList().size());
    }

    public Cliente updateCliente(UUID id, NewClienteDTO body) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        Comune comuneClienteLegale = comuneService.trovaConNome(body.nomeComuneSedeLegale());
        Indirizzo indirizzoSedeLegale = new Indirizzo(body.viaSedeLegale(), body.civicoSedeLegale(), body.capSedeLegale(), comuneClienteLegale, TipoIndirizzo.SEDE_LEGALE);
        indirizzoSedeLegale.setLocalita(comuneClienteLegale.getProvincia().getNome());
        Comune comuneClienteOperativo = comuneService.trovaConNome(body.nomeComuneSedeOperativa());
        Indirizzo indirizzoOperativo = new Indirizzo(body.viaSedeOperativa(), body.civicoSedeOperativa(), body.capSedeOperativa(), comuneClienteOperativo, TipoIndirizzo.SEDE_OPERATIVA);
        indirizzoOperativo.setLocalita(comuneClienteOperativo.getProvincia().getNome());

        LocalDate dataUltimoContatto = null;
        if (body.dataUltimoContatto() != null && !body.dataUltimoContatto().isEmpty()) {
            try {
                dataUltimoContatto = LocalDate.parse(body.dataUltimoContatto(), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data di ultimo contatto non valida");
            }
        }

        cliente.setRagioneSociale(body.ragioneSociale());
        cliente.setTipoClienti(TipoClienti.getTipoClienti(body.tipoCliente().toUpperCase()));
        cliente.setPartitaIva(body.partitaIva());
        cliente.setFatturatoAnnuale(body.fatturatoAnnuale());
        cliente.setPec(body.pec());
        cliente.setTelefono(body.telefono());
        cliente.setEmailContatto(body.emailContatto());
        cliente.setNomeContatto(body.nomeContatto());
        cliente.setCognomeContatto(body.cognomeContatto());
        cliente.setTelefonoContatto(body.telefonoContatto());
        cliente.setIndirizzoLegale(indirizzoService.save(indirizzoSedeLegale));
        cliente.setIndirizzoOperativo(indirizzoService.save(indirizzoOperativo));
        cliente.setDataUltimoContatto(dataUltimoContatto);

        return clienteRepository.save(cliente);
    }


    public void deleteCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente non trovato");
        }
        clienteRepository.deleteById(id);
    }
}
