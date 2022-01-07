package com.test.controller.cusMainPage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.movieInfoContentMaker;

public class MovieInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
//        String id = "0012141848";
        String content = movieInfoContentMaker("Info",id);
        out.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
