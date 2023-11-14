package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.DuplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class DuplicationController {

    private final DuplicationService duplicationService;

    // Todo 욕설 및 비속어 필터링은 커스텀 어노테이션을 만들 예정
    // Todo 영어/한글/숫자/_ 및 온점 사용 가능 필터링은 추가 예정
    @Operation(summary = "닉네임 중복 검증 API")
    @Parameters({
            @Parameter(name = "userName", description = "유저의 닉네임", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/users")
    public SuccessResponse verifyDuplicateUserName(@RequestParam("userName") @NotBlank @Size(min = 1, max = 16) String userName) {
        String userNameToken = duplicationService.verifyDuplicateUserName(userName);

        return new SuccessResponse<>(userNameToken);
    }
}
