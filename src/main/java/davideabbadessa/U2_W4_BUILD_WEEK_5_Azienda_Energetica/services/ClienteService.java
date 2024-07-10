package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Comune;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Indirizzo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoClienti;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoIndirizzo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewClienteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

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

    public Cliente save(NewClienteDTO body) {
        Comune comuneClienteLegale = comuneService.trovaConNome(body.nomeComuneSedeLegale());
        Indirizzo indirizzoSedeLegale = new Indirizzo(body.viaSedeLegale(), body.civicoSedeLegale(), body.capSedeLegale(), comuneClienteLegale, TipoIndirizzo.SEDE_LEGALE);
        indirizzoSedeLegale.setLocalita(comuneClienteLegale.getProvincia().getNome());
        Comune comuneClienteOperativo = comuneService.trovaConNome(body.nomeComuneSedeOperativa());
        Indirizzo indirizzoOperativo = new Indirizzo(body.viaSedeOperativa(), body.civicoSedeOperativa(), body.capSedeOperativa(), comuneClienteOperativo, TipoIndirizzo.SEDE_OPERATIVA);
        indirizzoOperativo.setLocalita(comuneClienteOperativo.getProvincia().getNome());
        return clienteRepository.save(new Cliente(body.ragioneSociale(),
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
        ));
    }
}
