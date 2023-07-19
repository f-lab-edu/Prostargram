package flab.project.config.baseresponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import static flab.project.config.baseresponse.BaseResponseEnum.SUCCESS;

public class SuccessResponse<T> extends BaseResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public SuccessResponse(T result) {
        super(SUCCESS);
        this.result=result;
    }

    public SuccessResponse() {
        super(SUCCESS);
    }
}
