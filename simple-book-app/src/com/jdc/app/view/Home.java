package com.jdc.app.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdc.app.entity.Author;
import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Shop;
import com.jdc.app.service.CategoryService;
import com.jdc.app.service.HomeService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


public class Home {
	
	@FXML
	private ComboBox<Category> bookCategory;
	@FXML
	private TextField bookName;
	@FXML
	private TableView<Book> tblList;
	@FXML
	private TableView<Shop> tblTotal;
	@FXML
	private TableColumn<Home, String> bookCol;
	@FXML
	private TableColumn<Home, String> priceCol;

	
	private CategoryService catService;
	private HomeService homeService;
	
	
	
	private void loadCategory() {
		List<Category> catList = catService.findAll();
		bookCategory.getItems().addAll(catList);
	}
	

	public void addToCart(MouseEvent event) {
		if(event.getClickCount() == 2) {
		
			
			Book book= tblList.getSelectionModel().getSelectedItem();
			List<Shop> shopDetail= homeService.shop(book.getName(),book.getPrice());
			//int price = this.price.getText().isEmpty() ? 0 : Integer.parseInt(this.price.getText());
			tblTotal.getItems().addAll(shopDetail);
					
			
		}
	}
	
	
	
	public void search()
	{
		tblList.getItems().clear();
		List<Book> homeDetail= homeService.findHomeDetail(bookCategory.getValue(),bookName.getText());
		//List<HomeDetail> homeDetail = homeService.findAll();
		tblList.getItems().addAll(homeDetail);
		
		
	}
	
		
 	
	@FXML
	private void initialize() {
		homeService = HomeService.getInstance();
		search();
		catService = CategoryService.getInstance();
		loadCategory();	
		
	}
	

}