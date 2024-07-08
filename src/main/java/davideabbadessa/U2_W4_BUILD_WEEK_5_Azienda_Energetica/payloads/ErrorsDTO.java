package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timeStamp) {
}
