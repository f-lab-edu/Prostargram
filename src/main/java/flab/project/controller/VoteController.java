package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PostType;
import flab.project.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "투표하기 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", required = true)})
    @PostMapping(value = "/posts/{postId}/votes")
    public SuccessResponse addPostVote(@PathVariable("postId") @Positive long postId, @RequestParam("post type") PostType postType, @RequestParam("optionId") List<@Positive Long> optionIds, @RequestParam("userId") @Positive long userId) {
        return voteService.addPostVote(postType, optionIds, userId);
    }
}
