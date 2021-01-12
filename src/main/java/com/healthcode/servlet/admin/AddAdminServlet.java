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

import static com.healthcode.domain.Admin.AdminRole;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/addAdmin")
public class AddAdminServlet extends HttpServlet {
    private final IAdminService adminService = new AdminServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        AdminRole type = AdminRole.valueOf(req.getParameter("type"));
        Integer collegeId = IntegerUtil.parseInt(req.getParameter("collegeId"));
        adminService.insertAdmin(name, password, type, collegeId);
    }
}
