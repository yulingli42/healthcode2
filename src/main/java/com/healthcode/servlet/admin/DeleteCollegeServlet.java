package com.healthcode.servlet.admin;

import com.healthcode.service.ICollegeService;
import com.healthcode.service.impl.CollegeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/deleteCollege")
public class DeleteCollegeServlet extends HttpServlet {
    private ICollegeService collegeService = new CollegeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        collegeService.deleteById(id);
    }
}
