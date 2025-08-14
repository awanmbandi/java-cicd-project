package com.example.ecommerce.servlet;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.store.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getCart(HttpSession session) {
        Object obj = session.getAttribute("CART");
        if (obj == null) {
            Map<String, CartItem> cart = new LinkedHashMap<>();
            session.setAttribute("CART", cart);
            return cart;
        }
        return (Map<String, CartItem>) obj;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, CartItem> cart = getCart(req.getSession(true));
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Map<String, CartItem> cart = getCart(session);

        String action = req.getParameter("action");
        String id = req.getParameter("id");
        if (action == null) action = "add";

        if ("add".equalsIgnoreCase(action) && id != null) {
            ProductRepository.findById(id).ifPresent(p -> {
                CartItem item = cart.get(id);
                if (item == null) {
                    cart.put(id, new CartItem(p, 1));
                } else {
                    item.setQuantity(item.getQuantity() + 1);
                }
            });
        } else if ("update".equalsIgnoreCase(action) && id != null) {
            CartItem item = cart.get(id);
            if (item != null) {
                try {
                    int qty = Integer.parseInt(req.getParameter("qty"));
                    item.setQuantity(qty);
                } catch (NumberFormatException ignored) {}
            }
        } else if ("remove".equalsIgnoreCase(action) && id != null) {
            cart.remove(id);
        } else if ("clear".equalsIgnoreCase(action)) {
            cart.clear();
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
