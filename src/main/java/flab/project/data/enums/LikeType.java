package flab.project.data.enums;

public enum LikeType {
    LIKE("PATCH_LIKE_REQUEST"),
    UNLIKE("PATCH_LIKE_REQUEST");

    private final String requestType;


    LikeType(String requestType) {
        this.requestType = requestType;
    }
}
