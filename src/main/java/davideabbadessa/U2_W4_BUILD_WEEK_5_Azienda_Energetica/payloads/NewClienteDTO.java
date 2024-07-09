package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record NewClienteDTO(
        @NotEmpty(message = "La ragione sociale deve essere un dato obbligatorio")
        String ragioneSociale,
        @NotEmpty(message = "La partita IVA deve essere un dato obbligatorio")
        String partitaIva,
        @NotEmpty(message = "la data di inserimento deve essere obbligatoria")
        String dataInserimento,
        @NotEmpty(message = "la data dell'ultimo contatto deve essere obbligatoria")
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
        String telefonoContatto
) {}
