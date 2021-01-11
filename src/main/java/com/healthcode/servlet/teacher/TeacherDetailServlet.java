package com.healthcode.servlet.teacher;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Teacher;
import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.TeacherServiceImpl;
import com.healthcode.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

@WebServlet(urlPatterns = "/teacher/cardStatus")
public class TeacherDetailServlet extends HttpServlet {
    private final ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = (Teacher) req.getSession().getAttribute(LOGIN_USER_SESSION);
        Boolean dailyCardSubmit = teacherService.getDailyCardStatus(teacher);
        resp.getOutputStream().write(JsonUtil.writeValue(dailyCardSubmit));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
