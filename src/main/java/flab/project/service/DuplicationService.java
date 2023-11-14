package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.DuplicationMapper;
import flab.project.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DuplicationService {

    private final RedisUtil redisUtil;
    private final DuplicationMapper duplicationMapper;

    public String verifyDuplicateUserName(String userName) {
        validateUserName(userName);

        Integer count = duplicationMapper.countUserName(userName);
        String verificationToken;

        if (count == null || count == 1) {
            throw new InvalidUserInputException("Duplicate userName.");
        } else {
            verificationToken = createVerificationToken();
            storeUserNameAndVerificationToken(userName, verificationToken);
        }

        return verificationToken;
    }

    private void validateUserName(String userName) {
        if (userName.length() < 1 || userName.length() > 16) {
            throw new InvalidUserInputException("Invalid userName.");
        }
    }

    private String createVerificationToken() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    private void storeUserNameAndVerificationToken(String userName, String verificationToken) {
        String name = userName + "_verify";
        int minutes = 60 * 50;

        redisUtil.setDataExpire(name, verificationToken, minutes);
    }
}
