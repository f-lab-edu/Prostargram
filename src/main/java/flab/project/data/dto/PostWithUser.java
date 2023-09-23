package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import flab.project.data.dto.model.BasePost;

@Getter
@RequiredArgsConstructor
public class PostWithUser {

    private final BasePost post;

    private final BasicUser basicUser;
}