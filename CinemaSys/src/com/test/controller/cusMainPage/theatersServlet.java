package com.test.controller.cusMainPage;

import pageConstructor.ContentMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.moviesContentMaker;

public class theatersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String regionProvince, regionCity, regionSite;
        regionProvince = request.getParameter("regionProvince");
        regionCity = request.getParameter("regionCity");
        regionSite = request.getParameter("regionSite");
        String content = "";
        if (ContentMaker.getNavStr() != null)
            content = moviesContentMaker("theaters", 2, regionProvince, regionCity, regionSite);
        else
            content = moviesContentMaker("theaters", ContentMaker.login(request.getCookies()), 2, regionProvince, regionCity, regionSite);
        out.write(content);
    }
}