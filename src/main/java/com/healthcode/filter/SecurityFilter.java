package com.healthcode.filter;

import cn.hutool.http.HttpStatus;
import com.google.common.collect.Maps;
import com.healthcode.domain.Admin;
import com.healthcode.domain.Student;
import com.healthcode.domain.Teacher;
import com.healthcode.utils.JsonUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebFilter(urlPatterns = "/*")
public class SecurityFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println(req.getPathInfo());
        System.out.println(req.getPathTranslated());
        System.out.println(req.getContextPath());
        System.out.println(req.getServletPath());
        Object user = req.getSession().getAttribute(LOGIN_USER_SESSION);
        if (req.getServletPath().startsWith("/admin/")) {
            if (user == null) {
                sendUnauthorized(res);
                return;
            } else if (!(user instanceof Admin)) {
                sendForbiddenMessage(res);
                return;
            }
        } else if (req.getServletPath().startsWith("/student/")) {
            if (user == null) {
                sendUnauthorized(res);
                return;
            } else if (!(user instanceof Student)) {
                sendForbiddenMessage(res);
                return;
            }
        } else if (req.getServletPath().startsWith("/teacher/")) {
            if (user == null) {
                sendUnauthorized(res);
                return;
            } else if (!(user instanceof Teacher)) {
                sendForbiddenMessage(res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private void sendUnauthorized(HttpServletResponse res) throws IOException {
        Map<Object, Object> message = Maps.newHashMap();
        message.put("message", "用户未登录");
        res.getOutputStream().write(JsonUtil.writeValue(message));
        res.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
    }

    private void sendForbiddenMessage(HttpServletResponse res) throws IOException {
        Map<Object, Object> message = Maps.newHashMap();
        message.put("message", "无权限访问");
        res.getOutputStream().write(JsonUtil.writeValue(message));
        res.setStatus(HttpStatus.HTTP_FORBIDDEN);
    }
}
