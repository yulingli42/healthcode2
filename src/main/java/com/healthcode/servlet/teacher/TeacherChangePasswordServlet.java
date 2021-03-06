package com.healthcode.servlet.teacher;

import com.healthcode.domain.Teacher;
import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.TeacherServiceImpl;

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
@WebServlet(urlPatterns = "/teacher/changePassword")
public class TeacherChangePasswordServlet extends HttpServlet {
    private ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        Teacher teacher = (Teacher) req.getSession().getAttribute(LOGIN_USER_SESSION);
        teacherService.changePassword(teacher.getId(),oldPassword,newPassword);
    }
}
