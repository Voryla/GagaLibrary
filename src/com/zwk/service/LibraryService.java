package com.zwk.service;

import com.zwk.model.Book;
import com.zwk.model.BorrowBook;
import com.zwk.model.User;
import java.util.List;

public interface LibraryService {
	/**
	 * 搜索书籍
	 *
	 * @param bookInfo
	 */
	List<Book> getSearchBook(String bookInfo,int offset,int limit, ServiceMessage serviceMessage);

	/**
	 * 获取符合当前条件的所有书籍count
	 * @param bookInfo
	 * @return
	 */
	int getSearchCount(String bookInfo);
	/**
	 * 获取用户所持有的书籍
	 */
	List<BorrowBook> getUserBorrowBooks(String userId, ServiceMessage serviceMessage);

	/**
	 *
	 * @param book
	 * @param user
	 * @param borrowDate yyyy-MM-dd hh-mm-ss格式
	 * @param dueDate yyyy-MM-dd hh-mm-ss格式
	 * @return
	 */
	boolean borrowBook(Book book, User user, String borrowDate, String dueDate );

	/**
	 * 查询书籍是否已租借
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
	boolean renewBook(String bookIsbn,User user,String borrowDate,String renewDate);
}
