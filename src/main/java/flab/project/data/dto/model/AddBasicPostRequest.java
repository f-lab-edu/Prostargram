package flab.project.data.dto.model;

import static flab.project.data.enums.PostType.BASIC;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
public class AddBasicPostRequest {

    private static final PostType postType= BASIC;

    private long postId;

    @NotBlank
    @Length(max = 2000)
    private String content;

    private List<@NotBlank String> hashTagNames;
}
