package com.atguigu.spzx.common.log.aspect;


import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncSysOperLogService;
import com.atguigu.spzx.common.service.utils.AuthContextUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;

@Aspect
@Component
public class LogginAspect {
    @Autowired
    private AsyncSysOperLogService asyncSysOperLogService;

    /**
     *      通知方法(环绕通知)
     *      1.before方法，用于给日志对象设置属性
     *      2.执行目标方法
     *      3.after方法，用于给日志对象持久化
     *          要注入对应的service对象以调用mapper进行持久化
     *          要方法重载，设置出现异常时的处理方案
     */
    @Pointcut(value = "@annotation(com.atguigu.spzx.common.log.annotation.Log)")
    public void log(){}

//    @Around(value = "@annotation(sysLog)")
    @Around("log()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

//        String methdoClassName = proceedingJoinPoint.getTarget().getClass().getName();
//        Class<?> aClass = Class.forName(methdoClassName);
//        Log sysLog = aClass.getDeclaredAnnotation(Log.class);   错误演示(获取了方法所在类的注解，而不是方法自己的注解)

        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Log sysLog = methodSignature.getMethod().getAnnotation(Log.class);

        SysOperLog sysOperLog = new SysOperLog();

        try {
            // 封装日志对象属性
            beforeHandleLog(sysLog,proceedingJoinPoint,sysOperLog);
            // 执行目标方法 获取返回值
            Object result = proceedingJoinPoint.proceed();
            // 执行后置方法 确定是否保存日志对象，并按结果执行保存
            afterHandleLog(sysLog,result,null,sysOperLog);
            // 返回结果集
            return result;
        } catch (Throwable e) {
            // 处理异常的后置方法
            afterHandleLog(sysLog,null,e,sysOperLog);
            throw new RuntimeException(e);
        }

    }


    private void beforeHandleLog(Log sysLog,ProceedingJoinPoint proceedingJoinPoint,SysOperLog sysOperLog){
           /*
            sysOperLog对象中的其他属性通过以下三个对象获取：
                1.通过proceedingJoinPoint对象获取
                2.通过request对象获取
                3.通过sysLog对象获取
         */

        // 1.设置方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        sysOperLog.setMethod(className + "." +methodName + "()");

        // 2.获取request对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 设置请求方式
        sysOperLog.setRequestMethod(request.getMethod());
        // 设置请求地址
        sysOperLog.setOperUrl(request.getRequestURI());
        // 设置操作人的ip地址
        sysOperLog.setOperIp(request.getRemoteAddr());
        // 设置操作人的账号姓名
        sysOperLog.setOperName(AuthContextUtil.getThreadLocal().getUsername());
        // 判断是否保存请求参数
        if (sysLog.isSaveRequestData()){
            if ("put".equalsIgnoreCase(request.getMethod()) || "post".equalsIgnoreCase(request.getMethod())){
                Object[] args = proceedingJoinPoint.getArgs();
                sysOperLog.setOperParam(argsArrayToString(args));
            }
        }
        // 设置操作模块
        sysOperLog.setTitle(sysLog.title());
        // 设置操作人类型
        sysOperLog.setOperatorType(sysLog.operatorType().name());
        // 设置业务类型
        sysOperLog.setBusinessType(sysLog.businessType().name());

    }

    private void afterHandleLog(Log sysLog,Object result,Throwable e,SysOperLog sysOperLog){

        if (e != null){
            sysOperLog.setStatus(1);
            sysOperLog.setErrorMsg(e.getMessage());
        }else{
            sysOperLog.setStatus(0);
        }

        if (sysLog.isSaveRequestData()){
            String jsonString = JSON.toJSONString(result);
            sysOperLog.setJsonResult(jsonString);
        }
        asyncSysOperLogService.saveOperLog(sysOperLog);

    }

    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {       // 判断是否为数组类型
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);  // 如果是数组，判断其元素类型是否为MultipartFile或其子类
        } else if (Collection.class.isAssignableFrom(clazz)) { // 判断是否为Collection集合类型
            Collection collection = (Collection) o;
            for (Object value : collection) {  // 只要有一个元素的类型为MultipartFile或其子类，则认为该集合对象为过滤对象
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {  // 判断是否为Map集合类型
            Map map = (Map) o;
            for (Object value : map.entrySet()) {  // 只要有一个Value的类型是MultipartFile或其子类，则认为该映射对象为过滤对象
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }

        // 如果以上条件都不满足，最后判断对象本身是否为MultipartFile、HttpServletRequest、HttpServletResponse类的实例
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }



}
