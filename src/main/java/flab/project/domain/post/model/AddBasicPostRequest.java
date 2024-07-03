package flab.project.domain.post.model;

import static flab.project.domain.post.enums.PostType.BASIC;

import flab.project.domain.post.enums.PostType;

import java.util.List;

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

    @Setter
    private List<MultipartFile> contentImages;

    @Override
    protected PostType getPostType() {
        return postType;
    }
}