package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.SignUp;
import flab.project.data.enums.LoginType;
import flab.project.mapper.SignUpMapper;
import flab.project.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final SignUpMapper signUpMapper;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;

    // Todo 이전에 검증한 값들을 FE단에서 가지고 있다가 넘겨주는 것인데, Controller단에서 수행하는 검증들을 반복해서 수행해줘야 할까?
    public void addUser(SignUp signUp, String emailToken, String userNameToken) {
        String email = signUp.getEmail();
        String userName = signUp.getUserName();
        String password = signUp.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        validateToken(email, emailToken, userName, userNameToken);

        signUpMapper.addUser(email, userName, encodedPassword, LoginType.NORMAL);
    }

    private void validateToken(String email, String emailSignUpToken, String userName, String userNameSignUpToken) {
        validateEmailToken(email, emailSignUpToken);
        validateUserNameToken(userName, userNameSignUpToken);
    }

    private void validateEmailToken(String email, String emailSignUpToken) {
        String emailInRedis = email + "_verify";
        String emailTokenInRedis = redisUtil.get(emailInRedis);

        if (!emailSignUpToken.equals(emailTokenInRedis)) {
            throw new InvalidUserInputException("Invalid emailSignUpToken.");
        }
    }

    private void validateUserNameToken(String userName, String userNameSignUpToken) {
        String userNameInRedis = userName + "_verify";
        String userNameTokenInRedis = redisUtil.get(userNameInRedis);

        if (!userNameSignUpToken.equals(userNameTokenInRedis)) {
            throw new InvalidUserInputException("Invalid userNameSignUpToken.");
        }
    }
}
