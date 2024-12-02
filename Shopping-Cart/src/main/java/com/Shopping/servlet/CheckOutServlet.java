package com.Shopping.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.Shopping.connection.DBConnection;
import com.Shopping.dao.OrderDao;
import com.Shopping.model.Cart;
import com.Shopping.model.Order;
import com.Shopping.model.User;

@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try(PrintWriter out = response.getWriter()){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
        
			//retrive all cart products
			ArrayList<Cart> listOfCart = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			//User authendication
			User auth = (User) request.getSession().getAttribute("auth");
			
			// Check auth and cart list
			if(listOfCart != null && auth != null) {
				
				//prepare the order object
				for(Cart carts : listOfCart) {
					Order order = new Order();
					order.setId(carts.getId());
					order.setUid(auth.getId());
					order.setQunatity(carts.getQuantity());
					order.setDate(formatter.format(date));
				
					//insitantiate the dao class
					OrderDao oDao = new OrderDao(DBConnection.getConnection());
					
					//calling insert method
					boolean result = oDao.insertOrder(order);
					if(!result) break;
				}
				listOfCart.clear();
				response.sendRedirect("order.jsp");
				return;
			} else {
				if(auth==null) {
					response.sendRedirect("login.jsp");
					return;
				}
				
				response.sendRedirect("cart.jsp");
				return;
			}
		}  catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
