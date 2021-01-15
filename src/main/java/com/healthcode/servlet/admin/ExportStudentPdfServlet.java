package com.healthcode.servlet.admin;

import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.utils.IntegerUtil;
import com.healthcode.utils.PdfUtil;
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
@WebServlet(urlPatterns = "/admin/studentPdf")
public class ExportStudentPdfServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer clazzId = IntegerUtil.parseInt(req.getParameter("classId"));
        Integer majorId = IntegerUtil.parseInt(req.getParameter("majorId"));
        Integer collegeId = IntegerUtil.parseInt(req.getParameter("collegeId"));
        StudentDailyCardStatistic statistic = studentService.getStudentStatistic(clazzId, majorId, collegeId);
        resp.getOutputStream().write(PdfUtil.GetPdf("student.pdf", statistic));
    }
}
