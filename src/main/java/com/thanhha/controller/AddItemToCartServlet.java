package com.thanhha.controller;

import com.thanhha.cart.CartObject;
import com.thanhha.dao.ProductDAO;
import com.thanhha.dto.ProductDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.thanhha.constant.ResourceUrl.VIEW_PRODUCT_CATALOG;

@WebServlet(name = "AddItemToCartServlet", value = "/AddItemToCartServlet")
public class AddItemToCartServlet extends HttpServlet {
    protected void processHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "viewProduct";
        try {
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObject();
            }

            String id = request.getParameter("selectedItem");
            ProductDAO productDAO = new ProductDAO();
            ProductDTO product = productDAO.getProductById(id);

            cart.addItemToCart(product.getId());
            session.setAttribute("CART", cart);
            session.setAttribute("PRODUCT_LIST", productDAO.getAllProducts());
        } finally {
            response.sendRedirect(url);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processHandle(request, response);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
