package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private List<ObjectError> errorsList;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<ObjectError> errorsList) {
        super("Errori nella validazione del payload");
        this.errorsList = errorsList;
    }
}
