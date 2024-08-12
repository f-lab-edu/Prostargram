package flab.project.domain.user.service;

import flab.project.domain.user.exception.InvalidEmailTokenException;
import flab.project.domain.user.exception.InvalidUsernameTokenException;
import flab.project.domain.user.model.SignUp;
import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.mapper.SignUpMapper;
import flab.project.utils.SignUpRedisUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService {

    public static final int TOKEN_LENGTH = 7;
    private final SignUpMapper signUpMapper;
    private final SignUpRedisUtil signUpRedisUtil;
    private final PasswordEncoder passwordEncoder;

    public void addUser(SignUp signUp) {
        String email = signUp.getEmail();
        String userName = signUp.getUsername();
        String password = signUp.getPassword();
        String emailToken = signUp.getEmailToken();
        String usernameToken = signUp.getUsernameToken();
        String encodedPassword = passwordEncoder.encode(password);

        validateToken(email, emailToken, userName, usernameToken);

        signUpMapper.addUser(email, userName, encodedPassword, LoginType.NORMAL);
    }

    private void validateToken(String email, String emailToken, String userName, String usernameToken) {
        validateTokenSize(emailToken, usernameToken);
        validateEmailToken(email, emailToken);
        validateUsernameToken(userName, usernameToken);
    }

    private void validateTokenSize(String emailToken, String usernameToken) {
        if (StringUtils.isBlank(emailToken) || emailToken.length() != TOKEN_LENGTH) {
            throw new InvalidEmailTokenException();
        }

        if (StringUtils.isBlank(usernameToken) || usernameToken.length() != TOKEN_LENGTH) {
            throw new InvalidUsernameTokenException();
        }
    }

    private void validateEmailToken(String email, String emailToken) {
        String emailTokenInRedis = signUpRedisUtil.get(email);

        if (!emailToken.equals(emailTokenInRedis)) {
            throw new InvalidEmailTokenException();
        }
    }

    private void validateUsernameToken(String username, String usernameToken) {
        String usernameKey = username + "_verify";
        String usernameTokenInRedis = signUpRedisUtil.get(usernameKey);

        if (!usernameToken.equals(usernameTokenInRedis)) {
            throw new InvalidUsernameTokenException();
        }
    }
}
