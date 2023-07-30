package flab.project.service;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Follows;
import flab.project.mapper.FollowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = FollowService.class)
class FollowServiceUnitTest {

    @Autowired
    FollowService followService;

    @MockBean
    FollowMapper followMapper;

    @DisplayName("followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSame(){
        //given
        Follows follows = new Follows(1L, 1L);
        // when then
        assertThatThrownBy(() -> followService.addFollow(follows))
            .isInstanceOf(InvalidUserInputException.class);
    }
}