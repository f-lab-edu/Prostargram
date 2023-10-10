package flab.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    // 서버가 동작은 하지만, 내가 작성한 API가 작동을 안하는지 확인할 때 사용
    @GetMapping("/test")
    public String Test(
            @AuthenticationPrincipal User user
    ) {
        Long userId = Long.valueOf(user.getUsername());
        return "userId : " + userId;
    }
}