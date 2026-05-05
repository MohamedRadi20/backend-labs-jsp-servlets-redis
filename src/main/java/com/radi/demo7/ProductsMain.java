package com.radi.demo7;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ProductsMain")
public class ProductsMain extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long start = System.currentTimeMillis();

        // model
        List <Product> data = null;
        try {
            String user = (String) request.getAttribute("user");
            data = ProductDb.getProductList(user);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("DB CALL TIME: " + (end - start) + " ms");

//        String [] data = {"product 1", "product 2", "product 3", "product 4", "product 5"};
        request.setAttribute("data", data);

        // view

        RequestDispatcher rs = request.getRequestDispatcher("display-products.jsp");
        rs.forward(request,response);
    }

}