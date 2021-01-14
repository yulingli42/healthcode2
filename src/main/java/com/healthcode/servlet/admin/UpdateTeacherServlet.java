package com.healthcode.servlet.admin;

import com.healthcode.service.IStudentService;
import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.service.impl.TeacherServiceImpl;
import com.healthcode.utils.IntegerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/updateTeacher")
public class UpdateTeacherServlet extends HttpServlet {
    private final ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Integer collegeId = IntegerUtil.parseInt(req.getParameter("collegeId"));
        String name = req.getParameter("name");
        String idCard = req.getParameter("idCard");
        teacherService.updateTeacher(id, collegeId, name, idCard);
    }
}
