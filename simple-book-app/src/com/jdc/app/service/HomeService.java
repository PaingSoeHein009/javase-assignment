package com.jdc.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Shop;
import com.jdc.app.util.ConnectionManager;

public class HomeService {

	private static final String sql = "select b.id, b.name book_name, b.price price, c.id category_id, c.name category_name, a.id author_id, a.name author_name from book b join category c on b.category_id = c.id join author a on b.author_id = a.id where 1 = 1";
	private static final String sqlShop = "select b.id, b.name book_name, b.price price from book b where 1 = 1";
	private static HomeService INSTANCE;
	private HomeService() {}
	
	public static HomeService getInstance() {
		if(null == INSTANCE)
			INSTANCE = new HomeService();
		return INSTANCE;
	}
	

	public List<Book> findAll() {
		return findHomeDetail(null, null);
	}
	
	
	public List<Book> findHomeDetail(Category category,String name) {
		
		List<Book> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder(sql);
		List<Object> params = new LinkedList<>();
		
		if(null != category) {
			sb.append(" and c.name like ?");
			params.add(category.getName());
		}
		
		if(null != name && !name.isEmpty()) {
			sb.append(" and b.name like ?");
			params.add("%".concat(name).concat("%"));
		}
		
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				list.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Book getObject(ResultSet rs) throws SQLException {
		
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setName(rs.getString("book_name"));
		b.setPrice(rs.getInt("price"));
		
		Category c = new Category();
		c.setId(rs.getInt("category_id"));
		c.setName(rs.getString("category_name"));
		
		Author a = new Author();
		a.setId(rs.getInt("author_id"));
		a.setName(rs.getString("author_name"));
		
	
		b.setCategory(c);
		b.setAuthor(a);
		
		return b;
	}
	
	public List<Shop> shop(String nameShop,int priceShop)
	{
		List<Shop> listShop = new ArrayList<>();
		StringBuilder sb = new StringBuilder(sqlShop);
		List<Object> params = new LinkedList<>();
		
		if(null != nameShop) {
			sb.append(" and b.name like ?");
			params.add(nameShop);
		}
		
		if(priceShop < 0) {
			sb.append(" and b.price >= ?");
			params.add(priceShop);
		}
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
				listShop.add(getShop(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listShop;
	}
	
public Shop getShop(ResultSet rs) throws SQLException {
	
	int quantity = 1;
	int total = 0;
	int price ;
	price = rs.getInt("price");
	total = quantity * price;
	
		Shop shop = new Shop();
		shop.setId(rs.getInt("id"));
		shop.setName(rs.getString("book_name"));
		shop.setPrice(rs.getInt("price"));
		shop.setQuantity(quantity);
		shop.setTotal(total);
		return shop;
	}
	
}
