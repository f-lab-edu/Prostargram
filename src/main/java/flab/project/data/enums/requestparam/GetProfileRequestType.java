package flab.project.data.enums.requestparam;

public enum GetProfileRequestType {
    GET("PROFILE_PAGE_REQUEST"),
    UPDATE("UPDATE_PAGE_REQUEST");

    private final String requestType;

    private GetProfileRequestType(String requestType) {
        this.requestType = requestType;
    }
}
