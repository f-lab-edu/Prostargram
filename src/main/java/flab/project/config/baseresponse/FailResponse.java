package flab.project.config.baseresponse;

public class FailResponse extends BaseResponse {

    public FailResponse(ResponseEnum status) {
        super(status);
    }

    public FailResponse(String message, int code) {
        super(false, message, code);
    }
}