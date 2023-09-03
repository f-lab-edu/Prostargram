package flab.project.config.exception;

public class NumberLimitOfSocialAccountsExceededException extends RuntimeException {

    public NumberLimitOfSocialAccountsExceededException() {
    }

    public NumberLimitOfSocialAccountsExceededException(String message) {
        super(message);
    }
}
