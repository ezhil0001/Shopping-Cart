package com.Shopping.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.Shopping.model.Cart;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()) {
			String productId = request.getParameter("id");
			if (productId != null) {
				ArrayList<Cart> listOfCart = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
				if (listOfCart != null) {
					for (Cart carts : listOfCart) {
						if (carts.getId() == Integer.parseInt(productId)) {
							listOfCart.remove(listOfCart.indexOf(carts));
							break;
						}
					}
				}
				response.sendRedirect("cart.jsp");

			} else {
				response.sendRedirect("cart.jsp");
			}

		}
	}

}
