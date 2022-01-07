package com.test.controller.Customer;

import pageConstructor.ContentMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.cinemaContentMaker;

///chooseCinema
public class ChooseCinemaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String content = "";
        String id = request.getParameter("id");

        if (ContentMaker.getNavStr() != null) content = cinemaContentMaker("theaters", id);
        else content = cinemaContentMaker("theaters", id, ContentMaker.login(request.getCookies()));

        out.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
