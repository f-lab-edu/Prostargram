package flab.project.controller;

import flab.project.data.dto.PostResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    @Operation(
            summary = "메인 페이지에서 피드 가져오기 API"
    )
    @GetMapping(value = "/feeds")
    public PostResponseDto getFeeds() {
        return null;
    }
}
