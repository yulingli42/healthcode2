package com.healthcode.servlet.admin;

import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;
import com.healthcode.utils.IntegerUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/insertStudent")
public class InsertStudentServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String idCard = req.getParameter("idCard");
        Integer classId = IntegerUtil.parseInt(req.getParameter("classId"));
        studentService.insertStudent(id, name, classId, idCard);
    }
}
