package com.ecnu.compiler.rbac.interceptor;

import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionRequiredInterceptor extends HandlerInterceptorAdapter {

    private String mappingURL;

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // get session
        HttpSession session = request.getSession(false);

        //判断请求URL是否合法
        String url = request.getRequestURI();
        if (mappingURL != null && !url.matches(mappingURL)) {
            return false;
        }

        if(ObjectUtils.isEmpty(session)
                || ObjectUtils.isEmpty(session.getAttribute(Constants.USER_ONLINE))){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendRedirect(request.getContextPath() + Constants.URL_LOGIN);
            return false;
        }

        //request valid
        return true;
    }
}
