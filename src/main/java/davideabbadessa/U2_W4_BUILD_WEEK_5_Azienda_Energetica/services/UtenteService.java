package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewUtenteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public Utente saveUtente(NewUtenteDTO nuovoUtenteDTO) {
        if (utenteRepository.findByEmail(nuovoUtenteDTO.email()).isEmpty()) {
            Utente nuovoUtente = new Utente(nuovoUtenteDTO.username(), nuovoUtenteDTO.email(), passwordEncoder.encode(nuovoUtenteDTO.password()), nuovoUtenteDTO.nome(), nuovoUtenteDTO.cognome());
            nuovoUtente.setAvatar("https://ui-avatars.com/api/?name=" + nuovoUtenteDTO.nome() + "+" + nuovoUtenteDTO.cognome());
            return utenteRepository.save(nuovoUtente);
        } else {
            throw new BadRequestException(" Email già in uso! ");
        }

    }
}
