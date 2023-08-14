package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
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

    //todo sout지우고 올리기.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BindException.class,
            InvalidUserInputException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class
    })
    public FailResponse exceptionResolveToInvalidUserInput(Exception e) {
        e.printStackTrace();
        System.out.println("ApiControllerAdvice.exceptionResolveToInvalidUserInput");
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberLimitOfInterestExceededException.class)
    public FailResponse exceptionResolveToDuplicateRequest(NumberLimitOfInterestExceededException e) {
        System.out.println("ApiControllerAdvice.exceptionResolveToDuplicateRequest");
        return new FailResponse(ResponseEnum.NUMBER_LIMIT_OF_INTEREST_EXCEEDED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public FailResponse exceptionResolveToDuplicateRequest(DuplicateKeyException e) {
        System.out.println("ApiControllerAdvice.exceptionResolveToDuplicateRequest");
        return new FailResponse(ResponseEnum.DUPLICATE_REQUEST);
    }

    //todo DataIntegrityViolationException은 자식 예외들도 있는거 같고.. 이런걸 어떻게 핸들링해야할까..? 보통 DB에서 나타나는 에러는 어느정도 핸들링해줄까?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public FailResponse exceptionResolveToNonExistUser(DataIntegrityViolationException e) {
        System.out.println("ApiControllerAdvice.exceptionResolveToNonExistUser");
        return new FailResponse(ResponseEnum.NON_EXIST_USER);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse exceptionResolveToServerError(RuntimeException e) {
        e.printStackTrace();
        System.out.println("ApiControllerAdvice.exceptionResolveToServerError");
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }

}