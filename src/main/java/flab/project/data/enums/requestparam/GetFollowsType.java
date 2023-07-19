package flab.project.data.enums.requestparam;

import lombok.Getter;

@Getter
public enum GetFollowsType {
    FOLLOWER("GET_FOLLOWERS_REQUEST"),
    FOLLOWING("GET_FOLLOWINGS_REQUEST");
    private final String requestType;

    private GetFollowsType(String requestType) {
        this.requestType = requestType;
    }
}
