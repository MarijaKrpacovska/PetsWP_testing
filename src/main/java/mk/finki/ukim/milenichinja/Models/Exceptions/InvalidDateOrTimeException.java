package mk.finki.ukim.milenichinja.Models.Exceptions;

public class InvalidDateOrTimeException extends RuntimeException{
    public InvalidDateOrTimeException() {
        super("The date or time you picked is invalid. Please pick a valid one.");
    }

}
