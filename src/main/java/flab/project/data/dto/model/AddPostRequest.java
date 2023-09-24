package flab.project.data.dto.model;

import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@SuperBuilder
public class AddPostRequest {
    protected long postId;

    @NotBlank
    @Length(max = 2000)
    protected String content;

    @Size(max = 5)
    protected Set<@NotBlank @Length(max = 15) String> hashTagNames;
}