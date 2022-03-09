package com.thanhha.controller.order;

import com.thanhha.cart.CartObject;
import com.thanhha.dao.OrderDetailDAO;
import com.thanhha.dao.ProductDAO;
import com.thanhha.dto.ProductDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.thanhha.constant.ResourceUrl.PathName.CART_PAGE;
import static com.thanhha.constant.ResourceUrl.PathName.PRODUCT_PAGE;

@WebServlet(name = "CreateOrderDetailServlet", value = "/CreateOrderDetailServlet")
public class CreateOrderDetailServlet extends HttpServlet {
    protected void processHandle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, NamingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ProductDAO productDAO = new ProductDAO();
        String customerId = null;
        String url = CART_PAGE;
        if (session != null) {
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart != null) {
                Map<String, Integer> items = cart.getItems();
                if (items != null) {
                    BigDecimal total = BigDecimal.valueOf(0);
                    for (Map.Entry<String, Integer> item : items.entrySet()) {
                        ProductDTO product = productDAO.getProductById(item.getKey());
                        System.out.println(item.getValue() + " " + item.getKey());
                        total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getValue())));
                    }
                    boolean result = orderDetailDAO.createOrderDetail((HashMap<String, Integer>) items, total, customerId);
                    if (result) {
                        url = PRODUCT_PAGE;
                        session.removeAttribute("CART");
                    }
                    response.sendRedirect(url);
                }
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processHandle(request, response);
        } catch (SQLException | IOException | NamingException e) {
            String errorMessage = e.getMessage();
            log("CreateOrderDetailServlet" + errorMessage);
//            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
