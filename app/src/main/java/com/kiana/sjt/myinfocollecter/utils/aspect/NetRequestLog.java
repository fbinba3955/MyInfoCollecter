package com.kiana.sjt.myinfocollecter.utils.aspect;

import com.blankj.utilcode.util.LogUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NetRequestLog {

    private final String POINT_CONTENT = "execution(* com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil.doGetData(..))";

    @Pointcut(POINT_CONTENT)
    public void getDataMethod(){}

    @Before("getDataMethod()")
    public void insertLog(JoinPoint point) {

        LogUtils.eTag("pointcut","askdjflksajfl;asjl;sjf;dls");
    }
}
