package Exceptions;

/**
 *  Exception class for reading files of the wrong format
 */
public class FormatException extends Exception  {

    public FormatException(String errorMessage) {
        super(errorMessage);
    }
}
