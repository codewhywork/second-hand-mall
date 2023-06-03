package com.bistu.why.admin.config;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author why
 */
//@Component
public class MyDelegatingPasswordEncoderConfig implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String[] passwd = rawPassword.toString().split("}");
        String encode = null;
        if (passwd[0].substring(1).equals("noop")) {
            encode = NoOpPasswordEncoder.getInstance().encode(passwd[1]);
        }
        return encode;
    }


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] passwd = encodedPassword.split("}");
        if (passwd[0].substring(1).equals("noop")) {
            return rawPassword.equals(passwd[1]);
        }
        return false;
    }
}
