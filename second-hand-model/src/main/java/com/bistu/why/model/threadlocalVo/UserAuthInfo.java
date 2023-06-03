package com.bistu.why.model.threadlocalVo;

import lombok.Data;

@Data
public class UserAuthInfo {
    private String userId;
    private String uname;
    private String img;
    private boolean isAuth;
}
