package flab.project.data.dto.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder
public class AddPostRequest {
    protected long postId;

    @NotBlank
    @Length(max = 2000)
    protected String content;

    protected List<@NotBlank String> hashTagNames;
}
