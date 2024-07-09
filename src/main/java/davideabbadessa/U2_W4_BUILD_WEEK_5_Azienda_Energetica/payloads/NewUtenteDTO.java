package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUtenteDTO(

        @NotEmpty(message = "Username deve essere un dato obbligatorio")
        @Size(min = 3, max = 20, message = "Lo username deve essere compreso tra 3 e 20 caratteri")
        String username,

        @NotEmpty(message = "L'email deve essere un dato obbligatorio")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password deve essere un dato obbligatorio")
        @Size(min = 4, max = 20, message = "La password deve essere compresa tra 4 e 20 caratteri")
        String password,

        @NotEmpty(message = "Il nome deve essere un dato obbligatorio")
        @Size(min = 3, max = 15, message = "Il nome deve essere compreso tra 3 e 15 caratteri")
        String nome,

        @NotEmpty(message = "Il cognome deve essere un dato obbligatorio")
        @Size(min = 3, max = 15, message = "Il cognome deve essere compreso tra 3 e 15 caratteri")
        String cognome
) {
}
