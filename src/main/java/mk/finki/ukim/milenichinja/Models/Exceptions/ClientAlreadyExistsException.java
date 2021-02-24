package mk.finki.ukim.milenichinja.Models.Exceptions;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException(String embg) {
        super(String.format("User with embg: %s already exists", embg));
    }
}
