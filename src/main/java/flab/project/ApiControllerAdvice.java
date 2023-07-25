package flab.project;

import flab.project.config.baseresponse.BaseResponse;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
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
    public FailResponse bindException(BindException e) {
        System.out.println("ApiControllerAdvice.bindException");
        return new FailResponse(ResponseEnum.IllegalArgument);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public FailResponse IllegalArgumentException(IllegalArgumentException e) {
        System.out.println("ApiControllerAdvice.IllegalArgumentException");
        return new FailResponse(ResponseEnum.IllegalArgument);
    }

}
