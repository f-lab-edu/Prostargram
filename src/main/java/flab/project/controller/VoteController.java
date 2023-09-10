package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@Validated
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "토론 게시물 투표하기 API")
    @Parameter(name = "postId", description = "게시물의 id", required = true)
    @PostMapping(value = "/posts/{postId}/votes/debate")
    public SuccessResponse addDebatePostVote(@PathVariable("postId") @Positive long postId, @RequestParam("optionId") @Positive long optionId, @RequestParam("userId") @Positive long userId) {
        return voteService.addDebatePostVote(postId, optionId, userId);
    }

    @Operation(summary = "통계 게시물 투표하기 API")
    @Parameter(name = "postId", description = "게시물의 id", required = true)
    @PostMapping(value = "/posts/{postId}/votes/poll")
    public SuccessResponse addPollPostVote(@PathVariable("postId") @Positive long postId, @RequestParam("optionIds") Set<@Positive Long> optionIds, @RequestParam("userId") @Positive long userId) {
        return voteService.addPollPostVote(postId, optionIds, userId);
    }
}