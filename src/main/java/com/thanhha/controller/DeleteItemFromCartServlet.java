package com.thanhha.controller;

import com.thanhha.cart.CartObject;
import com.thanhha.dto.ProductDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

import static com.thanhha.constant.ResourceUrl.VIEW_ITEMS_CART;

@WebServlet(name = "DeleteItemFromCartServlet", value = "/DeleteItemFromCartServlet")
public class DeleteItemFromCartServlet extends HttpServlet {
    protected void processHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart != null) {
                Map<String, Integer> items = cart.getItems();
                if (items != null) {
                    String[] selectedItem = request.getParameterValues("removeItems");
                    if (selectedItem != null) {
                        for (String item : selectedItem) {
                            cart.removeItemFromCart(item);
                        }
                        session.setAttribute("CART", cart);
                    }
                }
            }
        }
        response.sendRedirect(VIEW_ITEMS_CART);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            processHandle(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
