package br.com.rformagio.person.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomPersonRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, "Problema nos dados enviados!", errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());

    }

    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<Object> handlePersonNotFound(PersonNotFoundException pnfe,
                                                       WebRequest request) {

        ApiError apiError =
                new ApiError(HttpStatus.NOT_FOUND, pnfe.getLocalizedMessage(), pnfe.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({CepNotFoundException.class})
    public ResponseEntity<Object> handleCepNotFound(CepNotFoundException cnfe,
                                                       WebRequest request) {

        ApiError apiError =
                new ApiError(HttpStatus.NOT_FOUND, cnfe.getLocalizedMessage(), cnfe.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintValidation(ConstraintViolationException cve,
                                                             WebRequest request) {

        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations =  cve.getConstraintViolations();
        for(ConstraintViolation<?> violation : violations){
            errors.add(violation.getMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, "Problema nos dados enviados!", errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());

    }

    @ExceptionHandler({NoPersonFoundException.class})
    public ResponseEntity<Object> handleNoPersonFound(NoPersonFoundException npf,
                                                       WebRequest request) {

        ApiError apiError =
                new ApiError(HttpStatus.NO_CONTENT, "Lista vazia", npf.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


}
