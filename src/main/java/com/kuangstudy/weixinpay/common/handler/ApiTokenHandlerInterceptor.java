package com.kuangstudy.weixinpay.common.handler;


import com.kuangstudy.weixinpay.anno.IgnoreToken;
import com.kuangstudy.weixinpay.common.threadlocal.UserThreadLocal;
import com.kuangstudy.weixinpay.utils.json.JsonUtil;
import com.kuangstudy.weixinpay.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

// 登录拦截
public class ApiTokenHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1：从 http 请求头获取请求接口
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //2：检查是否有IgnoreToken注释，有则跳过认证
        if (method.isAnnotationPresent(IgnoreToken.class)) {
            IgnoreToken loginToken = method.getAnnotation(IgnoreToken.class);
            if (loginToken.required()) {
                return true;
            }
        }

        //3：从请求头中获取token
        String tokenvalue = getParam(request, "token");
        if(StringUtils.isEmpty(tokenvalue)){
            Map<String, Object> map = R.error()
                    .data("code",6001)
                    .data("message","token is empty").toMap();
            response.getWriter().print(JsonUtil.obj2String(map));
            return false;
        }

        if(StringUtils.isNotEmpty(tokenvalue) && !tokenvalue.equalsIgnoreCase("www.feifei.com")){
            Map<String, Object> map = com.kuangstudy.weixinpay.vo.R.error()
                    .data("code",6001)
                    .data("message","tokenerror").toMap();
            response.getWriter().print(JsonUtil.obj2String(map));
            return false;
        }

        return true;
    }

    /**
     * 获取参数
     * @param request
     * @param filedName
     * @return
     */
    public static String getParam(HttpServletRequest request, String filedName) {
        String param = request.getParameter(filedName);
        if(StringUtils.isEmpty(param)){
            param = request.getHeader(filedName);
        }
        return param;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserThreadLocal.remove();
    }

    // 此处两个方法，一定都是要preHandle方法返回为true的时候执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserThreadLocal.remove();
    }
}