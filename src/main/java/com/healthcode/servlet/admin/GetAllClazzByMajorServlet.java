package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;
import com.healthcode.service.impl.ClazzServiceImpl;
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
@WebServlet(urlPatterns = "/admin/getAllClazzByMajor")
public class GetAllClazzByMajorServlet extends HttpServlet {
    private final IClazzService clazzService = new ClazzServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer majorId = Integer.valueOf(req.getParameter("majorId"));
        List<Clazz> list = clazzService.getAllClazzByMajor(majorId);
        resp.getOutputStream().write(JsonUtil.writeValue(list));resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
