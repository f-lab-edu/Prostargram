package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.exception.ExistedUsernameException;
import flab.project.domain.user.mapper.UserMapper;
import flab.project.utils.RedisUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsernameDuplicationService {

    private static final String USERNAME_IN_REDIS_SUFFIX = "_verify";

    private static final long SECONDS_FOR_DURATION = 60 * 15L;
    public static final int USERNAME_MAX_LENGTH = 16;
    public static final int USERNAME_VERIFICATION_TOKEN_LENGTH = 7;

    private final RedisUtil redisUtil;
    private final UserMapper userMapper;

    public String verifyDuplicateUserName(String username) {
        validateUsername(username);

        String verificationToken = createVerificationToken();
        reserveUsername(username, verificationToken);

        return verificationToken;
    }

    private void validateUsername(String username) {
        validateLengthOfUsername(username);
        validateUsernamePreemptionInRedis(username);
        validateUsernameDuplicationInDB(username);
    }

    private void validateLengthOfUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new InvalidUserInputException("닉네임을 입력해주세요.");
        }

        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new InvalidUserInputException("닉네임의 최대 길이는 16자입니다.");
        }
    }

    private void validateUsernamePreemptionInRedis(String username) {
        if (redisUtil.hasKey(username)) {
            throw new ExistedUsernameException();
        }
    }

    private void validateUsernameDuplicationInDB(String username) {
        boolean exists = userMapper.existsByUsername(username);
        if (exists) {
            throw new ExistedUsernameException();
        }
    }

    private String createVerificationToken() {
        return UUID.randomUUID().toString().substring(0, USERNAME_VERIFICATION_TOKEN_LENGTH);
    }

    private void reserveUsername(String userName, String verificationToken) {
        String usernameKey = userName + USERNAME_IN_REDIS_SUFFIX;

        redisUtil.setWithDuration(
                usernameKey,
                verificationToken,
                SECONDS_FOR_DURATION
        );
    }
}
