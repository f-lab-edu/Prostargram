package flab.project.utils;

import flab.project.config.exception.ForbiddenAccessException;

public class AccessManagementUtil {

    public static void assertUserIdOwner(long loggedInUserId, long userIdFromParameter) {
        if(loggedInUserId != userIdFromParameter){
            throw new ForbiddenAccessException();
        }
    }

}
