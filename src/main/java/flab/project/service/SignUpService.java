package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.SignUp;
import flab.project.data.enums.LoginType;
import flab.project.mapper.SignUpMapper;
import flab.project.utils.SignUpRedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final SignUpMapper signUpMapper;
    private final SignUpRedisUtil signUpRedisUtil;
    private final PasswordEncoder passwordEncoder;

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
        String emailTokenInRedis = signUpRedisUtil.get(emailInRedis);

        if (!emailSignUpToken.equals(emailTokenInRedis)) {
            throw new InvalidUserInputException("Invalid emailSignUpToken.");
        }
    }

    private void validateUserNameToken(String userName, String userNameSignUpToken) {
        String userNameInRedis = userName + "_verify";
        String userNameTokenInRedis = signUpRedisUtil.get(userNameInRedis);

        if (!userNameSignUpToken.equals(userNameTokenInRedis)) {
            throw new InvalidUserInputException("Invalid userNameSignUpToken.");
        }
    }
}
