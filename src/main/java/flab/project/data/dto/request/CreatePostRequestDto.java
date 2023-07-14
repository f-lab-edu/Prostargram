package flab.project.data.dto.request;

import flab.project.data.dto.model.BasicPostInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드를 작성하는 Dto")
public class CreatePostRequestDto extends BasicPostInfo {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "이은비")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private String content;

    // Todo 이미지가 없을 경우, json으로 데이터가 직렬화 되는지 Controller 작성해서 확인
    @Schema(example = "https://imageUrl.url", nullable = false)
    private List<String> contentImgUrls;

    @Schema(example = "java")
    private List<String> hashTags;

}

