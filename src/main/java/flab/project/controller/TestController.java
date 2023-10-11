package flab.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    // 서버가 동작은 하지만, 내가 작성한 API가 작동을 안하는지 확인할 때 사용
    @GetMapping("/test")
    public String Test() {
        return "test";
    }
}