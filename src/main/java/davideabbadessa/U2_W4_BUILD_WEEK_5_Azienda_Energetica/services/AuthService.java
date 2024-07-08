package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.UnauthorizedException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteLoginDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.security.JWTTokenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTokenConfiguration jwtTokenConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndGenerateToken(UtenteLoginDTO body) {
        Utente dipendente = this.utenteService.findByEmail(body.email());
        if (passwordEncoder.matches(body.password(), dipendente.getPassword())) {
            return jwtTokenConfiguration.createToken(dipendente);
        } else {
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
