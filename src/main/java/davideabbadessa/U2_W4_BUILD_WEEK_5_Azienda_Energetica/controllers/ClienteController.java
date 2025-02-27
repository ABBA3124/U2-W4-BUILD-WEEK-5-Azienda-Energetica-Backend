package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.controllers;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Utente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.BadRequestException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewClienteDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewEmailDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewIndirizzoDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.clienteService.getClienti(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente saveCliente(@RequestBody @Validated NewClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return clienteService.save(body);
    }

    @GetMapping("/order-by-localita")
    public Page<Cliente> getAllClientiOrderByLocalita(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.clienteService.ordinaTuttiIClientiPerProvincia(page, size, sortBy);
    }

    @GetMapping("/filter")
    public Page<Cliente> getAllClientiWithFilter(@RequestParam(required = false) String nome,
                                                 @RequestParam(required = false) Double fatturatoAnnualeMin,
                                                 @RequestParam(required = false) Double fatturatoAnnualeMax,
                                                 @RequestParam(required = false) LocalDate dataInserimentoMin,
                                                 @RequestParam(required = false) LocalDate dataInserimentoMax,
                                                 @RequestParam(required = false) LocalDate dataUltimoContattoMin,
                                                 @RequestParam(required = false) LocalDate dataUltimoContattoMax,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy) {
        return this.clienteService.trovaTuttiConFiltri(nome, fatturatoAnnualeMin,
                fatturatoAnnualeMax, dataInserimentoMin, dataInserimentoMax, dataUltimoContattoMin, dataUltimoContattoMax, page, size, sortBy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente updateCliente(@PathVariable UUID id, @RequestBody @Validated NewClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
        return clienteService.updateCliente(id, body);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCliente(@PathVariable UUID id) {
        clienteService.deleteCliente(id);
    }

    @PostMapping("/{id}/email")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void sendEmail(@PathVariable UUID id, @AuthenticationPrincipal Utente utente, @RequestBody NewEmailDTO body) {
        clienteService.sendEmail(id, utente, body);
    }

    @PatchMapping("/{id}/indirizzoLegale")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente updateIndirizzoLegale(@PathVariable UUID id, @RequestBody NewIndirizzoDTO indirizzo) {
        return clienteService.updateIndirizzoLegale(id, indirizzo);
    }

    @PatchMapping("/{id}/indirizzoOperativo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente updateIndirizzoOperativo(@PathVariable UUID id, @RequestBody NewIndirizzoDTO indirizzo) {
        return clienteService.updateIndirizzoOperativo(id, indirizzo);
    }

}
