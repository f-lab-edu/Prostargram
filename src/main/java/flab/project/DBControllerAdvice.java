package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DBControllerAdvice {
    //todo sout지우고 올리기.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public FailResponse bindException(DuplicateKeyException e) {
        System.out.println("DBControllerAdvice.bindException");
        return new FailResponse(ResponseEnum.DUPLICATE_REQUEST);
    }

    //todo DataIntegrityViolationException은 자식 예외들도 있는거 같고.. 이런걸 어떻게 핸들링해야할까..? 보통 DB에서 나타나는 에러는 어느정도 핸들링해줄까?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public FailResponse IllegalArgumentException(DataIntegrityViolationException e) {
        System.out.println("DBControllerAdvice.IllegalArgumentException");
        return new FailResponse(ResponseEnum.NON_EXIST_USER);
    }
}
