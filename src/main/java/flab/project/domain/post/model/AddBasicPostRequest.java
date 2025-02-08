package flab.project.domain.post.model;

import static flab.project.domain.post.enums.PostType.BASIC;

import flab.project.domain.post.enums.PostType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class AddBasicPostRequest extends AddPostRequest {

    private static final PostType postType = BASIC;

    private Set<String> contentUrls;

    @Schema(hidden = true)
    private long postId;

    @Override
    protected PostType getPostType() {
        return postType;
    }
}