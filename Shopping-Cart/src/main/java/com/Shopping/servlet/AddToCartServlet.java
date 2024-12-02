package com.Shopping.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.Shopping.model.*;

@WebServlet(name = "AddToCartServlet", urlPatterns = "/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()) {
			
			ArrayList<Cart> listOfCart = new ArrayList<>();
			int id = Integer.parseInt(request.getParameter("id"));
			
			Cart c = new Cart();
			c.setId(id);
            c.setQuantity(1);
            
            HttpSession session = request.getSession();
            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
            
            if(cart_list == null) {
            	listOfCart.add(c);
            	session.setAttribute("cart-list", listOfCart);
                response.sendRedirect("index.jsp");

            } else {
                listOfCart = cart_list;

                boolean exist = false;
                for (Cart carts : cart_list) {
                    if (carts.getId() == id) {
                        exist = true;
                        out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='cart.jsp'>GO to Cart Page</a></h3>");
                    }
                }

                if (!exist) {
                    listOfCart.add(c);
                    response.sendRedirect("index.jsp");
                }
            }
			
		}

	}

}