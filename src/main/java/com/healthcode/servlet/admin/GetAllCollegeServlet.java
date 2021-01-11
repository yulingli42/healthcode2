package com.healthcode.servlet.admin;


import cn.hutool.http.ContentType;
import com.google.common.collect.Lists;
import com.healthcode.domain.Admin;
import com.healthcode.domain.College;
import com.healthcode.service.ICollegeService;
import com.healthcode.service.impl.CollegeServiceImpl;
import com.healthcode.utils.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/getAllCollege")
public class GetAllCollegeServlet extends HttpServlet {
    private final ICollegeService collegeService = new CollegeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Admin admin = (Admin) req.getSession().getAttribute(LOGIN_USER_SESSION);
        List<College> colleges;
        if (admin.getRole() == Admin.AdminRole.COLLEGE_ADMIN) {
            colleges = Lists.newArrayList(collegeService.getCollegeById(admin.getCollege().getId()));
        } else {
            colleges = collegeService.getAllCollege();
        }
        resp.getOutputStream().write(JsonUtil.writeValue(colleges));
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
