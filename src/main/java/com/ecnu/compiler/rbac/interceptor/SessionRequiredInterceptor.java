package com.ecnu.compiler.rbac.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionRequiredInterceptor extends HandlerInterceptorAdapter {

    private String mappingURL;

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
        //判断请求URL是否合法
        String url = request.getRequestURI();
        if (mappingURL != null && !url.matches(mappingURL)) {
            return false;
        }
//
//        //判断用户是否登录
//        if (UserUtils.fetchUser().equals(User.nullUser())) {
//            responseException(new Resp(HttpRespCode.USER_NOT_LOGIN), response);
//            return false;
//        }
        return true;
    }
}
