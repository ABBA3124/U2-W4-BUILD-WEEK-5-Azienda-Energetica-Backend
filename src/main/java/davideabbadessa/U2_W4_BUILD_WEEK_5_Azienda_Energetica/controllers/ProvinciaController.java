package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NuovaProvinciaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.ProvinciaService;
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
@RequestMapping("/province")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping
    public Page<Provincia> trovaTutteProvince(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return provinciaService.trovaTutteProvince(page, size, sortBy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminaProvincia(@PathVariable("id") UUID id) {
        provinciaService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Provincia salvaProvincia(@RequestBody @Validated NuovaProvinciaDTO nuovaProvincia, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return provinciaService.saveWithDTO(nuovaProvincia);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Provincia aggiornaProvincia(@PathVariable("id") UUID id, @RequestBody @Validated NuovaProvinciaDTO nuovaProvincia, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return provinciaService.aggiornaProvincia(id, nuovaProvincia);
    }


}
