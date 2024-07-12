package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewEmailDTO(
        @NotBlank
        String oggetto,
        @NotBlank
        String testo) {
}
