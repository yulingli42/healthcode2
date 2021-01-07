package com.healthcode.servlet;

import com.healthcode.domain.Admin;
import com.healthcode.service.IAdminService;
import com.healthcode.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * 用户登录
 *
 * @author qianlei
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/api/login.do")
public class LoginServlet extends HttpServlet {
    private final IAdminService adminService = new AdminServiceImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (Objects.isNull(type)) {
            request.setAttribute("message", "未填写用户角色信息");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        switch (type) {
            case "student":
                //TODO 学生登陆
                break;
            case "teacher":
                // TODO  教师登陆处理
            case "admin":
                Admin user = adminService.login(username, password);
                if (Objects.isNull(user)) {
                    request.setAttribute("message", "用户名或密码错误");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute(LOGIN_USER_SESSION, user);
                    response.sendRedirect("/admin/listAllCollege.do");
                }
                break;
            default:
                request.setAttribute("message", "不支持的用户类型");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
