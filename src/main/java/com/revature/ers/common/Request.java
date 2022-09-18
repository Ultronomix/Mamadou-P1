package com.revature.ers.common;

import com.revature.ers.users.User;

public interface Request<U> {
    User extractEntity();
}
