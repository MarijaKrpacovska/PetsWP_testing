package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PetAlreadyAdoptedException extends RuntimeException{
    public PetAlreadyAdoptedException(int id) {
        super(String.format("Pet with id %d is already adopted. Please choose a different pet.", id));
    }
}
