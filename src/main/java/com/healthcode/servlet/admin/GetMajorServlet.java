package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Major;
import com.healthcode.service.IMajorService;
import com.healthcode.service.impl.MajorServiceImpl;
import com.healthcode.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/getMajor")
public class GetMajorServlet extends HttpServlet {
    private final IMajorService majorService = new MajorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        Major major = majorService.getMajorById(id);
        resp.getOutputStream().write(JsonUtil.writeValue(major));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
