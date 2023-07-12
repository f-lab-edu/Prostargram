package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema
public class GetPostResponseDto {
    // PostAuthor 및 CommentAuthor 코드 추가 시, 반영 예정
    // ex. PostAuthor.userId, PostAuthor.userName, ..
}
