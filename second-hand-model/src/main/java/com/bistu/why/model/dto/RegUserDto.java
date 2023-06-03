package com.bistu.why.model.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * @author why
 */
@Data
public class RegUserDto {
    @Length(min = 6, max = 12,message = "用户名大于6位小于12位")
    private String uname;

    @Length(min = 11,max = 11,message = "手机号11位")
    private String phone;
    /**
     *
     */
    @Length(min = 6,max = 12,message = "密码大于6位小于12位")
    private String passwd;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;


    private String code;

}
