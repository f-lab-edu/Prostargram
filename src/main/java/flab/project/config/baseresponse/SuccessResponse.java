package flab.project.config.baseresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;

@Getter
public class SuccessResponse<T> extends BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public SuccessResponse(T result) {
        super(SUCCESS);
        this.result = result;
    }

    public SuccessResponse() {
        super(SUCCESS);
    }
}
