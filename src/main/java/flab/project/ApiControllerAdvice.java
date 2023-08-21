package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.InvalidUserInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiControllerAdvice {

    /*
    * 클라이언트에서 잘못 요청하여 발생한 예외를 위한 핸들링입니다.*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidUserInputException.class,
            MethodArgumentNotValidException.class
    })
    public FailResponse exceptionResolveToInvalidUserInput(Exception e) {
        e.printStackTrace();
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    /*
    * 서버 내부에서 예기치 못하게 발생한 예외를 위한 핸들링입니다.*/
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse exceptionResolveToServerError(RuntimeException e) {
        e.printStackTrace();
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }
}
