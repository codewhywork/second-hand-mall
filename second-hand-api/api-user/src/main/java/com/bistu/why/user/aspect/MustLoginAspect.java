package com.bistu.why.user.aspect;

import com.bistu.why.contextHolder.UserThreadLocal;
import com.bistu.why.model.threadlocalVo.UserAuthInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author why
 */
@Aspect
@Component
public class MustLoginAspect {
    @Pointcut("@annotation(com.bistu.why.user.anno.MustLoginAnno)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object doLoginFilter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UserAuthInfo userAuthInfo = UserThreadLocal.geteUserThreadLocal().get();
        if (userAuthInfo == null || userAuthInfo.isAuth()) {
            return "您还未登录";
        }
        return proceedingJoinPoint.proceed();
    }
}
