package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static flab.project.common.Constraints.COUNT_OF_DEBATE_POST_OPTION_COUNT;
import static flab.project.data.enums.PostType.DEBATE;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddDebatePostRequest extends AddPostRequest {
    private static final PostType postType = DEBATE;

    @Size(min = 2, max = 2)
    private Set<@NotBlank @Length(max = 35) String> optionContents;
}
