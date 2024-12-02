<%@page import="com.Shopping.connection.DBConnection" %>
<%@page import="com.Shopping.model.*" %>
<%@page import="com.Shopping.dao.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    
	User auth = (User) request.getSession().getAttribute("auth");
	List<Order> orders = null;
	
	if (auth != null) {
    	request.setAttribute("person", auth);
    	OrderDao orderDao  = new OrderDao(DBConnection.getConnection());
		orders = orderDao.userOrders(auth.getId()); 
	} else {
		response.sendRedirect("login.jsp");
	}
	
	ArrayList<Cart> listOfCart = (ArrayList<Cart>) session.getAttribute("cart-list");
	if (listOfCart != null) {
		request.setAttribute("cart_list", listOfCart);
	}
	%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/includes/header.jsp"%>
<title>E-Commerce Cart</title>
</head>
<body>
	<%@include file="/includes/navbar.jsp"%>
	
	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			
			<%
			if(orders != null){
				for(Order o:orders){%>
					<tr>
						<td><%=o.getDate() %></td>
						<td><%=o.getName() %></td>
						<td><%=o.getCategory() %></td>
						<td><%=o.getQunatity() %></td>
						<td><%=dcf.format(o.getPrice()) %></td>
						<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId()%>">Cancel Order</a></td>
					</tr>
				<%}
			}
			%>
			
			</tbody>
		</table>
	</div>

	<%@include file="/includes/footer.jsp"%>
</body>
</html>