package com.zwk.model;

public class BorrowBook {
	// 书号
	private String isbn=null;
	// 书名
	private String bookName=null;
	// 作者
	private String bookAuthor=null;
	// 出版社
	private String bookPress=null;
	// 出版时间
	private String bookPublicationTime=null;
	// 价格
	private String bookPrice=null;
	// 租借日期
	private String borrowTime=null;
	// 还书期限
	private String dueTime=null;

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

	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
}
