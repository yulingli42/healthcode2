package com.healthcode.servlet.admin;

import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.utils.IntegerUtil;
import com.healthcode.utils.JsonUtil;
import com.healthcode.vo.StudentDailyCardStatistic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/getStudentStatistic")
public class GetStudentStatisticServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer clazzId = IntegerUtil.parseInt(request.getParameter("clazzId"));
        Integer majorId = IntegerUtil.parseInt(request.getParameter("majorId"));
        Integer collegeId = IntegerUtil.parseInt(request.getParameter("collegeId"));
        StudentDailyCardStatistic statistic = studentService.getStudentStatistic(clazzId, majorId, collegeId);
        response.getOutputStream().write(JsonUtil.writeValue(statistic));
    }


}
