package kr.co.bestiansoft.ebillservicekg.sed_jk.exception;

import org.springframework.http.HttpStatus;

public class ClientServerException extends BaseException {
    public ClientServerException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
