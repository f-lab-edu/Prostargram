package flab.project.domain.post.model;

import static flab.project.domain.post.enums.PostType.BASIC;

import flab.project.domain.post.enums.PostType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

@SuperBuilder
@NoArgsConstructor
@Getter
public class AddBasicPostRequest extends AddPostRequest {

    private static final PostType postType = BASIC;

    @Range(min = 1, max = 6)
    private int imageCount;

    @Schema(hidden = true)
    private long postId;

    @Override
    protected PostType getPostType() {
        return postType;
    }
}