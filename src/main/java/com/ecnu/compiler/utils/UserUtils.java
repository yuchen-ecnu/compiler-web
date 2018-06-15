package com.ecnu.compiler.utils;

import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class UserUtils {

    /**
     * 获取当前用户
     */
    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if(ObjectUtils.isEmpty(session)){
            return null;
        }else{
            return (User) session.getAttribute(Constants.USER_ONLINE);
        }
    }

    /**
     * 注册当前用户
     */
    public static void registerCurrentUser(User user) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Constants.USER_ONLINE, user);
    }
}
