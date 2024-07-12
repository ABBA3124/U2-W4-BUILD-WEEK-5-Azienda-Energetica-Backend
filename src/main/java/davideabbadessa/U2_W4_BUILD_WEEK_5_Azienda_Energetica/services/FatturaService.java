package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Fattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.StatoFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewFatturaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.FatturaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private StatoFatturaService statoFatturaService;
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Fattura> getFatture(int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return fatturaRepository.findAll(pageable);
    }

    public Fattura save(NewFatturaDTO body) {
        Cliente clienteFattura = clienteService.findByRagioneSociale(body.cliente());
        StatoFattura statoFattura = statoFatturaService.findByStatus(StatusFattura.getStatoFattura(body.statoFattura()));
        Fattura nuovaFattura = new Fattura(LocalDate.parse(body.data()), body.importo(), clienteFattura, statoFattura);
        nuovaFattura.setNumero(fatturaRepository.findAll().size() + 1);
        return fatturaRepository.save(nuovaFattura);
    }

    public Page<Fattura> trovaTutteLeFattureConFiltri(String nome,
                                                      StatusFattura statoFattura,
                                                      LocalDate dataMin,
                                                      LocalDate dataMax,
                                                      Integer annoMin,
                                                      Integer annoMax,
                                                      Double importoMin,
                                                      Double importoMax,
                                                      int pageNumber,
                                                      int pageSize,
                                                      String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fattura> cq = cb.createQuery(Fattura.class);
        Root<Fattura> fattura = cq.from(Fattura.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nome != null && !nome.isEmpty()) {
            Join<Fattura, Cliente> cliente = fattura.join("cliente");
            predicates.add(cb.like(cliente.get("ragioneSociale"), "%" + nome + "%"));
        }

        if (statoFattura != null) {
            Join<Fattura, StatoFattura> stato = fattura.join("statoFattura");
            predicates.add(cb.equal(stato.get("stato"), statoFattura));
        }

        if (dataMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(fattura.get("data"), dataMin));
        }

        if (dataMax != null) {
            predicates.add(cb.lessThanOrEqualTo(fattura.get("data"), dataMax));
        }

        if (annoMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(cb.function("year", Integer.class, fattura.get("data")), annoMin));
        }

        if (annoMax != null) {
            predicates.add(cb.lessThanOrEqualTo(cb.function("year", Integer.class, fattura.get("data")), annoMax));
        }

        if (importoMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(fattura.get("importo"), importoMin));
        }

        if (importoMax != null) {
            predicates.add(cb.lessThanOrEqualTo(fattura.get("importo"), importoMax));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Fattura> query = entityManager.createQuery(cq);
        List<Fattura> resultList = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList, pageable, query.getResultList().size());
    }


    public void deleteById(UUID id) {
        fatturaRepository.deleteById(id);
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException("Fattura non trovata!"));
    }


    public Fattura findByNumero(int numero) {
        return fatturaRepository.findByNumero(numero).orElseThrow(() -> new NotFoundException("Fattura non trovata!"));
    }

    public Fattura findByIdAndUpdate(UUID id, NewFatturaDTO body) {
        Fattura found = this.findById(id);
        found.setData(LocalDate.parse(body.data()));
        found.setImporto(body.importo());
        found.setStatoFattura(statoFatturaService.findByStatus(StatusFattura.getStatoFattura(body.statoFattura())));
        return fatturaRepository.save(found);
    }
}
