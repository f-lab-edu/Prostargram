package flab.project.domain.user.exception;

public class ExistedUsernameException extends RuntimeException {

    public ExistedUsernameException() {
        super("사용 불가능한 닉네임입니다.");
    }
}