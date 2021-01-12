package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Major;
import com.healthcode.service.IMajorService;
import com.healthcode.service.impl.MajorServiceImpl;
import com.healthcode.utils.JsonUtil;

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
@WebServlet(urlPatterns = "/admin/getMajorByCollegeId")
public class GetMajorByCollegeIdServlet extends HttpServlet {
    private final IMajorService majorService = new MajorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer collegeId = Integer.valueOf(req.getParameter("collegeId"));
        List<Major> majors = majorService.getAllMajorByCollegeId(collegeId);
        resp.getOutputStream().write(JsonUtil.writeValue(majors));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
