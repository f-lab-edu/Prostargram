package flab.project.domain.user.facade;

import flab.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;

    public void updateProfileImage(long userId, String contentUrl) {
        userService.updateProfileImage(userId, contentUrl);
    }
}