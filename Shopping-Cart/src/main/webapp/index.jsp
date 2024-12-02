<%@page import="com.Shopping.connection.DBConnection" %>
<%@page import="com.Shopping.model.*" %>
<%@page import="com.Shopping.dao.*" %>>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	User auth = (User) request.getSession().getAttribute("auth");
	if (auth != null) {
    	request.setAttribute("person", auth);
	}
	
	ProductDao pdata = new ProductDao(DBConnection.getConnection());
	List<Product> products = pdata.getAllProducts();
	
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
	
	<div class = "container">
		<div class = "card-header my-3"> All Products</div>
		<div class = "row">
		
		<%
			if(!products.isEmpty()) {
				for(Product pro : products) {
				%>
					<div class = "col-md-3 my-3">
					<div class="card w-100" style="width: 18rem;">
  						<img class="card-img-top" src="product-images/<%=pro.getImage()%>" alt="Card image cap">
  						<div class="card-body">
    						<h5 class="card-title"><%= pro.getName() %></h5>
    						<h6 class = "price"> Price: ₹<%= pro.getPrice() %></h6>
    						<h6 class = "category"> Category: <%= pro.getCategory() %></h6>
    						<div class="mt-3 d-flex justify-content-between">
    							<a href="add-to-cart?id=<%= pro.getId() %>" class="btn btn-dark">Add to Cart</a>
    							<a class="btn btn-primary" href="order-now?quantity=1&id=<%=pro.getId()%>">Buy Now</a>
    						</div>	
  						</div>
					</div>	
					</div>
				<%}
			}	
		%>
		
		</div>
	
	</div>
	
	<%@include file="/includes/footer.jsp"%>
</body>
</html>