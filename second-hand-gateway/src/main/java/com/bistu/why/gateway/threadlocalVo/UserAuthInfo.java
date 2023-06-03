package com.bistu.why.gateway.threadlocalVo;

import lombok.Data;

/**
 * @author why
 */
@Data
public class UserAuthInfo {
    private String userId;
    private String uname;
    private String img;
    private boolean isAuth;
}
