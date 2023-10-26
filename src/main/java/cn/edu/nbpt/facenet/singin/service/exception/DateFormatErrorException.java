package cn.edu.nbpt.facenet.singin.service.exception;

public class DateFormatErrorException extends BaseException {
    public DateFormatErrorException() {
        super();
    }

    public DateFormatErrorException(String message) {
        super(message);
    }

    public DateFormatErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateFormatErrorException(Throwable cause) {
        super(cause);
    }

    protected DateFormatErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
