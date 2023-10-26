package cn.edu.nbpt.facenet.singin.service.exception;

public class UserFaceDeleteException extends BaseException{
    public UserFaceDeleteException() {
    }

    public UserFaceDeleteException(String message) {
        super(message);
    }

    public UserFaceDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFaceDeleteException(Throwable cause) {
        super(cause);
    }

    public UserFaceDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
