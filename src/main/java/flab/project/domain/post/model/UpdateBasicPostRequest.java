package flab.project.domain.post.model;

import static flab.project.domain.post.enums.PostType.BASIC;

import flab.project.domain.post.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@Builder
public class UpdateBasicPostRequest {

    private static final PostType postType = BASIC;

    protected long postId;

    @NotBlank
    @Length(max = 2000)
    protected String content;

    @Size(max = 5)
    protected Set<@NotBlank @Length(max = 15) String> hashTagNames;

    @Schema(example = "https://imageUrl.url")
    private Set<String> contentImageUrls;

    @Builder
    private UpdateBasicPostRequest(
            long postId,
            String content,
            Set<@NotBlank @Length(max = 15) String> hashTagNames,
            Set<String> contentImageUrls
    ) {
        this.postId = postId;
        this.content = content;
        this.hashTagNames = hashTagNames;
        this.contentImageUrls = contentImageUrls;
    }

    protected PostType getPostType() {
        return postType;
    }

    public BasicPost toEntity(long userId, Timestamp createdAt) {
        return BasicPost.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .hashTagNames(hashTagNames)
                .contentImageUrls(contentImageUrls)
                .createdAt(createdAt)
                .postType(postType)
                .build();
    }
}
