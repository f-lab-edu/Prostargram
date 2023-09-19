package flab.project.data.dto;

import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostWithUser {

    private final BasePost post;

    private final BasicUser basicUser;
}