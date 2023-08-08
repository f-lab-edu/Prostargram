package flab.project.ApiControllerAdvice;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.InvalidUserInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidUserInputException.class, RuntimeException.class})
    public FailResponse exceptionResolve(Exception e) {
        if (e instanceof InvalidUserInputException) {
            return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
        } else {
            return new FailResponse(ResponseEnum.SERVER_ERROR);
        }
    }
}
