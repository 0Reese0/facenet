package cn.edu.nbpt.facenet.singin.service.exception;

public class UserFaceAddException extends BaseException{
    public UserFaceAddException() {
    }

    public UserFaceAddException(String message) {
        super(message);
    }

    public UserFaceAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFaceAddException(Throwable cause) {
        super(cause);
    }

    public UserFaceAddException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
