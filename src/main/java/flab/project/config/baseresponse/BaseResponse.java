package flab.project.config.baseresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message"})
public abstract class BaseResponse {

    @Schema(description = "성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "요청에 대한 결과 메세지", example = "요청에 성공하였습니다.")
    private final String message;

    @Schema(description = "요청에 대한 결과 코드", example = "1000")
    private final int code;

    protected BaseResponse(ResponseEnum status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    protected BaseResponse(Boolean isSuccess, String message, int code) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
    }
}