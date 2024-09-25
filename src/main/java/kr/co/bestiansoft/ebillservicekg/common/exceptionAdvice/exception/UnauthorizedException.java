package kr.co.bestiansoft.ebillservicekg.common.exceptionAdvice.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}