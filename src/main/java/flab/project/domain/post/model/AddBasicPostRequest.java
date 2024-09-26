package flab.project.domain.post.model;

import static flab.project.domain.post.enums.PostType.BASIC;

import flab.project.domain.post.enums.PostType;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@SuperBuilder
@NoArgsConstructor
@Getter
public class AddBasicPostRequest extends AddPostRequest {

    private static final PostType postType = BASIC;

    @Schema(hidden = true)
    @Setter
    private List<MultipartFile> contentImages;

    @Schema(hidden = true)
    private long postId;

    @Override
    protected PostType getPostType() {
        return postType;
    }
}