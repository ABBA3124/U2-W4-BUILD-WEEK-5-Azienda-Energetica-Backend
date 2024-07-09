package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewFatturaDTO(
        @NotEmpty(message = "La data della fattura deve essere obbligatoria")
        String data,
        @NotNull
        double importo,
        @NotEmpty(message = "Il numero di fattura deve essere obbligatorio")
        String numero,
        @NotBlank
        String statoFattura,
        @NotBlank
        String cliente
) {}
