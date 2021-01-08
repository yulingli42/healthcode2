package com.healthcode.filter;

import cn.hutool.http.HttpStatus;
import com.google.common.collect.Maps;
import com.healthcode.common.HealthCodeException;
import com.healthcode.utils.JsonUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author qianlei
 */
@WebFilter(urlPatterns = "/*")
public class ExceptionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (HealthCodeException e) {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("message", e.getMessage());
            res.getOutputStream().write(JsonUtil.writeValue(map));
            res.setStatus(HttpStatus.HTTP_BAD_REQUEST);
        }
    }
}
