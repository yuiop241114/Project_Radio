package com.radio.cast.globalFile.exception;

import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.connector.ClientAbortException;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.radio.cast.globalFile.exception.dto.ErrorResponse;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 출력용
@RestControllerAdvice //모든 Controller에서 발생하는 예외를 처리한다는 의미
public class GlobalExceptionHandler {
        
    // [추가] 클라이언트가 연결을 중단한 경우 처리
    // 이 예외는 서버의 잘못이 아니므로 로그만 살짝 남기거나 무시합니다.
    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e) {
        log.warn("Client closed the connection abruptly (ClientAbortException).");
        // ResponseEntity를 반환하지 않고 void로 선언하면 Spring이 추가적인 응답 시도를 하지 않습니다.
    }

    

    // BusinessException 처리 (가장 중요!)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage());
        
        ErrorCode errorCode = e.getErrorCode();
        
        ErrorResponse response = ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.name())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }
    
    // @Valid 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());
        
        // 첫 번째 에러 메시지만 반환
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("INVALID_INPUT_VALUE")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage());
        
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("INVALID_INPUT_VALUE")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    // 그 외 모든 예외 처리
//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<ErrorResponse> handleException(Exception e) {
//         log.error("Unexpected error", e);
        
//         ErrorResponse response = ErrorResponse.builder()
//                 .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                 .code("INTERNAL_SERVER_ERROR")
//                 .message("서버 오류가 발생했습니다.")
//                 .timestamp(LocalDateTime.now())
//                 .build();
        
//         return ResponseEntity
//                 .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                 .body(response);
//     }
@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        // [수정 추천] ClientAbortException이 위에서 걸러지겠지만, 
        // 혹시 모를 연쇄 오류를 방지하기 위해 원인을 한 번 더 체크하면 좋습니다.
        if (e.getCause() instanceof java.io.IOException && e.getMessage().contains("Broken pipe")) {
             return null; // 혹은 위와 같이 void 처리를 권장합니다.
        }

        log.error("Unexpected error", e);
        
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("INTERNAL_SERVER_ERROR")
                .message("서버 오류가 발생했습니다.")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
