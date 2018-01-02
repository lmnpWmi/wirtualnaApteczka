package lmnp.wirtualnaapteczka.exceptions;

/**
 * Exception that is thrown when Medicine fails validation - not all required fields has been specified.
 *
 * @author Sebastian Nowak
 * @createdAt 02.01.2018
 */
public class IncorrectMedicineDataException extends RuntimeException {

    public IncorrectMedicineDataException(String message) {
        super(message);
    }

    public IncorrectMedicineDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectMedicineDataException(Throwable cause) {
        super(cause);
    }
}
