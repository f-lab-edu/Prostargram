package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "토론 게시물 Dto")
public class DebatePost extends BasePost {

    private List<Option> options;

    private Integer selectedOptionId;
}