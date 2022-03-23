package com.zwk.service;

import com.zwk.dao.LibraryDAO;
import com.zwk.dao.LibraryDAOImpl;
import com.zwk.model.Book;
import com.zwk.model.BorrowBook;
import com.zwk.model.User;
import com.zwk.utils.Log4jInit;
import java.util.List;

public class LibraryServiceImpl implements LibraryService {
	private LibraryDAO libraryDAO;

	public LibraryServiceImpl() {
		libraryDAO = new LibraryDAOImpl();
	}

	@Override
	public List<Book> getSearchBook(String bookInfo, int offset, int limit, ServiceMessage serviceMessage) {
		List<Book> bookList = libraryDAO.getSearchBook(bookInfo, offset, limit);
		if (!bookList.isEmpty()) {
			serviceMessage.showMessage("未查询到该书", false);
			return bookList;
		} else {
			serviceMessage.showMessage("未查询到该书", false);
		}
		return null;
	}

	@Override
	public int getSearchCount(String bookInfo) {
		return libraryDAO.getSearchCount(bookInfo);
	}

	@Override
	public List<BorrowBook> getUserBorrowBooks(String userId, ServiceMessage serviceMessage) {
		List<BorrowBook> borrowBooks = libraryDAO.getUserBorrowBook(userId);
		if (!borrowBooks.isEmpty()) {
			serviceMessage.showMessage("获取用户借用书籍成功", true);
			return borrowBooks;
		} else {
			serviceMessage.showMessage("获取用户借用书籍失败", false);
		}
		return null;
	}

	@Override
	public boolean borrowBook(Book book, User user, String borrowDate, String dueDate) {
		if (libraryDAO.borrowBook(book, user.getUserId(), borrowDate, dueDate)) {
			Log4jInit.getLogger(this).info("用户" + user.getUserId() + "租借" + book.getIsbn() + "成功!");
			return true;
		} else {
			Log4jInit.getLogger(this).info("用户" + user.getUserId() + "租借" + book.getIsbn() + "失败!");
			return false;
		}
	}

	@Override
	public boolean bookIsBorrow(String bookIsbn, String userId) {
		return libraryDAO.bookIsBorrow(bookIsbn, userId);
	}

	@Override
	public boolean dueBook(String bookIsbn, String userId) {
		return libraryDAO.dueBook(bookIsbn, userId);
	}

	@Override
	public boolean renewBook(String bookIsbn, User user, String borrowDate, String renewDate) {
		return libraryDAO.renewBook(bookIsbn, user, borrowDate, renewDate);
	}
}
