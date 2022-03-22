package com.duannd.jetty;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.info("contextPath: {}", req.getContextPath());
        LOGGER.info("servletPath: {}", req.getServletPath());
        LOGGER.info("getPathInfo: {}", req.getPathInfo());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"id\":1,\"name\":\"Duan\"}");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
