package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.College;
import com.healthcode.service.ICollegeService;
import com.healthcode.service.impl.CollegeServiceImpl;
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
@WebServlet(urlPatterns = "/admin/getCollege")
public class GetCollegeServlet extends HttpServlet {
    private final ICollegeService collegeService = new CollegeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        College college = collegeService.getCollegeById(id);
        resp.getOutputStream().write(JsonUtil.writeValue(college));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
