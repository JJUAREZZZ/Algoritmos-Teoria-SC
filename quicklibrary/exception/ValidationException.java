package quicklibrary.exception;
// faltaba esta excepcion dentro del github
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}