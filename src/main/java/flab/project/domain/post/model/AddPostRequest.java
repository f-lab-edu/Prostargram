package flab.project.domain.post.model;

import flab.project.domain.post.enums.PostType;
import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class AddPostRequest {

    protected long postId;

    @NotBlank
    @Length(max = 2000)
    protected String content;

    @Size(max = 5)
    protected Set<@NotBlank @Length(max = 15) String> hashTagNames;

    @Builder.Default
    protected Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    protected abstract PostType getPostType();
}