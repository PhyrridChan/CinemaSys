package com.test.controller.cusMainPage;

import pageConstructor.ContentMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.*;

public class rankingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        String content = "";
        if (ContentMaker.getNavStr() != null) content = rankingContentMaker("ranking");
        else content = rankingContentMaker("ranking", ContentMaker.login(request.getCookies()));
        System.out.println(content);
        out.write(content);
    }
}
