package com.healthcode.servlet.student;

import cn.hutool.http.ContentType;
import com.healthcode.domain.Student;
import com.healthcode.service.impl.StudentServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author zhenghong
 */
@WebServlet(urlPatterns = "/student/qrcode")
public class StudentQRCodeServlet extends HttpServlet {
    private final StudentServiceImpl studentService = new StudentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //显示学生二维码
        Student student = (Student) req.getSession().getAttribute(LOGIN_USER_SESSION);
        resp.getOutputStream().write(studentService.showHealthCode(student));
        resp.setContentType("image/png");
        resp.setContentType(ContentType.build(ContentType.JSON.getValue(), StandardCharsets.UTF_8));
    }
}
