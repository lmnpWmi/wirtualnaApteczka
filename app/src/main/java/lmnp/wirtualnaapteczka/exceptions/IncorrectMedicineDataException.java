package lmnp.wirtualnaapteczka.exceptions;

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
