package com.thanhha.controller.product;

import com.thanhha.dao.AccountDAO;
import com.thanhha.dao.ProductDAO;
import com.thanhha.dto.ProductDTO;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import static com.thanhha.constant.ResourceUrl.PathValue.VIEW_PRODUCT_CATALOG;

@WebServlet(name = "ViewProductServlet", value = "/ViewProductServlet")
public class ViewProductServlet extends HttpServlet {
    protected void processHandle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, NamingException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_PRODUCT_CATALOG;

        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> productList = productDAO.getAllProducts();
        request.setAttribute("VIEW_RESULT", productList);


        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processHandle(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}