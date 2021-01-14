package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;
import com.healthcode.service.impl.ClazzServiceImpl;
import com.healthcode.utils.IntegerUtil;
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
@WebServlet(urlPatterns = "/admin/getClass")
public class GetClassServlet extends HttpServlet {
    private final IClazzService clazzService = new ClazzServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = IntegerUtil.parseInt(req.getParameter("id"));
        Clazz clazz = clazzService.getClazzById(id);
        resp.getOutputStream().write(JsonUtil.writeValue(clazz));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
