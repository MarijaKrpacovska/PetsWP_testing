package mk.finki.ukim.milenichinja.Models.Exceptions;

import java.util.function.Supplier;

public class InvalidUserCredentialsException extends RuntimeException {

    public InvalidUserCredentialsException() {
        super("Invalid user credentials exception");
    }
}
