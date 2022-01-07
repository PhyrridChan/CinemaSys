package com.test.controller.booking;

import Items.Arrange;
import bills.Bills;
import bills.GetBill;
import pageConstructor.ContentMaker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static Items.GetArrange.getArrange;
import static pageConstructor.ContentMaker.ticketContent;

public class TicketBookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        String content = "";
        String arrangeID = request.getParameter("arrangeID");;
        Arrange arrange = getArrange(arrangeID);
        Bills bills = new Bills(arrange, 3);
        bills = GetBill.uploadBill(bills);
        content = ticketContent("booking", ContentMaker.login(request.getCookies()), bills);
        Cookie cookie = new Cookie("bill", bills.toString());
        cookie.setMaxAge(2*60);
        response.addCookie(cookie);
        out.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
