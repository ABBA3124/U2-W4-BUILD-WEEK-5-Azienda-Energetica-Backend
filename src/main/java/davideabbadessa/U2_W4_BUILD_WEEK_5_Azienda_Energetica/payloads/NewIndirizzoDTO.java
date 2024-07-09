package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewIndirizzoDTO(
        @NotEmpty(message = "la via è un dato obbligatorio")
        String via,
        @NotEmpty(message = "Il numero civico è un dato obbligatorio")
        String civico,
        @NotEmpty(message = "La località deve essere obbligatioria")
        String localita,
        @NotEmpty(message = "Il cap deve essere obbligatorio")
        String cap,
        @NotEmpty(message = "Il comune deve essere obbligatorio")
        String comune
) {}
