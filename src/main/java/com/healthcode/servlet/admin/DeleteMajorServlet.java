package com.healthcode.servlet.admin;

import com.healthcode.service.IMajorService;
import com.healthcode.service.impl.MajorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/deleteMajor")
public class DeleteMajorServlet extends HttpServlet {
    private IMajorService majorService=new MajorServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        majorService.deleteById(id);
    }
}
