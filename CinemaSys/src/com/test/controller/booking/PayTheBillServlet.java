package com.test.controller.booking;

import bills.Bills;
import bills.GetBill;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static pageConstructor.ContentMaker.moneyMaker;

public class PayTheBillServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid = request.getParameter("bid");
        String tics = request.getParameter("tics");
        String total = request.getParameter("total");
        String counter = request.getParameter("counter");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String content = "";
        Bills bills = GetBill.getbill(bid);
        if (bills.getTotal() == 0) bills.setTotal(Double.parseDouble(total));
        if (bills.getCounter() == 0) bills.setCounter(Integer.parseInt(counter));
        if (bills.getTicketStr() == null || bills.getTicketStr().equals("")) bills.setTickets(tics);
        bills = GetBill.uploadBill(bills);
        Cookie[] cookies = request.getCookies();
        if (bills.getState() != 3) {
            response.sendRedirect("/cinema/billInfo?bid=" + bills.getBid());
            return;
        }

        String userInfo = "";
        for (Cookie c : cookies)
            if (c.getName().equals("userInfo")) userInfo = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
        GetBill.updateUser(bills.getBid(), userInfo);
        content = moneyMaker("payTheBill", bills);
        out.write(content);
    }
}
