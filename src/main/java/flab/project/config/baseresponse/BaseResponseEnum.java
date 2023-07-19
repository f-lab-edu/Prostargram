package flab.project.config.baseresponse;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseEnum {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseEnum(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}


