package flab.project.config.exception;

import static flab.project.common.Constraints.NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS;

public class NumberLimitOfSocialAccountsExceededException extends RuntimeException {

    private static final String errorMessage = "소셜 계정은 최대 " + NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS + "개 까지 설정할 수 있습니다.";

    public NumberLimitOfSocialAccountsExceededException() {
        super(errorMessage);
    }
}