package com.healthcode.servlet.admin;

import com.healthcode.service.IStudentService;
import com.healthcode.service.impl.StudentServiceImpl;
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
@WebServlet(urlPatterns = "/admin/updateStudent")
public class UpdateStudentServlet extends HttpServlet {
    private final IStudentService studentService = new StudentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Integer classId = IntegerUtil.parseInt(req.getParameter("classId"));
        String name = req.getParameter("name");
        String idCard = req.getParameter("idCard");
        studentService.updateStudent(id, null, classId, name, idCard);
    }
}
