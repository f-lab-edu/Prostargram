package flab.project.domain.user.exception;

public class SocialAccountNotFoundException extends RuntimeException {

    public SocialAccountNotFoundException() {
        super("존재하지 않는 소셜 계정입니다.");
    }
}
