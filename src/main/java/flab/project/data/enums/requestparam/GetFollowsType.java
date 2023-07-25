package flab.project.data.enums.requestparam;

import lombok.Getter;

@Getter
public enum GetFollowsType {
    FOLLOWERS("GET_FOLLOWERS_REQUEST"),
    FOLLOWINGS("GET_FOLLOWINGS_REQUEST");
    private final String requestType;

    private GetFollowsType(String requestType) {
        this.requestType = requestType;
    }
}
