package flab.project.domain.user.exception;

public class InvalidEmailTokenException extends RuntimeException{

    public InvalidEmailTokenException() {
        super("이메일 인증을 다시 진행해주세요.");
    }
}
