package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.AlreadyExistsException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.ResourceNotFoundException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        CommonResponse response = CommonResponse.builder()
                                                .code(HttpStatus.BAD_REQUEST.value())
                                                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                             .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponse> illegalArgumentException(IllegalArgumentException e) {
        CommonResponse response = CommonResponse.builder()
                                                .code(HttpStatus.BAD_REQUEST.value())
                                                .message(e.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                             .body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse> resourceNotFoundException(ResourceNotFoundException e) {

        CommonResponse response = CommonResponse.builder()
                                                .code(HttpStatus.NOT_FOUND.value())
                                                .message(e.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                             .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<CommonResponse> alreadyExistsException(AlreadyExistsException e) {

        CommonResponse response = CommonResponse.builder()
                                                .code(HttpStatus.BAD_REQUEST.value())
                                                .message(e.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                             .body(response);
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<CommonResponse> unauthorizedException(UnauthorizedException e) {

        CommonResponse response = CommonResponse.builder()
                                                .code(HttpStatus.UNAUTHORIZED.value())
                                                .message(e.getMessage())
                                                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                             .body(response);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({PSQLException.class})
    public ResponseEntity<CommonResponse> psqlException(PSQLException ex) {

    	String sqlState = ex.getSQLState(); // SQL 상태 코드
    	String errMsg = "SqlState: "+sqlState+", ErrMsg: "+ex.getMessage();
    	CommonResponse response = CommonResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(errMsg).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler({AccessDeniedException.class})
//    public ResponseEntity<CommonResponse> accessDeniedException(AccessDeniedException e) {
//
//        CommonResponse response = CommonResponse.builder()
//                                                .code(HttpStatus.FORBIDDEN.value())
//                                                .message(e.getMessage())
//                                                .build();
//
//        return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
//                             .body(response);
//    }

}
