package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewUtenteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteLoginDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteLoginResponseDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.UtenteResponseDTO;
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


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteResponseDTO creaUtente(@Validated @RequestBody NewUtenteDTO nuovoUtenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new UtenteResponseDTO(this.utenteService.saveUtente(nuovoUtenteDTO).getId());
    }

    @PostMapping("/login")
    public UtenteLoginResponseDTO login(@RequestBody @Validated UtenteLoginDTO utenteLoginDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return new UtenteLoginResponseDTO(authService.authenticateUtenteAndGenerateToken(utenteLoginDTO));
    }
}
