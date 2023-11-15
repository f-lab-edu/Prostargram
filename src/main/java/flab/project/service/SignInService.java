package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.SignInMapper;
import flab.project.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInService {

    private final SignInMapper signInMapper;
    private final RedisUtil redisUtil;

    // Todo 이전에 검증한 값들을 FE단에서 가지고 있다가 넘겨주는 것인데, Controller단에서 수행하는 검증들을 반복해서 수행해줘야 할까?
    public void addUserInformation(String email, String emailToken, String userName, String userNameToken, String password) {
        vaildateToken(email, emailToken, userName, userNameToken);
        validatePassword(password);

        signInMapper.addUserInformation(email, userName, password);
    }

    private void vaildateToken(String email, String emailSignInToken, String userName, String userNameSignInToken) {
        validateEmailToken(email, emailSignInToken);
        validateUserNameToken(userName, userNameSignInToken);
    }

    private void validateEmailToken(String email, String emailSignInToken) {
        String emailInRedis = email + "_verify";
        String emailTokenInRedis = redisUtil.get(emailInRedis);

        if (!emailTokenInRedis.equals(emailSignInToken)) {
            throw new InvalidUserInputException("Invalid emailSignInToken.");
        }
    }

    private void validateUserNameToken(String userName, String userNameSignInToken) {
        String userNameInRedis = userName + "_verify";
        String userNameTokenInRedis = redisUtil.get(userNameInRedis);

        if (!userNameTokenInRedis.equals(userNameSignInToken)) {
            throw new InvalidUserInputException("Invalid userNameSignInToken.");
        }
    }

    private void validatePassword(String password) {
        validatePasswordForMinMaxLength(password);
        validatePasswordForBlank(password);
    }

    private void validatePasswordForMinMaxLength(String password) {
        if (password.length() < 8 || password.length() > 20) {
            // Todo 근데 왜 Exception 내용을 간략히 적기로 했었지?
            throw new InvalidUserInputException("Not fit the minimum or maximum length of the password.");
        }
    }

    private void validatePasswordForBlank(String password) {
        if (password == null || password.length() == 0 || StringUtils.isBlank(password)) {
            throw new InvalidUserInputException("Invalid password.");
        }
    }
}
