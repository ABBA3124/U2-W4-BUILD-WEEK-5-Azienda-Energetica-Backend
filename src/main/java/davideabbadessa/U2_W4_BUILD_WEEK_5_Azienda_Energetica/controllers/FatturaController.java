package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Fattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewFatturaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.FatturaService;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fatture")
public class FatturaController {
    @Autowired
    private FatturaService fatturaService;
    @Autowired
    private StatoFatturaService statoFatturaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura saveFattura(@RequestBody @Validated NewFatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return fatturaService.save(body);
    }

    @GetMapping
    public Page<Fattura> getAllFatture(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.fatturaService.getFatture(page, size, sortBy);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFattura(@PathVariable("id") UUID id) {
        fatturaService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable("id") UUID id, @RequestBody @Validated NewFatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return fatturaService.findByIdAndUpdate(id, body);
    }

    @GetMapping("/filter")
    public Page<Fattura> getAllFattureWithFilter(@RequestParam(required = false) String nome,
                                                 @RequestParam(required = false) String statoFattura,
                                                 @RequestParam(required = false) LocalDate dataMin,
                                                 @RequestParam(required = false) LocalDate dataMax,
                                                 @RequestParam(required = false) Integer annoMin,
                                                 @RequestParam(required = false) Integer annoMax,
                                                 @RequestParam(required = false) Double importoMin,
                                                 @RequestParam(required = false) Double importoMax,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy) {
        StatusFattura stato = null;
        if (statoFattura != null) {
            stato = StatusFattura.getStatoFattura(statoFattura);
        }

        return this.fatturaService.trovaTutteLeFattureConFiltri(nome, stato,
                dataMin, dataMax, annoMin, annoMax, importoMin, importoMax, page, size, sortBy);
    }
}
