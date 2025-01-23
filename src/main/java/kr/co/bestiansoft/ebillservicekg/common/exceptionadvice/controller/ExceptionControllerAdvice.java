package kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.bestiansoft.ebillservicekg.common.errLog.service.ErrLogService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.AlreadyExistsException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.ForbiddenException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.ResourceNotFoundException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	private ErrLogService errLogService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

    	int errCd = HttpStatus.BAD_REQUEST.value();
    	String errMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
    	
        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponse> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
    	
    	int errCd = HttpStatus.BAD_REQUEST.value();
    	String errMsg = e.getMessage();
    	
        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse> resourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
    	
    	int errCd = HttpStatus.NOT_FOUND.value();
    	String errMsg = e.getMessage();

        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<CommonResponse> alreadyExistsException(AlreadyExistsException e, HttpServletRequest request) {
    	
    	int errCd = HttpStatus.BAD_REQUEST.value();
    	String errMsg = e.getMessage();

        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<CommonResponse> unauthorizedException(UnauthorizedException e, HttpServletRequest request) {
    	
    	int errCd = HttpStatus.UNAUTHORIZED.value();
    	String errMsg = e.getMessage();

        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }
    
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<CommonResponse> forbiddenException(ForbiddenException e, HttpServletRequest request) {

    	int errCd = HttpStatus.FORBIDDEN.value();
    	String errMsg = e.getMessage();
    	
        CommonResponse response = CommonResponse.builder()
                                                .code(errCd)
                                                .message(errMsg)
                                                .build();
//        log.error(e.getMessage());
        e.printStackTrace();
        
        errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({PSQLException.class})
    public ResponseEntity<CommonResponse> psqlException(PSQLException ex, HttpServletRequest request) {
    	
    	int errCd = HttpStatus.INTERNAL_SERVER_ERROR.value();
    	String sqlState = ex.getSQLState(); // SQL 상태 코드
    	String errMsg = "SqlState: "+sqlState+", ErrMsg: "+ex.getMessage();
    	
    	CommonResponse response = CommonResponse.builder().code(errCd).message(errMsg).build();
    	
    	ex.printStackTrace();
    	
    	errLogService.saveErrLog(request, errCd, errMsg);
        
        return ResponseEntity.status(errCd).body(response);
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
