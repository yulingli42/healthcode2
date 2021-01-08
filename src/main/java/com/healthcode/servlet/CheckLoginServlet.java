package com.healthcode.servlet;

import com.healthcode.domain.Admin;
import com.healthcode.domain.Student;
import com.healthcode.domain.Teacher;
import com.healthcode.utils.JsonUtil;
import com.healthcode.vo.LoginUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/checkLoginStatus")
public class CheckLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute(LOGIN_USER_SESSION);
        LoginUser loginUser = null;
        if (user == null) {
            loginUser = new LoginUser();
        } else if (user instanceof Student) {
            loginUser = new LoginUser("student", ((Student) user).getName());
        } else if (user instanceof Teacher) {
            loginUser = new LoginUser("teacher", ((Teacher) user).getName());
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            switch (admin.getRole()) {
                case SCHOOL_ADMIN:
                    loginUser = new LoginUser("schoolAdmin", ((Admin) user).getUsername());
                    break;
                case SYSTEM_ADMIN:
                    loginUser = new LoginUser("systemAdmin", ((Admin) user).getUsername());
                    break;
                case COLLEGE_ADMIN:
                    loginUser = new LoginUser("collegeAdmin", ((Admin) user).getUsername());
                    break;
                default:
            }
        }
        resp.getOutputStream().write(JsonUtil.writeValue(loginUser));
    }
}
