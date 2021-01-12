package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Admin;
import com.healthcode.service.IAdminService;
import com.healthcode.service.impl.AdminServiceImpl;
import com.healthcode.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/getAllAdmin")
public class GetAllAdminServlet extends HttpServlet {
    private final IAdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Admin> admins = adminService.findAll();
        resp.getOutputStream().write(JsonUtil.writeValue(admins));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
