package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewClienteDTO(
        @NotEmpty(message = "La ragione sociale deve essere un dato obbligatorio")
        String ragioneSociale,
        @NotBlank
        String tipoCliente,
        @NotEmpty(message = "La partita IVA deve essere un dato obbligatorio")
        String partitaIva,
        String dataUltimoContatto,
        @NotNull()
        double fatturatoAnnuale,
        @NotEmpty(message = "Il pec deve essere obbligatorio")
        String pec,
        @NotEmpty(message = "Il numero di telefono deve essere obbligatorio")
        @Size(min = 10, max = 10, message = "Il numero fornito non è valido")
        String telefono,
        @NotEmpty(message = "L'email deve essere oblbigatoria")
        String emailContatto,
        @NotEmpty(message = "Il nome deve essere un dato obbligatorio")
        @Size(min = 3, max = 15, message = "il numero di caratteri del nome deve essere compreso tra 3 e 15")
        String nomeContatto,
        @NotEmpty(message = "Il cognome deve essere un dato obbligatorio")
        @Size(min = 3, max = 15, message = "il numero di caratteri del cognome deve essere compreso tra 3 e 15")
        String cognomeContatto,
        @NotEmpty(message = "Il numero di telefono deve essere obbligatorio")
        @Size(min = 10, max = 10, message = "Il numero fornito non è valido")
        String telefonoContatto,
        @NotEmpty
        String viaSedeLegale,
        @NotEmpty
        String civicoSedeLegale,
        @NotEmpty
        String capSedeLegale,
        @NotEmpty
        String nomeComuneSedeLegale,
        @NotEmpty
        String viaSedeOperativa,
        @NotEmpty
        String civicoSedeOperativa,
        @NotEmpty
        String capSedeOperativa,
        @NotEmpty
        String nomeComuneSedeOperativa
) {
}
