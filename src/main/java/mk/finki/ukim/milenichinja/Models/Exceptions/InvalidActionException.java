package mk.finki.ukim.milenichinja.Models.Exceptions;

public class InvalidActionException extends RuntimeException{
    public InvalidActionException() {
        super("That action can not be performed.");
    }

}
