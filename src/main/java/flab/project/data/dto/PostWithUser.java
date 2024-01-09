package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import flab.project.data.dto.model.BasePost;

@Getter
@RequiredArgsConstructor
public class PostWithUser {

    private final BasePost post;

    private final BasicUser basicUser;

    public PostWithUser(BasePost post, Profile profile) {
        this.post = post;
        this.basicUser = BasicUser.builder()
            .userId(profile.getUserId())
            .userName(profile.getUserName())
            .profileImgUrl(profile.getProfileImgUrl())
            .build();
    }
}