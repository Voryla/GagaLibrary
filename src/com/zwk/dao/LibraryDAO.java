package com.zwk.dao;

import com.zwk.model.Book;
import com.zwk.model.BorrowBook;
import com.zwk.model.User;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface LibraryDAO {
	/**
	 * 搜索书籍
	 * @param bookInfo
	 * @return
	 */
	List<Book> getSearchBook(String bookInfo,int offset,int limit);

	/**
	 * 获取符合当前条件的所有书籍count
	 * @param bookInfo
	 * @return
	 */
	int getSearchCount(String bookInfo);

	/**
	 * 查询当前用户所借阅的所有书籍
	 * @return
	 */
	List<BorrowBook> getUserBorrowBook(String userId);

	/**
	 * 租借书籍
	 * @param userId
	 * @param book
	 * @param borrowDate yyyy-MM-dd hh-mm-ss格式
	 * @param dueDate yyyy-MM-dd hh-mm-ss格式
	 * @return
	 */
	boolean borrowBook(Book book, String userId, String borrowDate, String dueDate);

	/**
	 * 查询用户是否持有该书籍
	 * @param bookIsbn
	 * @param userId
	 * @return
	 */
	boolean bookIsBorrow(String bookIsbn,String userId);

	/**
	 * 还书
	 * @param userId
	 * @param bookIsbn
	 * @return
	 */
	boolean dueBook(String bookIsbn,String userId);

	/**
	 * 续借
	 * @param bookIsbn
	 * @param user
	 * @param borrowDate
	 * @param renewDate
	 * @return
	 */
	boolean renewBook(String bookIsbn, User user, String borrowDate, String renewDate);

}
