package com.thanhha.controller.product;

import com.thanhha.product.ProductDAO;
import com.thanhha.product.ProductDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.thanhha.constant.ResourceUrl.PathValue.VIEW_PRODUCT_CATALOG;

@WebServlet(name = "ViewProductServlet", value = "/ViewProductServlet")
public class ViewProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String url = VIEW_PRODUCT_CATALOG;

            ProductDAO productDAO = new ProductDAO();
            List<ProductDTO> productList = productDAO.getAllProducts(true);
            request.setAttribute("VIEW_RESULT", productList);

            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            log("ViewProductServlet_SQLException: " + e.getMessage());
        } catch (NamingException e) {
            log("ViewProductServlet_NamingException: " + e.getMessage());
        }
    }
}
