package flab.project.data.dto.request;

import flab.project.data.dto.model.BasicPostInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드를 작성하는 Dto")
public class CreatePostRequestDto extends BasicPostInfo {

}

