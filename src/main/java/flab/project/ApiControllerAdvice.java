package flab.project;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BindException.class,
            InvalidUserInputException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            DataIntegrityViolationException.class,
            NumberLimitOfInterestExceededException.class,
            MissingServletRequestPartException.class
    })
    public FailResponse exceptionResolveToInvalidUserInput(Exception e) {
        return new FailResponse(ResponseEnum.INVALID_USER_INPUT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public FailResponse exceptionResolveToDeletedPost(NotFoundException e) {
        return new FailResponse(e.getMessage(), 4002);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            NotImageExtensionOrNotSupportedExtensionException.class
    })
    public FailResponse exceptionResolveToNotImageExtensionOrNotSupportedExtension(NotImageExtensionOrNotSupportedExtensionException e) {
        return new FailResponse(ResponseEnum.NOT_IMAGE_EXTENSION_OR_NOT_SUPPORTED_EXTENSION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public FailResponse exceptionResolveToDuplicateRequest(DuplicateKeyException e) {
        return new FailResponse(ResponseEnum.DUPLICATE_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotExistUserException.class)
    public FailResponse exceptionResolveToNonExistUser(NotExistUserException e) {
        return new FailResponse(ResponseEnum.NON_EXIST_USER);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public FailResponse exceptionResolveToServerError(RuntimeException e) {
        return new FailResponse(ResponseEnum.SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedToUpdateProfileImageToDatabaseException.class)
    public FailResponse exceptionResolveToFailedUpdateProfileImage(FailedToUpdateProfileImageToDatabaseException e) {
        return new FailResponse(ResponseEnum.FAILED_UPDATE_PROFILE_IMAGE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateUsername.class)
    public FailResponse exceptionResolveToNonExistUser(DuplicateUsername e) {
        return new FailResponse(ResponseEnum.DUPLICATE_USERNAME);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedToWriteEmailException.class)
    public FailResponse exceptionResolveToFailedWriteEmail(FailedToWriteEmailException e) {
        return new FailResponse(ResponseEnum.FAILED_WRITE_EMAIL);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenAccessException.class)
    public FailResponse exceptionResolveToFailedToVerifyUserIsOwner(ForbiddenAccessException e) {
        return new FailResponse(ResponseEnum.FAILED_TO_VERIFY_USER_IS_OWNER);
    }
}
