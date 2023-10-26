package cn.edu.nbpt.facenet.singin.service.exception;

public class CheckFaceExcep extends BaseException {
    public CheckFaceExcep() {
        super();
    }

    public CheckFaceExcep(String message) {
        super(message);
    }

    public CheckFaceExcep(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckFaceExcep(Throwable cause) {
        super(cause);
    }

    protected CheckFaceExcep(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
