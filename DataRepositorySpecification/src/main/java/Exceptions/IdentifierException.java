package Exceptions;

/**
 *  Exception class for database integrity injury
 */
public class IdentifierException extends Exception  {

    public IdentifierException(String errorMessage) {
        super(errorMessage);
    }
}
