package com.test.controller.CnmMainPage;

import Users.UserInfoParse;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static pageConstructor.ContentMaker.cnmContentMaker;


public class CinemaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String userInfo = "";
        for (Cookie cookie : cookies) {
            if ((cookie.getName()).equals("userInfo")) {
                userInfo = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                break;
            }
        }
        HashMap<String, String> infoMap = UserInfoParse.parse(userInfo);

        String content = cnmContentMaker("me", infoMap);
        out.write(content);
    }
}
