package com.liu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TwoServlet", urlPatterns = "/two")
public class TwoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //直接从请求体中读取参数信息
        String username = request.getParameter("username");
        System.out.println("TwoServlet从请求体得到的参数：" + username);
    }

}
