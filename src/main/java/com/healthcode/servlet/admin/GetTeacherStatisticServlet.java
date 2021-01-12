package com.healthcode.servlet.admin;

import cn.hutool.http.ContentType;
import com.healthcode.service.ITeacherService;
import com.healthcode.service.impl.TeacherServiceImpl;
import com.healthcode.utils.IntegerUtil;
import com.healthcode.utils.JsonUtil;
import com.healthcode.vo.TeacherDailyCardStatistic;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/admin/getTeacherStatistic")
public class GetTeacherStatisticServlet extends HttpServlet {
    private final ITeacherService teacherService = new TeacherServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer collegeId = IntegerUtil.parseInt(request.getParameter("collegeId"));
        TeacherDailyCardStatistic statistic = teacherService.getTeacherStatistic(collegeId);
        response.getOutputStream().write(JsonUtil.writeValue(statistic));
        response.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
