package com.healthcode.servlet.admin;

import com.healthcode.service.IClazzService;
import com.healthcode.service.IMajorService;
import com.healthcode.service.impl.ClazzServiceImpl;
import com.healthcode.service.impl.MajorServiceImpl;
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
@WebServlet(urlPatterns = "/admin/addClass")
public class AddClassServlet extends HttpServlet {
    private final IClazzService clazzService = new ClazzServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Integer majorId = IntegerUtil.parseInt(req.getParameter("majorId"));
        clazzService.addClazz(majorId, name);
    }
}
