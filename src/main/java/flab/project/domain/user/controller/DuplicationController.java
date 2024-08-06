package flab.project.domain.user.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.service.DuplicationService;
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
// Todo Duplication 대신 다른 명사로 클래스 이름 변경 예정
public class DuplicationController {

    private final DuplicationService duplicationService;

    // Todo 욕설 및 비속어 필터링은 커스텀 어노테이션을 만들 예정
    @Operation(summary = "닉네임 중복 검증 API")
    @Parameters({
            @Parameter(name = "userName",
                    description = "유저의 닉네임",
                    in = ParameterIn.QUERY,
                    required = true)
    })
    @PostMapping(value = "/verify-username")
    public SuccessResponse verifyDuplicateUserName(
            @RequestParam("userName")
            @NotBlank @Size(min = 1, max = 16) String userName
    ) {
        String userNameToken
                = duplicationService.verifyDuplicateUserName(userName);

        return new SuccessResponse<>(userNameToken);
    }
}
