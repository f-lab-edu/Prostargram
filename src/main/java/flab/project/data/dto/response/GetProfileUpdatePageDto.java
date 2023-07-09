package flab.project.data.dto.response;

import flab.project.data.dto.common.ProfileInfo;
import flab.project.data.dto.common.HashTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로필 페이지 요청때 반환되는 Schema")
public class GetProfileUpdatePageDto extends ProfileInfo {

    @Schema(example = "해시태그 리스트")
    private List<HashTag> hashTagList;
}
