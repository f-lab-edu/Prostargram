package flab.project.controller;

import flab.project.data.dto.common.ProfileInfo;
import flab.project.data.dto.response.GetFollowDto;
import flab.project.data.enums.GetFollowsType;
import flab.project.data.enums.GetProfileRequestType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "UserController")
@RestController
public class UserController {

    @Operation(
            summary = "프로필 정보 확인하기 API",
            description = "1. 프로필 페이지에서 사용되는 API ( type으로 PROFILE_PAGE_REQUEST전달 ), "
                    + "2. 프로필 수정하기 페이지 에서 사용되는 API ( type으로 UPDATE_PAGE_REQUEST전달 ) ")
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "프로필을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
                    @Parameter(name = "type", description = "어떤 화면에서 사용하느냐에 따라 type을 다르게 전달해야함.", required = true)
            }
    )
    @ApiResponses({
            @ApiResponse(description = "PROFILE_PAGE_REQUEST로 요청할 경우와 UPDATE_PAGE_REQUEST로 요청할 경우 반환 형식이 다름."
                    + " example에는 공통 필드만 표시되므로 직접 실행해서 확인바람.")})
    @GetMapping(value = "/users/{userId}")
    public ProfileInfo getProfileInfo(@PathVariable("userId") Long userId, @RequestParam("type") GetProfileRequestType type) {
        return null;
    }

    @Operation(
            summary = "팔로워/팔로잉 목록 확인하기 API"
    )
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "프로필을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
                    @Parameter(name = "type", description = "팔로워/팔로잉 페이지에 따라 파라미터를 다르게 전달해야함.", required = true)
            }
    )
    @GetMapping(value = "/users/{userId}/follows")
    public List<GetFollowDto> getFollows(
            @PathVariable("userId") Long userId,
            @RequestParam("type") GetFollowsType type
    ) {
        return null;
    }


}
