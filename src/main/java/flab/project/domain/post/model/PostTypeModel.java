package flab.project.domain.post.model;

import flab.project.domain.post.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeModel {
    private long postId;
    private PostType postType;
}
