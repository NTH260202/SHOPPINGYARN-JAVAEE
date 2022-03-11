package com.thanhha.controller.order;

import com.thanhha.cart.CartObject;
import com.thanhha.constant.ResourceUrl;
import com.thanhha.orderDetail.OrderDetailDAO;
import com.thanhha.product.ProductDAO;
import com.thanhha.product.ProductDTO;
import com.thanhha.product.ProductUpdateError;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.thanhha.constant.ErrorMessage.PRODUCT.IS_QUANTITY_ERROR;
import static com.thanhha.constant.ErrorMessage.PRODUCT.QUANTITY_IS_INVALID;
import static com.thanhha.constant.ResourceUrl.PathName.CART_PAGE;
import static com.thanhha.constant.ResourceUrl.PathName.PRODUCT_PAGE;

@WebServlet(name = "CreateOrderDetailServlet", value = "/CreateOrderDetailServlet")
public class CreateOrderDetailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ProductDAO productDAO = new ProductDAO();
        ProductUpdateError productError = new ProductUpdateError();

        String customerId = null;
        String url = CART_PAGE;
        try {
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        BigDecimal total = BigDecimal.valueOf(0);
                        for (Map.Entry<String, Integer> item : items.entrySet()) {
                            ProductDTO product = productDAO.getProductById(item.getKey());
                            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getValue())));
                        }
                        boolean result = orderDetailDAO.createOrderDetail((HashMap<String, Integer>) items, total, customerId);

                        if (result) {
                            IS_QUANTITY_ERROR = false;
                            url = PRODUCT_PAGE;
                            session.removeAttribute("CART");
                            response.sendRedirect(url);
                        } else {
                            if (IS_QUANTITY_ERROR) {
                                ProductUpdateError error = new ProductUpdateError();
                                error.setQuantityIsInvalid(QUANTITY_IS_INVALID);
                                request.setAttribute("ERROR_MESSAGE", error);
                                RequestDispatcher dispatcher = request.getRequestDispatcher(ResourceUrl.PathValue.CART_PAGE);
                                dispatcher.forward(request, response);
                            } else {
                                response.sendRedirect(url);
                            }
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log("CreateOrderDetailServlet_NamingException" + e.getMessage());
        } catch (SQLException e) {
            log("CreateOrderDetailServlet_SQLException: " + e.getMessage());
        } catch (IOException e) {
            log("CreateOrderDetailServlet_IOException: " + e.getMessage());
        }
    }
}
