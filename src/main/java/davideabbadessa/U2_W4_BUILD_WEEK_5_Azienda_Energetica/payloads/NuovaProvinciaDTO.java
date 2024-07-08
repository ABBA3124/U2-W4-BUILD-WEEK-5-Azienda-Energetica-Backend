package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;

public record NuovaProvinciaDTO(
        @NotBlank
        String nome,
        @NotBlank
        String sigla,
        @NotBlank
        String regione
) {
}
