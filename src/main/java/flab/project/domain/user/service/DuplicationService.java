package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.exception.ExistedUsernameException;
import flab.project.domain.user.mapper.DuplicationMapper;
import flab.project.utils.RedisUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DuplicationService {

    private static final String USERNAME_IN_REDIS_SUFFIX = "_verify";

    private static final long SECONDS_FOR_DURATION = 60 * 30L;

    private final RedisUtil redisUtil;

    private final DuplicationMapper duplicationMapper;

    public String verifyDuplicateUserName(String userName) {
        validateUserName(userName);

        String verificationToken = createVerificationToken();
        reserveUserName(userName, verificationToken);

        return verificationToken;
    }

    private void validateUserName(String userName) {
        validateLengthOfUserName(userName);
        validateUserNamePreemptionInRedis(userName);
        validateUserNameDuplicationInDB(userName);
    }

    private void validateLengthOfUserName(String userName) {
        if (StringUtils.isBlank(userName) || userName.length() > 16) {
            throw new InvalidUserInputException("Invalid userName.");
        }
    }

    private void validateUserNamePreemptionInRedis(String userName) {
        if (redisUtil.hasKey(userName)) {
            throw new ExistedUsernameException();
        }
    }

    private void validateUserNameDuplicationInDB(String userName) {
        Integer count = duplicationMapper.countUserName(userName);
        if (count == null || count == 1) {
            throw new ExistedUsernameException();
        }
    }

    private String createVerificationToken() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    private void reserveUserName(String userName, String verificationToken) {
        String usernameKey = userName + USERNAME_IN_REDIS_SUFFIX;

        redisUtil.setWithDuration(
                usernameKey,
                verificationToken,
                SECONDS_FOR_DURATION
        );
    }
}
