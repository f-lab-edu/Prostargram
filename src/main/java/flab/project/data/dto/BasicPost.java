package flab.project.data.dto;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 Dto")
public class BasicPost extends BasePost {

    @Schema(example = "https://imageUrl.url", nullable = false)
    private final List<String> contentImgUrls;

    public BasicPost(long userId, long postId, String content, List<String> hashTags, PostType postType, long likeCount, long commentCount, String createTime, List<String> contentImgUrls) {
        super(userId, postId, content, hashTags, postType, likeCount, commentCount, createTime);
        this.contentImgUrls = contentImgUrls;
    }
}
