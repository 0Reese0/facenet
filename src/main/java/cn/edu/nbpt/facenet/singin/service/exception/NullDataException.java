package cn.edu.nbpt.facenet.singin.service.exception;

public class NullDataException extends BaseException {
    public NullDataException() {
        super();
    }

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullDataException(Throwable cause) {
        super(cause);
    }

    protected NullDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
