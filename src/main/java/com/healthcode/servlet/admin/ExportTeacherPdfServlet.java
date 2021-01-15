package com.healthcode.servlet.admin;

import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.TeacherServiceImpl;
import com.healthcode.utils.IntegerUtil;
import com.healthcode.utils.PdfUtil;
import com.healthcode.vo.TeacherDailyCardStatistic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/teacherPdf")
public class ExportTeacherPdfServlet extends HttpServlet {
    private final ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Integer collegeId = IntegerUtil.parseInt(request.getParameter("collegeId"));
        TeacherDailyCardStatistic statistic = teacherService.getTeacherStatistic(collegeId);
        resp.getOutputStream().write(PdfUtil.GetPdf("teacher.pdf", statistic));
    }
}
