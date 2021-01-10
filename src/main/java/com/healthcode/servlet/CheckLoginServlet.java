package com.healthcode.servlet;

import com.healthcode.utils.JsonUtil;
import com.healthcode.vo.LoginUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.healthcode.common.Constant.SessionConstant.LOGIN_USER_SESSION;

/**
 * @author qianlei
 */
@WebServlet(urlPatterns = "/checkLoginStatus")
public class CheckLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute(LOGIN_USER_SESSION);
        LoginUser loginUser = new LoginUser(user);
        resp.getOutputStream().write(JsonUtil.writeValue(loginUser));
    }
}
