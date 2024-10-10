package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}