package com.test.controller.cusMainPage;

import pageConstructor.ContentMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.*;
import static pageConstructor.ContentMaker.mainContentMaker;

public class moviesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String[] parasArray = request.getParameterValues("choose");
        String paras = "";
        if (parasArray != null) for (String s : parasArray) paras = s;
        String content = "";
        if (ContentMaker.getNavStr() != null) content = moviesContentMaker("movies", 1, paras);
        else content = moviesContentMaker("movies", ContentMaker.login(request.getCookies()), 1, paras);
        out.write(content);
    }
}
