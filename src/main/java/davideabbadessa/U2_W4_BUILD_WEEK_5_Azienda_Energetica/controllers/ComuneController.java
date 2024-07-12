package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Comune;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewComuneDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comuni")
public class ComuneController {

    @Autowired
    private ComuneService comuneService;

    @GetMapping
    public Page<Comune> trovaTuttiIComuni(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return comuneService.trovaTuttiIComuni(page, size, sortBy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminaComune(@PathVariable("id") UUID id) {
        comuneService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Comune salvaComune(@RequestBody @Validated NewComuneDTO nuovoComunePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return comuneService.saveWithDTO(nuovoComunePayload);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Comune aggiornaComune(@PathVariable("id") UUID id, @RequestBody @Validated NewComuneDTO nuovoComunePayload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return comuneService.aggiornaComune(id, nuovoComunePayload);
    }


}
