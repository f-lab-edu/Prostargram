package flab.project.data.dto;

import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.BasicUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicPostWithUser {

    private BasicPost basicPost;

    private BasicUser basicUser;
}
