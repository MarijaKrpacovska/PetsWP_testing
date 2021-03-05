package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ValuteNotFoundException extends RuntimeException {
    public ValuteNotFoundException(String val) {
        super(String.format("Valute %s is not found", val));
    }
}
