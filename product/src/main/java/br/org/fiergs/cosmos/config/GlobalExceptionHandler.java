package br.org.fiergs.cosmos.config;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.org.fiergs.common.dto.ErrorDTO;
import br.org.fiergs.common.dto.ErrorDetailDTO;
import br.org.fiergs.common.model.exception.BusinessException;
import br.org.fiergs.common.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError("Not Found")
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(ex.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateIsbnException(BusinessException ex, WebRequest request) {
        logger.error("Error processing request: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError("Unprocessable Entity")
                .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .withMessage(ex.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDTO> handleObjectOptimisticLockingFailureException(
            ObjectOptimisticLockingFailureException ex, WebRequest request) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError("Conflict")
                .withStatus(HttpStatus.CONFLICT.value())
                .withMessage(ex.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage());

        List<ErrorDetailDTO> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorDetailDTO.builder()
                        .withField(((FieldError) error).getField())
                        .withMessage(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError("Bad Request")
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage("Validation error")
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .withDetails(details)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAuthorizationDeniedException(AuthorizationDeniedException ex,
            WebRequest request) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError(HttpStatus.FORBIDDEN.getReasonPhrase())
                .withStatus(HttpStatus.FORBIDDEN.value())
                .withMessage(ex.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Error processing request: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .withError("Internal Server Error")
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withMessage(ex.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
