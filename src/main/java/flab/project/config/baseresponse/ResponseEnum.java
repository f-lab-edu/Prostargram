package flab.project.config.baseresponse;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    INVALID_USER_INPUT(false, 4000, "잘못된 요청입니다."),
    NON_EXIST_USER(false, 4001, "존재하지 않는 유저입니다."),
    NUMBER_LIMIT_OF_INTEREST_EXCEEDED(false, 4002, "관심사는 최대 3개 까지 설정할 수 있습니다."),

    DUPLICATE_REQUEST(false, 5000, "서버 오류입니다. 잠시후 다시 시도하세요."),
    SERVER_ERROR(false, 5000, "서버 오류입니다. 잠시후 다시 시도하세요.");
    //todo 메시지 어떻게 던져야할까..?
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private ResponseEnum(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

