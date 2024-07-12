package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.config.MailgunSender;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Ruolo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.Role;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewUtenteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RuoloService ruoloService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailgunSender mailgunSender;

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public Utente save(NewUtenteDTO nuovoUtenteDTO) {
        utenteRepository.findByEmail(nuovoUtenteDTO.email()).ifPresent(u -> {
            throw new BadRequestException("L'utente " + nuovoUtenteDTO.email() + " esiste già!");
        });
        utenteRepository.findByUsername(nuovoUtenteDTO.username()).ifPresent(u -> {
            throw new BadRequestException("L'utente " + nuovoUtenteDTO.username() + " esiste più!");
        });
        Ruolo found = ruoloService.findByRole(Role.USER);
        Utente nuovoUtente = new Utente(nuovoUtenteDTO.username(), nuovoUtenteDTO.email(), passwordEncoder.encode(nuovoUtenteDTO.password()), nuovoUtenteDTO.nome(), nuovoUtenteDTO.cognome());
        if (!nuovoUtente.getRuoli().contains(found)) {
            nuovoUtente.setRuoli(List.of(found));
        } else {
            throw new BadRequestException("L'utente " + nuovoUtenteDTO.nome() + " possiede già questo ruolo");
        }
        utenteRepository.save(nuovoUtente);
        mailgunSender.sendRegistrationEmail(nuovoUtente);
        return nuovoUtente;
    }

    public Page<Utente> getUtenti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente findByIdAndUpdate(UUID utenteId, NewUtenteDTO body) {
        Utente found = this.findById(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(passwordEncoder.encode(body.password()));
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        return this.utenteRepository.save(found);
    }

    public void findByIdAndDelete(UUID utenteId) {
        Utente found = this.findById(utenteId);
        this.utenteRepository.delete(found);
    }

    public Utente uploadImage(UUID utenteId, MultipartFile file) throws IOException {
        Utente found = this.findById(utenteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        return this.utenteRepository.save(found);
    }


}
