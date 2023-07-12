package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글을 가져오는 응답 Dto")
public class GetAllCommentResponseDto {
    // 대댓글을 구현하기 위한 상속 관계 설계 후, 구현 예정
}