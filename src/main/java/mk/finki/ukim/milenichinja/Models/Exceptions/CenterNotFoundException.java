package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(int id) {
        super(String.format("Center with id: %d is not found", id));
    }
}
