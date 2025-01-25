package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception;

public class MaximumCapacityReachedException extends RuntimeException {
    public MaximumCapacityReachedException(String message) {
        super(message);
    }
}