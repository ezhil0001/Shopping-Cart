<%@page import="com.Shopping.model.*" %>
<%@page import="java.util.*" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
        <%
		User auth = (User) request.getSession().getAttribute("auth");
		if (auth != null) {
			response.sendRedirect("index.jsp");
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
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="user-login" method="post">
					<div class="form-group">
						<label>Email address</label> 
						<input type="email" name="login-email" class="form-control" placeholder="Enter email">
					</div>
					<div class="form-group">
						<label>Password</label> 
						<input type="password" name="login-password" class="form-control" placeholder="Password">
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@include file="/includes/footer.jsp"%>
</body>
</html>