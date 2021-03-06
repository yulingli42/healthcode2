package com.healthcode.servlet.student;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Student;
import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/student/cardStatus")
public class StudentDetailServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student student = (Student) req.getSession().getAttribute(LOGIN_USER_SESSION);
        Boolean dailyCardSubmit = studentService.checkStudentDailyCardToday(student);
        resp.getOutputStream().write(JsonUtil.writeValue(dailyCardSubmit));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));

    }
}
