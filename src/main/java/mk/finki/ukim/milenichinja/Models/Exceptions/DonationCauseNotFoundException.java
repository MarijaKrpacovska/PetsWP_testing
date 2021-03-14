package mk.finki.ukim.milenichinja.Models.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DonationCauseNotFoundException extends RuntimeException {
    public DonationCauseNotFoundException(int id) {
        super(String.format("Donation Cause with id: %d is not found", id));
    }
}
