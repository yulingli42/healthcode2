package com.healthcode.servlet.teacher;

import com.healthcode.domain.Teacher;
import com.healthcode.service.impl.TeacherServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author zhenghong
 */
@WebServlet(urlPatterns = "/teacher/qrcode")
public class TeacherQRCodeServlet extends HttpServlet {
    private final TeacherServiceImpl teacherService = new TeacherServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //教师二维码展示
        Teacher teacher = (Teacher) req.getSession().getAttribute(LOGIN_USER_SESSION);
        resp.getOutputStream().write(teacherService.showHealthCode(teacher));
    }
}
