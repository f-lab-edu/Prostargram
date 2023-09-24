package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static flab.project.data.enums.PostType.DEBATE;

//@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddDebatePostRequest extends AddPostRequest{
    private static final PostType postType = DEBATE;

    @Size(min = 2, max = 2)
    private List<String> optionContents;
}
