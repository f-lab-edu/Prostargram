package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.Size;
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

    protected Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    protected abstract PostType getPostType();
}