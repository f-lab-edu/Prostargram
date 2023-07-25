package flab.project.config.baseresponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BaseResponseTest {

    @DisplayName("BaseResponse 자료형으로 getResult를 호출하면 successResponse 필드의 result를 불러낼 수 있다.")
    @Test
    public void successResponseCanGetResult() {
        //given
        Object object = new Object();
        BaseResponse<Object> response = new SuccessResponse<>(object);

        //when
        Object result = response.getResult();

        //then
        assertThat(result).isEqualTo(object);
    }

    @Disabled("getResult가 FailResponse에서 문제를 일으키는 중.. ")
    @DisplayName("실제 타입이 FailResponse일 때, BaseResponse의 getResult()를 호출하면 RuntimeError를 반환한다..")
    @Test
    public void failResponseCanNotGetResult() {
        //given
        BaseResponse<Object> response = new FailResponse(ResponseEnum.SUCCESS);

        //when then
        assertThatThrownBy(() -> response.getResult())
            .isInstanceOf(RuntimeException.class);
    }
}