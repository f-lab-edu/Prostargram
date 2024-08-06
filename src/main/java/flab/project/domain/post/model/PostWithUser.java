package flab.project.domain.post.model;

import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Profile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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