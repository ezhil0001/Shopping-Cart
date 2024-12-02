package com.Shopping.dao;

import java.sql.*;
import java.util.*;


import com.Shopping.model.*;


public class ProductDao {
	private Connection connec;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    

	public ProductDao(Connection connec) {
		super();
		this.connec = connec;
	}
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();
		try {
			
			query = "SELECT * FROM products";
			pst = this.connec.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Product row = new Product();
				row.setId(rs.getInt("id"));
                row.setName(rs.getString("name"));
                row.setCategory(rs.getString("category"));
                row.setPrice(rs.getDouble("price"));
                row.setImage(rs.getString("image"));

                products.add(row);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return products;
		
	}
	
	public Product getSingleProduct(int id) {
		 Product row = null;
	        try {
	            query = "SELECT * FROM products WHERE id=? ";

	            pst = this.connec.prepareStatement(query);
	            pst.setInt(1, id);
	            ResultSet rs = pst.executeQuery();

	            while (rs.next()) {
	            	row = new Product();
	                row.setId(rs.getInt("id"));
	                row.setName(rs.getString("name"));
	                row.setCategory(rs.getString("category"));
	                row.setPrice(rs.getDouble("price"));
	                row.setImage(rs.getString("image"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }

	        return row;
	    }
	
	public double getTotalCartPrice(ArrayList<Cart> listOfCart) {
        double sum = 0;
        try {
            if (listOfCart.size() > 0) {
                for (Cart item : listOfCart) {
                    query = "SELECT price FROM products WHERE id=?";
                    
                    pst = this.connec.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    
                    while (rs.next()) {
                        sum+=rs.getDouble("price")*item.getQuantity();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }
	
    public List<Cart> getCartProducts(ArrayList<Cart> listOfCart) {
        List<Cart> products = new ArrayList<>();
        try {
            if (listOfCart.size() > 0) {
                for (Cart item : listOfCart) {
                    query = "SELECT * FROM products WHERE id=?";
                    pst = this.connec.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        Cart row = new Cart();
                        row.setId(rs.getInt("id"));
                        row.setName(rs.getString("name"));
                        row.setCategory(rs.getString("category"));
                        row.setPrice(rs.getDouble("price")*item.getQuantity());
                        row.setQuantity(item.getQuantity());
                        products.add(row);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return products;
    }

	

}
