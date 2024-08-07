package flab.project.domain.user.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.model.AddInterest;
import flab.project.domain.user.facade.InterestFacade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class InterestController {

    private final InterestFacade interestFacade;

    @PostMapping("/users/{userId}/interests")
    public SuccessResponse addInterest(
            @PathVariable("userId") @Positive long userId,
            @Validated @RequestBody AddInterest addInterestDto
    ) {
        return interestFacade.addInterest(addInterestDto);
    }

    @DeleteMapping("/users/{userId}/interests")
    public SuccessResponse deleteInterest(
            @PathVariable("userId") @Positive long userId,
            @RequestParam @Positive long hashTagId,
            @NotBlank String name
    ) {
        return interestFacade.deleteInterest(userId, hashTagId, name);
    }
}