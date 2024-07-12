package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewUtenteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody NewUtenteDTO body) {
        return this.utenteService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), body);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.getUtenti(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findById(@PathVariable UUID utenteId) {
        return this.utenteService.findById(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated NewUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return this.utenteService.findByIdAndUpdate(utenteId, body);
        }
    }

    @DeleteMapping("/{utenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.findByIdAndDelete(utenteId);
    }

    @PatchMapping("/{utenteId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente uploadAvatarFromAdmin(@PathVariable UUID utenteId, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.utenteService.uploadImage(utenteId, image);
    }

    @PatchMapping("/me/avatar")
    public Utente uploadAvatar(@AuthenticationPrincipal Utente user, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.utenteService.uploadImage(user.getId(), image);
    }
}
