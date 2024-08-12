package flab.project.domain.user.exception;

public class InvalidUsernameTokenException extends RuntimeException{

    public InvalidUsernameTokenException() {
        super("닉네임 중복 검증을 다시 진행해주세요.");
    }
}
