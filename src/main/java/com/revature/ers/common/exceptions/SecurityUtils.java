package com.revature.ers.common.exceptions;

import com.revature.ers.users.UserResponse;

public class SecurityUtils {

    public static boolean isDirector(UserResponse subject) {
        return subject.getRoleId().equals("DIRECTOR");
    }

    // Only to be used with GET user requests
    public static boolean requesterOwned(UserResponse subject, String resourceId) {
        return subject.getUserId().equals(resourceId);
    }
}
