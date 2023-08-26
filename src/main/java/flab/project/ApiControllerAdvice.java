package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.InvalidUserInputException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BindException.class,
            InvalidUserInputException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            DataIntegrityViolationException.class
    })
    public FailResponse exceptionResolveToInvalidUserInput(Exception e) {
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public FailResponse exceptionResolveToDuplicateRequest(DuplicateKeyException e) {
        return new FailResponse(ResponseEnum.DUPLICATE_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse exceptionResolveToServerError(RuntimeException e) {
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }
}
