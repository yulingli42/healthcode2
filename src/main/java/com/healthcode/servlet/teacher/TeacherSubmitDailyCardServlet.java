package com.healthcode.servlet.teacher;

import com.healthcode.domain.Teacher;
import com.healthcode.dto.CurrentDailyCard;
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
@WebServlet(urlPatterns = "/teacher/submit")
public class TeacherSubmitDailyCardServlet extends HttpServlet {
    private final ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacher = (Teacher) req.getSession().getAttribute(LOGIN_USER_SESSION);
        CurrentDailyCard card = new CurrentDailyCard();
        card.setHaveBeenToKeyEpidemicAreas(Boolean.parseBoolean(req.getParameter("haveBeenToKeyEpidemicAreas")));
        card.setHaveBeenAbroad(Boolean.parseBoolean(req.getParameter("haveBeenAbroad")));
        card.setTheExposed(Boolean.parseBoolean(req.getParameter("isTheExposed")));
        card.setSuspectedCase(Boolean.parseBoolean(req.getParameter("isSuspectedCase")));
        card.setCurrentSymptoms(req.getParameterValues("currentSymptoms"));

        teacherService.submitDailyCard(teacher, card);
    }
}
