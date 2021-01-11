package com.healthcode.servlet;

import cn.hutool.http.ContentType;
import com.healthcode.common.HealthCodeException;
import com.healthcode.domain.Admin;
import com.healthcode.domain.Student;
import com.healthcode.domain.Teacher;
import com.healthcode.service.IAdminService;
import com.healthcode.service.IStudentService;
import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.AdminServiceImpl;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.service.impl.TeacherServiceImpl;
import com.healthcode.utils.JsonUtil;
import com.healthcode.vo.LoginUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * 用户登录
 *
 * @author qianlei
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final IAdminService adminService = new AdminServiceImpl();
    private final IStudentService studentService = new StudentServiceImpl();
    private final ITeacherService teacherService = new TeacherServiceImpl();

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
        LoginUser user;
        switch (type) {
            case "student":
                Student student = studentService.login(username, password);
                request.getSession().setAttribute(LOGIN_USER_SESSION, student);
                user = new LoginUser(student);
                break;
            case "teacher":
                Teacher teacher = teacherService.login(username, password);
                request.getSession().setAttribute(LOGIN_USER_SESSION, teacher);
                user = new LoginUser(teacher);
                break;
            case "admin":
                Admin admin = adminService.login(username, password);
                request.getSession().setAttribute(LOGIN_USER_SESSION, admin);
                user = new LoginUser(admin);
                break;
            default:
                throw new HealthCodeException("不支持的用户类型");
        }
        response.getOutputStream().write(JsonUtil.writeValue(user));
        response.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
