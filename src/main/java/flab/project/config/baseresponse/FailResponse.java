package flab.project.config.baseresponse;

public class FailResponse extends BaseResponse {
    public FailResponse(ResponseEnum status) {
        super(status);
    }

    //Todo BaseResponse의 편의성 변환메서드인 getResult 때문에 FailResponse를 변환할 때, getResult가 호출되어 문제가 생김.
    public Object getResult() {
        return null;
    }
}
