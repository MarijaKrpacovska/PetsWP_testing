package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException(int id) {
        super(String.format("Pet with id: %d is not found", id));
    }
}
