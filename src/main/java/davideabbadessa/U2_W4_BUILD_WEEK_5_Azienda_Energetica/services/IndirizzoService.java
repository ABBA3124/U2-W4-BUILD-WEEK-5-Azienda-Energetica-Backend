package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Indirizzo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewIndirizzoDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndirizzoService {
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ComuneService comuneService;

    public Indirizzo save(Indirizzo indirizzo) {
        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo updateIndirizzoLegale(UUID id, NewIndirizzoDTO body) {
        Indirizzo indirizzoPrecedente = findById(id);
        indirizzoPrecedente.setVia(body.via());
        indirizzoPrecedente.setCivico(body.civico());
        indirizzoPrecedente.setLocalita(body.localita());
        indirizzoPrecedente.setCap(body.cap());
        indirizzoPrecedente.setComune(comuneService.trovaConNome(body.comune()));
        return save(indirizzoPrecedente);
    }

    public Indirizzo updateIndirizzoOperativo(UUID id, NewIndirizzoDTO body) {
        Indirizzo indirizzoPrecedente = findById(id);
        indirizzoPrecedente.setVia(body.via());
        indirizzoPrecedente.setCivico(body.civico());
        indirizzoPrecedente.setLocalita(body.localita());
        indirizzoPrecedente.setCap(body.cap());
        indirizzoPrecedente.setComune(comuneService.trovaConNome(body.comune()));
        return save(indirizzoPrecedente);
    }


    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id).orElseThrow(() -> new NotFoundException("Indirizzo non trovato!"));
    }
}
