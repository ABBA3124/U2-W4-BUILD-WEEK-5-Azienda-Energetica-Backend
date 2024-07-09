package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NuovoUtenteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteLoginDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteLoginResponseDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.AuthService;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody @Validated UtenteLoginDTO utenteLoginDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return new UtenteLoginResponseDTO(authService.authenticateUserAndGenerateToken(utenteLoginDTO));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated NuovoUtenteDTO nuovoUtenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return utenteService.save(nuovoUtenteDTO);
    }
}
