package com.healthcode.servlet.admin;

import com.healthcode.service.IAdminService;
import com.healthcode.service.impl.AdminServiceImpl;
import com.healthcode.utils.IntegerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/deleteAdmin")
public class DeleteAdminServlet extends HttpServlet {
    private final IAdminService adminService = new AdminServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = IntegerUtil.parseInt(req.getParameter("id"));
        adminService.deleteById(id);
    }
}
