package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}