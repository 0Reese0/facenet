package cn.edu.nbpt.facenet.singin.service.exception;

public class PermissionExcep extends BaseException {
    public PermissionExcep() {
        super();
    }

    public PermissionExcep(String message) {
        super(message);
    }

    public PermissionExcep(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionExcep(Throwable cause) {
        super(cause);
    }

    protected PermissionExcep(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
