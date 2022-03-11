package com.thanhha.controller.cart;

import com.thanhha.cart.CartObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

import static com.thanhha.constant.ResourceUrl.PathName.CART_PAGE;

@WebServlet(name = "DeleteItemFromCartServlet", value = "/DeleteItemFromCartServlet")
public class DeleteItemFromCartServlet extends HttpServlet {
    protected void processHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
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
            response.sendRedirect(CART_PAGE);
        } catch (IOException e) {
            log("DeleteItemFromCartServlet_IOException " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
