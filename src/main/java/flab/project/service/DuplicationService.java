package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.DuplicationMapper;
import flab.project.utils.RedisUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
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
        validateUserNameDuplicationInDB(userName);
        validateUserNamePreemptionInRedis(userName);
    }

    private void validateLengthOfUserName(String userName) {
        if (StringUtils.isBlank(userName) || userName.length() > 16) {
            throw new InvalidUserInputException("Invalid userName.");
        }
    }

    private void validateUserNameDuplicationInDB(String userName) {
        Integer count = duplicationMapper.countUserName(userName);
        if (count == null || count == 1) {
            throw new InvalidUserInputException("Duplicate userName In DB.");
        }
    }

    private void validateUserNamePreemptionInRedis(String userName) {
        if (redisUtil.hasKey(userName)) {
            throw new DuplicateFormatFlagsException("Duplicate userName.");
        }
    }

    private String createVerificationToken() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    private void reserveUserName(String userName, String verificationToken) {
        String name = userName + USERNAME_IN_REDIS_SUFFIX;

        redisUtil.setWithDuration(
                name,
                verificationToken,
                SECONDS_FOR_DURATION
        );
    }
}
