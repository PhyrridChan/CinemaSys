package com.test.controller.booking;

import bills.Bills;
import bills.GetBill;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class HandleTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid = request.getParameter("bid");
        Bills bills = GetBill.getbill(bid);
        if (bills.getState() == 3) bills.setState(0);
        else if (bills.getState() == 0) bills.setState(1);
        bills = GetBill.uploadBill(bills);
        response.sendRedirect("/cinema/billInfo?bid=" + bills.getBid());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
