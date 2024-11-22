package src.model;

// TODO: Should we push through with this?
public enum ErrorCodes {
    INCORRECT_DATE(1292, "Invalid date entered"),
    EXISTING_TRANSACTIONS(23, "Could not delete due to existing transactions connected to %s");

    public final int errorCode;
    public final String message;

    private ErrorCodes(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
