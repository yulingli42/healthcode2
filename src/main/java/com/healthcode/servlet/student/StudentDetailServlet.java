package com.healthcode.servlet.student;

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

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/student/cardStatus")
public class StudentDetailServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = (Student) req.getSession().getAttribute(LOGIN_USER_SESSION);
        Boolean dailyCardSubmit = studentService.checkStudentDailyCardToday(student);
        resp.getOutputStream().write(JsonUtil.writeValue(dailyCardSubmit));
    }
}
