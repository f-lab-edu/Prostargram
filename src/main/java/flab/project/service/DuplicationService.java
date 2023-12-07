package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.DuplicationMapper;
import flab.project.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DuplicationService {

    private static final String USERNAME_IN_REDIS_SUFFIX = "_verify";

    private final RedisUtil redisUtil;
    private final DuplicationMapper duplicationMapper;

    public String verifyDuplicateUserName(String userName) {
        validateUserName(userName);

        String verificationToken = createVerificationToken();
        preemptUserName(userName, verificationToken);

        return verificationToken;
    }

    private void validateUserName(String userName) {
        validateLengthOfUserName(userName);
        validateUserNameDuplicationDB(userName);
        validateUserNamePreemptionInRedis(userName);
    }

    private void validateLengthOfUserName(String userName) {
        if (userName.length() < 1 || userName.length() > 16) {
            throw new InvalidUserInputException("Invalid userName.");
        }
    }

    private void validateUserNameDuplicationDB(String userName) {
        Integer count = duplicationMapper.countUserName(userName);
        if (count == null || count == 1) {
            throw new InvalidUserInputException("Duplicate userName.");
        }
    }

    private void validateUserNamePreemptionInRedis(String userName) {
        if (redisUtil.exist(userName)) {
            throw new DuplicateFormatFlagsException("중복된 닉네임입니다.");
        }
    }

    private String createVerificationToken() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    private void preemptUserName(String userName, String verificationToken) {
        String name = userName + USERNAME_IN_REDIS_SUFFIX;
        int minutes = 60 * 30;

        redisUtil.setDataExpire(name, verificationToken, minutes);
    }
}
