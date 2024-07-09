package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewStatoFatturaDTO(
        @NotEmpty(message = "Lo stato della fattura è un dato obbligatorio")
        String stato
) {}
