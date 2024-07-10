package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Ruolo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.Role;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewRuoloDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {
    @Autowired
    private RuoloRepository ruoloRepository;

    public Ruolo save(NewRuoloDTO nuovoRuoloDTO) {
        Ruolo nuovoRuolo = new Ruolo(Role.valueOf(nuovoRuoloDTO.role().toUpperCase()));
        return ruoloRepository.save(nuovoRuolo);
    }

    public Ruolo findByRole(Role role) {
        return ruoloRepository.findByRole(role).orElseThrow(() -> new NotFoundException("Ruolo " + role + " non trovato!"));
    }
}
