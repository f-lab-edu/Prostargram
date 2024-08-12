package flab.project.domain.user.exception;

public class ExistedAccountException extends RuntimeException {
    public ExistedAccountException() {
        super("이미 가입된 계정입니다.");
    }
}
