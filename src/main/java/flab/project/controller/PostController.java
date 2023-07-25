package flab.project.controller;

import flab.project.config.baseresponse.BaseResponse;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.CreateBasicPostRequestDto;
import flab.project.data.dto.CreateBasicPostResponseDto;
import flab.project.data.dto.GetDetailBasicPostResponseDto;
import flab.project.data.dto.GetFeedResponseDto;
import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 생성자 주입
@RestController // Json 형태로 객체 데이터 반환
public class PostController {

    // Todo 구현 예정
    private final PostService postService;

    @Operation(
            summary = "메인 페이지에서 피드 가져오기 API"
    )
    /*@Parameters({
            @Parameter(name = "postId", description = "메인 페이지에서 노출되는 피드의 id", required = true)
    })*/
    @GetMapping(value = "/feeds")
    public BaseResponse getFeeds() {
        try {
            GetFeedResponseDto responseDto = postService.getFeeds();
            return new SuccessResponse<>(responseDto);
        } catch (Exception e) {
            // Todo ResponseEnum에 실패에 관련된 상수 추가 및 ControllerAdvice 학습
            return new FailResponse(ResponseEnum.FAILURE);
        }
    }

    // Todo Create~RequestDto가 필요없어질 수도 있기 때문에, 예시로 CreateBasicPostRequestDto에 대한 API만 작성하였습니다.
    // Todo 하나의 API 엔드 포인트에서 Post type을 판별할지, 각 Post type에 대해 별도의 API 엔드 포인트를 가질지 고민 중입니다.
    @Operation(
            summary = "일반 게시글 작성하기 API"
    )
    @Parameters({
            // 클라이언트가 서버에게 요청하는 데이터
            @Parameter(name = "CreateBasicRequestDto", description = "일반 게시물 생성에 필요한 Request Dto", required = true)
    })
    @PostMapping(value = "/posts/basic")
    public BaseResponse createBasicPost(
            @RequestBody CreateBasicPostRequestDto requestDto) {
        try {
            CreateBasicPostResponseDto responseDto = postService.createBasicPost(requestDto);
            return new SuccessResponse<>(responseDto);
        } catch (Exception e) {
            return new FailResponse(ResponseEnum.FAILURE);
        }
    }

    @Operation(
            summary = "일반 피드 상세보기 API"
    )
    @Parameters({
            @Parameter(name = "postId", description = "특정 일반 게시물의 id", required = true)
    })
    @GetMapping(value = "/feeds/{postId}")
    public BaseResponse getDetailBasicPost(
            // Todo 왜 reference type을 쓰셨을까?
            @PathVariable("postId") long userId,
            // Todo postType vs type
            @RequestParam("type") PostType postType) {
        try {
            GetDetailBasicPostResponseDto responseDto = postService.getDetailBasicPost();
            return new SuccessResponse<>(responseDto);
        } catch (Exception e) {
            return new FailResponse(ResponseEnum.FAILURE);
        }
    }

    // Todo "피드 좋아요 또는 좋아요 취소 API"는 별도의 LikeController로 작성해야할까? 아니면 PostController에 포함시켜서 작성해야할까?
    // Todo 또한 "댓글 작성" 및 "댓글 조회" 그리고 "댓글 좋아요 또는 좋아요 취소 API"는 하나의 CommentController에서 endPoint 작성을 하는게 맞을까?

    @Operation(
            summary = "토론 피드 또는 통계 피드 투표하기 API"
    )
    @Parameters({
            // Todo Dto 없이 아래와 같이 API만으로 구현 vs 투표에 관한 Dto 작성 후 구현
            @Parameter(name = "postId", description = "특정 토론 또는 통계 게시물의 id", required = true),
            @Parameter(name = "userId", description = "투표하는 유저의 id", required = true)
    })
    @PostMapping(value = "feeds/{postId}/votes")
    // Todo 회의 후, Post vs PUT 결정
    public BaseResponse createVote(
            @PathVariable("postId") long postId,
            @PathVariable("userId") long userId) {
        try {

        } catch (Exception e) {

        }
    }

}
