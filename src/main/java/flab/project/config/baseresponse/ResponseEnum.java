package flab.project.config.baseresponse;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    INVALID_USER_INPUT(false, 4000, "잘못된 요청입니다."),
    NON_EXIST_USER(false, 4001, "존재하지 않는 유저입니다."),
    NOT_FOUND_POST(false, 4002, "없는 게시물입니다."),
    NOT_IMAGE_EXTENSION_OR_NOT_SUPPORTED_EXTENSION(false, 4003, "이미지 확장자가 아니거나, 지원되지 않는 확장자 입니다."),
    NUMBER_LIMIT_OF_INTEREST_EXCEEDED(false, 4004, "관심사는 최대 3개까지 설정할 수 있습니다."),
    DUPLICATE_USERNAME(false, 4005, "중복된 닉네임입니다."),
    UNAUTHORIZED_USER(false, 4006, "로그인이 필요합니다. 로그인 후 다시 시도하세요."),
    FORBIDDEN_ACCESS(false, 4007, "해당 요청에 대한 권한이 없습니다."),

    FAILED_TO_VERIFY_USER_IS_OWNER(false,403,"권한이 없습니다."),
    SERVER_ERROR(false, 5000, "서버 오류입니다. 잠시후 다시 시도하세요."),
    DUPLICATE_REQUEST(false, 5001, "서버 오류입니다. 잠시후 다시 시도하세요."),
    FAILED_UPDATE_PROFILE_IMAGE(false, 5002, "프로필 이미지 수정에 실패했습니다. 잠시 후 다시 시도하세요."),
    FAILED_WRITE_EMAIL(false, 5003, "이메일 작성에 실패했습니다. 잠시 후 다시 시도하세요.");

    // 커스텀 상태코드는 1000~10000
    // 1000번대면 유저쪽 도메인
    // 2000번 대면 팔로워/팔로잉쪽 도메임
    // 3000번대면 게시물쪽 도메인
    private final boolean isSuccess;
    private final int code;
    private final String message;

    ResponseEnum(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}