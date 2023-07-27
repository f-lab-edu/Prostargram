package flab.project.data.dto;

import flab.project.data.dto.domain.BasePost;
import flab.project.data.dto.domain.BasicUser;

public class PostResponseDto {
    private final BasePost basePost;
    private final BasicUser basicUser;

    public PostResponseDto(BasePost basePost, BasicUser basicUser) {
        this.basePost = basePost;
        this.basicUser = basicUser;
    }
}
