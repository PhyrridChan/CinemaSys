package com.test.controller.Customer;

import SqlUtil.MySQLUtil;
import Users.UserLogin;
import Users.Users;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.LoginContent.loginContent;

public class CustomerLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySQLUtil mySQLUtil = new MySQLUtil();
        int loginType = 1;
        String userName = request.getParameter("userName");
        String userPWD = request.getParameter("userPWD");
        System.out.println("\n" + loginType + "-" + userName + "-" + userPWD);
        Users users = UserLogin.login(1, userName, userPWD);
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        loginContent(users, out, response, loginType);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
