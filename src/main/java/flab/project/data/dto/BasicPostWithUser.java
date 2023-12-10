package flab.project.data.dto;

import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.BasicUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicPostWithUser {

    private BasicPost basicPost;

    private BasicUser basicUser;
}
