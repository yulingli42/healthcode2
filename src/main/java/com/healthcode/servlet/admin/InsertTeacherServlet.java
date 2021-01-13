package com.healthcode.servlet.admin;

import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.TeacherServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/insertTeacher")
public class InsertTeacherServlet extends HttpServlet {
    private final ITeacherService teacherService=new TeacherServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String teacherId = req.getParameter("teacherId");
        String name = req.getParameter("name");
        String idCard = req.getParameter("idCard");
        Integer collegeId = Integer.valueOf(req.getParameter("collegeId"));
        teacherService.insertTeacher(teacherId,name,idCard,collegeId);
    }
}
