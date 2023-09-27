package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import static flab.project.data.enums.PostType.DEBATE;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddDebatePostRequest extends AddPostRequest {

    private static final PostType postType = DEBATE;

    @Size(min = 2, max = 2)
    private Set<@NotBlank @Length(max = 35) String> optionContents;
}