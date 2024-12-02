package com.Shopping.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;

import com.Shopping.connection.DBConnection;
import com.Shopping.dao.OrderDao;
import com.Shopping.model.*;

@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	         Date date = new Date();
			
			User auth = (User) request.getSession().getAttribute("auth");
			
			if(auth != null) {
				String productId = request.getParameter("id");
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				
				if(productQuantity <= 0) {
					productQuantity = 1;
				}
				Order orderModel = new Order();
				orderModel.setId(Integer.parseInt(productId));
				orderModel.setUid(auth.getId());
				orderModel.setQunatity(productQuantity);
				orderModel.setDate(formatter.format(date));
				
				
				OrderDao orderDao = new OrderDao(DBConnection.getConnection());
			
                boolean result = orderDao.insertOrder(orderModel);
                if (result) {
                    ArrayList<Cart> listOfCart = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (listOfCart != null) {
                        for (Cart carts : listOfCart) {
                            if (carts.getId() == Integer.parseInt(productId)) {
                                listOfCart.remove(listOfCart.indexOf(carts));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("order.jsp");
                } else {
                    out.println("order failed");
                }
				
            } else {
                response.sendRedirect("login.jsp");
            }

        } catch (ClassNotFoundException|SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
