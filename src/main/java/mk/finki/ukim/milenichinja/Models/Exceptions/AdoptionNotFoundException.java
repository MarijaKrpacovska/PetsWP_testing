package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AdoptionNotFoundException extends RuntimeException {
    public AdoptionNotFoundException(int id) {
        super(String.format("Adoption with id: %d is not found", id));
    }
}
