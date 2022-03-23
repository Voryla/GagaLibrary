package com.zwk.model;

import java.sql.Blob;

public class Book {
	// 书号
	private String isbn = null;
	// 书名
	private String bookName = null;
	// 作者
	private String bookAuthor = null;
	// 出版社
	private String bookPress = null;
	// 出版时间
	private String bookPublicationTime = null;
	// 价格
	private String bookPrice = null;
	// 库存
	private Integer bookStock = null;
	// 类别
	private Integer bookClassId = null;
	// 图像
	private Blob img;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPress() {
		return bookPress;
	}

	public void setBookPress(String bookPress) {
		this.bookPress = bookPress;
	}

	public String getBookPublicationTime() {
		return bookPublicationTime;
	}

	public void setBookPublicationTime(String bookPublicationTime) {
		this.bookPublicationTime = bookPublicationTime;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Integer getBookStock() {
		return bookStock;
	}

	public void setBookStock(Integer bookStock) {
		this.bookStock = bookStock;
	}

	public Integer getBookClassId() {
		return bookClassId;
	}

	public void setBookClassId(Integer bookClassId) {
		this.bookClassId = bookClassId;
	}

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}
}
