package com.test.controller.booking;

import bills.Bills;
import bills.GetBill;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import static pageConstructor.ContentMaker.billInfoMaker;

public class BillInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        String content = "";
        String bid = request.getParameter("bid");;
        Bills bills = GetBill.getbill(bid);
        content = billInfoMaker("billInfo", bills);
        out.write(content);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
