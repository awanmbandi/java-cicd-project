package com.example.ecommerce.servlet;

import com.example.ecommerce.model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Object obj = session.getAttribute("CART");
        if (obj instanceof Map) {
            Map<String, CartItem> cart = (Map<String, CartItem>) obj;
            cart.clear();
        }
        req.setAttribute("message", "Order placed successfully! (demo - no payment processed)");
        req.getRequestDispatcher("/WEB-INF/jsp/checkout.jsp").forward(req, resp);
    }
}
