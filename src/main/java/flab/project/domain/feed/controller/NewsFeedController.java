package flab.project.domain.feed.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.common.model.PaginationModel;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.feed.service.NewsFeedService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @Operation(
            summary = "피드 조회 API (구현중)",
            description = """
                    page만 query string으로 전송하면 된다.\n
                    </br></br>
                    post는 example value에서 보는 것과 형태가 다를 수 있다.\n 
                    post는 **basic post와 debate post 두가지 형태로** 반환될 수 있기 때문인데
                    두 형태는 schema를 참고 바란다.\n
                    (poll post의 경우 1차 구현 범위가 아니므로 무시해도 된다.)
                    </br></br>
                    현재 임시 반환값에는 basic post와 debate post모두 포함시켜놓았다.\n
                    """
    )
    @GetMapping("/feeds")
    public SuccessResponse<PaginationModel<List<PostWithUser>>> getFeeds(
            @LoggedInUserId Long userId,
            @RequestParam long page
    ) {
        PaginationModel<List<PostWithUser>> feeds = newsFeedService.getFeeds(userId,page);

        return new SuccessResponse<>(feeds);
    }
}
