package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    //todo sout지우고 올리기.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public FailResponse bindExceptionResolve(BindException e) {
        System.out.println("ApiControllerAdvice.bindException");
        return new FailResponse(ResponseEnum.ILLEGAL_ARGUMENT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public FailResponse illegalArgumentExceptionResolve(IllegalArgumentException e) {
        System.out.println("ApiControllerAdvice.IllegalArgumentException");
        return new FailResponse(ResponseEnum.ILLEGAL_ARGUMENT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse runtimeExceptionResolve(RuntimeException e) {
        System.out.println("ApiControllerAdvice.runtimeExceptionResolve");
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }

}
