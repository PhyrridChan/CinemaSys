package com.test.controller.cusMainPage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.theaterInfoContentMaker;

public class TheaterInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String movieID = request.getParameter("movieID");
//        String time = request.getParameter("time");
        String content = theaterInfoContentMaker("Info", id, movieID);
        out.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
