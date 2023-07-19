package flab.project.config.baseresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static flab.project.config.baseresponse.BaseResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public abstract class BaseResponse {

    @Schema(name = "성공 여부", example = "true")
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @Schema(name = "요청에 대한 결과 메세지", example = "요청에 성공하였습니다.")
    private final String message;

    @Schema(name = "요청에 대한 결과 코드", example = "1000")
    private final int code;

    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

}
