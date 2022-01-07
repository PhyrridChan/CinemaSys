package com.test.controller.Customer;

import SqlUtil.MySQLUtil;
import Users.Customers;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerInfoInputServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String UPLOAD_DIRECTORY = "upload";


    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; 
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        
        ServletFileUpload upload = new ServletFileUpload(factory);

        
        upload.setFileSizeMax(MAX_FILE_SIZE);

        
        upload.setSizeMax(MAX_REQUEST_SIZE);

        
        upload.setHeaderEncoding("UTF-8");

        
        
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;

        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uniFilePath = null;
        Map<String, Object> params = new HashMap<>();
        try {
            
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
                
                for (FileItem item : formItems) {
                    
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        uniFilePath = File.separator + fileName;
                        String filePath = uploadPath + uniFilePath;
                        File storeFile = new File(filePath);
                        
                        System.out.println(fileName + " : " + filePath);
                        
                        item.write(storeFile);
                        request.setAttribute("message", "头像上传成功");
                    } else {
                        String name = item.getFieldName();
                        String value = new String(item.getString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        params.put(name, value);
                    }
                }
            }
        } catch (Exception e) {
            request.setAttribute("massage", "错误信息：" + e.getMessage());
            e.printStackTrace();
        }

        String phone = (String) params.get("phone"),
                age = (String) params.get("age"),
                sex = (String) params.get("sex"),
                regionProvince = (String) params.get("regionProvince"),
                regionCity = (String) params.get("regionCity"),
                regionSite = (String) params.get("regionSite"),
                avator = uniFilePath;

        String userName = null, userPWD = null;
        Cookie cookie = null;
        
        Cookie[] cookies = request.getCookies();
        
        response.setContentType("text/html;charset=UTF-8");
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if ((cookie.getName()).equals("userName")) {
                userName = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cookie.setMaxAge(0);
                cookie.setDomain("");
                response.addCookie(cookie);
            }
            if ((cookie.getName()).equals("userPWD")) {
                userPWD = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                cookie.setMaxAge(0);
                cookie.setDomain("");
                response.addCookie(cookie);
            }
        }
        if (userName == null || userPWD == null) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("<script language='javascript'>alert('出错！');window.location.href='/cinema'</script>");
        }
        else {
            System.out.println("request: " + request +
                    ", age: " + age +
                    ", sex: " + sex +
                    ", regionProvince: " + regionProvince +
                    ", regionCity: " + regionCity +
                    ", regionSite: " + regionSite +
                    ", avator: " + uniFilePath +
                    ", userName: " + userName +
                    ", userPWD: " + userPWD
            );

            MySQLUtil mySQLUtil = new MySQLUtil();
            Customers users = new Customers(userName, avator, sex.equals("male") ? '男' : '女', Integer.parseInt(age), phone, Integer.parseInt(regionProvince), Integer.parseInt(regionCity), Integer.parseInt(regionSite));
            users.printInfo();
            users.addCus(userPWD);

            response.sendRedirect("/cinema");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
