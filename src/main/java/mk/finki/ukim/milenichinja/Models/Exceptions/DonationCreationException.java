package mk.finki.ukim.milenichinja.Models.Exceptions;

public class DonationCreationException extends RuntimeException {
    public DonationCreationException() {
        super("Invalid user credentials exception");
    }
}
