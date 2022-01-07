package com.test.controller.managerOfCinema;

import SqlUtil.MySQLUtil;
import Users.UserLogin;
import Users.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.LoginContent.loginContent;

public class CnmLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySQLUtil mySQLUtil = new MySQLUtil();
        int loginType = 2;
        String userName = request.getParameter("userName");
        String userPWD = request.getParameter("userPWD");
        System.out.println("\n\n\n\n\n\n" + loginType + "-" + userName + "-" + userPWD);
        Users users = UserLogin.login(loginType, userName, userPWD);
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        loginContent(users, out, response, loginType);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
