package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.InvalidUserInput;
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
    @ExceptionHandler(BindException.class)
    public FailResponse bindExceptionResolve(BindException e) {
        System.out.println("ApiControllerAdvice.bindException");
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserInput.class)
    public FailResponse invalidUserInputExceptionResolve(InvalidUserInput e) {
        System.out.println("ApiControllerAdvice.invalidUserInputExceptionResolve");
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public FailResponse constraintViolationExceptionResolve(ConstraintViolationException e) {
        System.out.println("ApiControllerAdvice.constraintViolationExceptionResolve");
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public FailResponse methodArgumentTypeMismatchExceptionResolve(MethodArgumentTypeMismatchException e) {
        System.out.println("ApiControllerAdvice.methodArgumentTypeMismatchExceptionResolve");
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public FailResponse duplicateKeyExceptionResolve(DuplicateKeyException e) {
        System.out.println("ApiControllerAdvice.bindException");
        return new FailResponse(ResponseEnum.DUPLICATE_REQUEST);
    }

    //todo DataIntegrityViolationException은 자식 예외들도 있는거 같고.. 이런걸 어떻게 핸들링해야할까..? 보통 DB에서 나타나는 에러는 어느정도 핸들링해줄까?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public FailResponse dataIntegrityViolationExceptionResolve(DataIntegrityViolationException e) {
        System.out.println("DBControllerAdvice.IllegalArgumentException2");
        return new FailResponse(ResponseEnum.NON_EXIST_USER);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse runtimeExceptionResolveResolve(RuntimeException e) {
        System.out.println("ApiControllerAdvice.runtimeExceptionResolve");
        e.printStackTrace();
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }

}
