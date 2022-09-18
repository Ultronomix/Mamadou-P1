package com.revature.ers.common;

import com.revature.ers.users.UserResponse;

public class SecurityUtils {

    public static boolean isDirector(UserResponse subject) {
        return subject.getRoleId().equals("Manager");
    }

    // Only to be used with GET user requests
    public static boolean requesterOwned(UserResponse subject, String resourceId) {
        return subject.getUserId().equals(resourceId);
    }
}