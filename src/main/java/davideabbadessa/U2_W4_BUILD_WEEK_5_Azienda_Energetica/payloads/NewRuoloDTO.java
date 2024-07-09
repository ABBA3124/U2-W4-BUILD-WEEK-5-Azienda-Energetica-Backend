package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewRuoloDTO(
    @NotEmpty(message = "Il ruolo deve essere un dato obbligatorio")
    String role
) {}
