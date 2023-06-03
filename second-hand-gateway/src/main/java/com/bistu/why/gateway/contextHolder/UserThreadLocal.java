package com.bistu.why.gateway.contextHolder;


import com.bistu.why.gateway.threadlocalVo.UserAuthInfo;

/**
 * @author why
 */

public class UserThreadLocal {
    private UserThreadLocal() {
    }

    private static class LazyCreateUserAuthThreadLocal {
        private static final ThreadLocal<UserAuthInfo> USER_AUTH_INFO_THREAD_LOCAL = new ThreadLocal<>();
    }

    public static ThreadLocal<UserAuthInfo> geteUserThreadLocal() {
        return LazyCreateUserAuthThreadLocal.USER_AUTH_INFO_THREAD_LOCAL;
    }
}

