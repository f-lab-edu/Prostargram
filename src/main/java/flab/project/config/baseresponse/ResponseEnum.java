package flab.project.config.baseresponse;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    INVALID_USER_INPUT(false, 4000, "잘못된 요청입니다."),
    SERVER_ERROR(false, 5000, "서버 오류입니다. 잠시 후 다시 시도하세요.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    ResponseEnum(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}


