package flab.project.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글을 작성하는 Dto")
public class CreateCommentRequestDto {

    // PostAuthor 추가 시, 수정 예정
    @Schema(example = "1")
    private String postId;

    @Schema(example = "1")
    private String userId;

    @Schema(example = "정민욱")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;

    @Schema(example = "방금 전")
    private String createdAt;

    @Schema(example = "요즘도 열심히 알고리즘 풀이를 진행하시네요.", maxLength = 100)
    private String content;

}
