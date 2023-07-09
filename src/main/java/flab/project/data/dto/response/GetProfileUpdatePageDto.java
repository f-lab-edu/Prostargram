package flab.project.data.dto.response;

import flab.project.data.dto.common.UpdatePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로필 페이지 요청때 반환되는 Schema")
public class GetProfileUpdatePageDto extends UpdatePage {

}
