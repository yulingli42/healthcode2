package com.healthcode.servlet.student;

import com.healthcode.domain.Student;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

@WebServlet(urlPatterns = "/student/submit")
public class StudentSubmitDailyCardServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = (Student) req.getSession().getAttribute(LOGIN_USER_SESSION);
        CurrentDailyCard card = new CurrentDailyCard();
        card.setHaveBeenToKeyEpidemicAreas(Boolean.parseBoolean(req.getParameter("haveBeenToKeyEpidemicAreas")));
        card.setHaveBeenAbroad(Boolean.parseBoolean(req.getParameter("haveBeenAbroad")));
        card.setTheExposed(Boolean.parseBoolean(req.getParameter("isTheExposed")));
        card.setSuspectedCase(Boolean.parseBoolean(req.getParameter("isSuspectedCase")));
        card.setCurrentSymptoms(req.getParameterValues("currentSymptoms"));

        studentService.submitDailyCard(student, card);
    }
}
