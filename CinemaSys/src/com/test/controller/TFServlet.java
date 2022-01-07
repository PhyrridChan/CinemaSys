package com.test.controller;

import pageConstructor.UpdateTable;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import static pageConstructor.UpdateTable.getDic;

public class TFServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Enumeration<String> params = request.getParameterNames();
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> dic = getDic();
        for (Enumeration<String> e = params; e.hasMoreElements(); ) {
            String key = e.nextElement().toString();
            if (!request.getParameter(key).equals("") && !request.getParameter(key).equals("undefined")) {
                map.put(dic.getOrDefault(key, key), request.getParameter(key));
            }
//            if (request.getParameter(key) != "") map.put(key, request.getParameter(key));
        }
        String id = map.get("id");
        id = id.substring(id.indexOf('_') + 1);
        id = id.replace("Cus", "");
        id = id.replace("Staff", "");
        map.replace("id", id);
        UpdateTable.updateTable(map); //tableName id
        response.sendRedirect("/cinema/admin/a1n0e0/me?tableName=" + map.get("tableName"));
    }
}
