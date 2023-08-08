package flab.project.data.dto.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Schema(description = "토론 게시물 Dto")
public class DebatePost extends BasePost {

    private final List<Option> options;
}
