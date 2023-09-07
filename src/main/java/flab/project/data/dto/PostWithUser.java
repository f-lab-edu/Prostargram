package flab.project.data.dto;

import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicUser;

public class PostWithUser {
    private final BasePost basePost;
    private final BasicUser basicUser;

    public PostWithUser(BasePost basePost, BasicUser basicUser) {
        this.basePost = basePost;
        this.basicUser = basicUser;
    }
}
