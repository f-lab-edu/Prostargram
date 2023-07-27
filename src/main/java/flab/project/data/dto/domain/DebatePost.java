package flab.project.data.dto.domain;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "토론 게시물 Dto")
public class DebatePost extends BasePost {

    private final List<Option> options;

    public DebatePost(long userId, long postId, String content, List<String> hashTags, PostType postType, long likeCount, long commentCount, String createTime, long optionId, List<Option> options) {
        super(userId, postId, content, hashTags, postType, likeCount, commentCount, createTime);
        this.options = options;
    }
}
