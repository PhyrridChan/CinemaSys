package com.test.controller.Customer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CustomerSignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName, userPWD, userRePWD;
        userName = request.getParameter("userName");
        userPWD = request.getParameter("userPWD");
        userRePWD = request.getParameter("userRePWD");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        if (userPWD != null && userRePWD != null && !userRePWD.equals(userPWD)) {
            out.write("<script language='javascript'>alert('两次密码不匹配，请重新填写');window.location.href='/login_page/CustomerLoginPage/customerSignUp.html'</script>");
        } else if (userName == null || userName.equals("") || Objects.equals(userPWD, "")) {
            out.write("<script language='javascript'>alert('信息填写不完全，请重新填写');window.location.href='/login_page/CustomerLoginPage/customerSignUp.html'</script>");
        } else {
            // 为userName和userPWD创建 Cookie
            Cookie userInfoOfPage1_userName = new Cookie("userName", URLEncoder.encode(userName, StandardCharsets.UTF_8));
            Cookie userInfoOfPage1_userPWD = new Cookie("userPWD", URLEncoder.encode(userPWD, StandardCharsets.UTF_8));
            // 为两个 Cookie 设置过期日期为 5 min后
            userInfoOfPage1_userName.setMaxAge(60 * 5);
            userInfoOfPage1_userPWD.setMaxAge(60 * 5);
            // 在响应头中添加两个 Cookie
            response.addCookie(userInfoOfPage1_userName);
            response.addCookie(userInfoOfPage1_userPWD);
            // 设置响应内容类型
            response.setContentType("text/html;charset=UTF-8");

            response.setStatus(302);
            response.sendRedirect("/login_page/CustomerLoginPage/customerInfoInput.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
