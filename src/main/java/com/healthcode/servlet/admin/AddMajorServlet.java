package com.healthcode.servlet.admin;

import com.healthcode.service.ICollegeService;
import com.healthcode.service.IMajorService;
import com.healthcode.service.impl.CollegeServiceImpl;
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
@WebServlet(urlPatterns = "/admin/addMajor")
public class AddMajorServlet extends HttpServlet {
    private final IMajorService majorService = new MajorServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Integer collegeId = IntegerUtil.parseInt(req.getParameter("collegeId"));
        majorService.addMajor(collegeId, name);
    }
}
