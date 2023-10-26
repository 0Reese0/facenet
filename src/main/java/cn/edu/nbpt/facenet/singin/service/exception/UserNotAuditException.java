package cn.edu.nbpt.facenet.singin.service.exception;

public class UserNotAuditException extends BaseException {
    public UserNotAuditException() {
        super();
    }

    public UserNotAuditException(String message) {
        super(message);
    }

    public UserNotAuditException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAuditException(Throwable cause) {
        super(cause);
    }

    protected UserNotAuditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
