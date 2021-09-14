package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TransferSumInvalidException extends RuntimeException {
    public TransferSumInvalidException(double sum) {
        super(String.format("Transfer sum %f is not valid", sum));
    }
}
