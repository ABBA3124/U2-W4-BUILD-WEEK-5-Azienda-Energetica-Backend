package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import jakarta.validation.constraints.NotBlank;

public record NuovoUtenteDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String nome,
        @NotBlank
        String cognome) {
}
