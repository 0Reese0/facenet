package cn.edu.nbpt.facenet.singin.service.exception;

public class UserFaceUpdateException extends BaseException{
    public UserFaceUpdateException() {
    }

    public UserFaceUpdateException(String message) {
        super(message);
    }

    public UserFaceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFaceUpdateException(Throwable cause) {
        super(cause);
    }

    public UserFaceUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
