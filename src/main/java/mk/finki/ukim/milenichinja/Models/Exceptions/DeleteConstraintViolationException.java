package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeleteConstraintViolationException extends DataIntegrityViolationException {
    public DeleteConstraintViolationException(int id) {
        super(String.format("Center with id %d cannot be deleted because there are pets in it", id));
    }
}
