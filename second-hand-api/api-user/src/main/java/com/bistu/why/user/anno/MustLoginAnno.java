package com.bistu.why.user.anno;

import java.lang.annotation.*;

/**
 * @author why
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MustLoginAnno {

}
