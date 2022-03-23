package com.zwk.dao;

import com.zwk.model.Book;
import com.zwk.model.BorrowBook;
import com.zwk.model.User;
import com.zwk.utils.JDBCUtils;
import com.zwk.utils.Log4jInit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAOImpl implements LibraryDAO {
	private static Connection connection = null;

	static {
		try {
			connection = JDBCUtils.getConnection();
			// 连接数据库成功
			Log4jInit.getLogger(LibraryDAOImpl.class).info("Mysql Connection success " + connection.getClass());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LibraryDAOImpl() {

	}

	@Override
	public List<Book> getSearchBook(String bookInfo, int offset, int limit) {
		try {
			PreparedStatement statement = null;
			// 查询所有书籍
			if (bookInfo.equals("all")) {
				statement = connection.prepareStatement("SELECT isbn,book_name,book_author," +
						"book_press,book_publication_time,book_price,book_stock,book_class_id,book_img FROM" +
						" gaga_library_book_test LIMIT ?,?");
				statement.setInt(1, offset);
				statement.setInt(2, limit);
			} else {
				statement = connection.prepareStatement("SELECT isbn,book_name,book_author," +
						"book_press,book_publication_time,book_price,book_stock,book_class_id,book_img FROM" +
						" gaga_library_book_test WHERE book_name LIKE ? OR book_author LIKE ? OR isbn LIKE ? LIMIT ?,?");
				statement.setNString(1, "%" + bookInfo + "%");
				statement.setNString(2, "%" + bookInfo + "%");
				statement.setNString(3, "%" + bookInfo + "%");
				statement.setInt(4, offset);
				statement.setInt(5, limit);
			}
			// 接收查询结果
			ResultSet resultSet = statement.executeQuery();
			Log4jInit.getLogger(this.getClass()).info("开始查询与 " + bookInfo + " 相关书籍");
			List<Book> bookList = new ArrayList<>();
			// 填充搜索结果
			while (resultSet.next()) {
				Book tempBook = new Book();
				tempBook.setIsbn(resultSet.getString(1));
				tempBook.setBookName(resultSet.getString(2));
				tempBook.setBookAuthor(resultSet.getString(3));
				tempBook.setBookPress(resultSet.getString(4));
				tempBook.setBookPublicationTime(resultSet.getString(5));
				tempBook.setBookPrice(resultSet.getString(6));
				tempBook.setBookStock(resultSet.getInt(7));
				tempBook.setBookClassId(resultSet.getInt(8));
				tempBook.setImg(resultSet.getBlob(9));
				bookList.add(tempBook);
				Log4jInit.getLogger(this.getClass()).info(tempBook.getBookName());
			}
			if (bookList.size() < 1)
				Log4jInit.getLogger(this.getClass()).error("并未查询到与 " + bookInfo + " 相关的书籍");

			return bookList;
		} catch (SQLException throwAbles) {
			throwAbles.printStackTrace();
		}

		return null;
	}

	@Override
	public int getSearchCount(String bookInfo) {
		int count = 0;
		try {
			PreparedStatement statement = null;
			// 查询所有书籍
			if (bookInfo.equals("all")) {
				statement = connection.prepareStatement("SELECT isbn,book_name,book_author," +
						"book_press,book_publication_time,book_price,book_stock,book_class_id,book_img FROM" +
						" gaga_library_book_test");

			} else {
				statement = connection.prepareStatement("SELECT isbn,book_name,book_author," +
						"book_press,book_publication_time,book_price,book_stock,book_class_id,book_img FROM" +
						" gaga_library_book_test WHERE book_stock>0 AND book_name LIKE ? OR book_author LIKE ? OR isbn LIKE ?");
				statement.setNString(1, "%" + bookInfo + "%");
				statement.setNString(2, "%" + bookInfo + "%");
				statement.setNString(3, "%" + bookInfo + "%");
			}
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
		} catch (SQLException throwAbles) {
			throwAbles.printStackTrace();
		}
		return count;
	}


	@Override
	public List<BorrowBook> getUserBorrowBook(String userId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT isbn,book_name,book_author,book_press,book_publication_time,book_price,borrow_date,due_date FROM " +
							"((SELECT id,isbn,book_name,book_author,book_press,book_publication_time,book_price" +
							" FROM gaga_library_book_test WHERE isbn IN(SELECT book_isbn FROM gaga_library_borrow_test WHERE user_id=? AND is_delete=0))AS a" +
							",(SELECT book_isbn,borrow_date,due_date FROM gaga_library_borrow_test WHERE user_id=? AND is_delete=0)AS b ) WHERE a.isbn=b.book_isbn");
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<BorrowBook> borrowBooks = new ArrayList<>();
			while (resultSet.next()) {
				BorrowBook book = new BorrowBook();
				book.setIsbn(resultSet.getString("isbn"));
				book.setBookName(resultSet.getString("book_name"));
				book.setBookAuthor(resultSet.getString("book_author"));
				book.setBookPress(resultSet.getString("book_press"));
				book.setBookPublicationTime(resultSet.getString("book_publication_time"));
				book.setBookPrice(resultSet.getString("book_price"));
				book.setBorrowTime(resultSet.getString("borrow_date"));
				book.setDueTime(resultSet.getString("due_date"));
				borrowBooks.add(book);
			}
			return borrowBooks;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean borrowBook(Book book, String userId, String borrowDate, String dueDate) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO gaga_library_borrow_test(borrow_date,due_date,book_isbn,user_id)VALUES (?,?,?,?)");
			preparedStatement.setString(1, borrowDate);
			preparedStatement.setString(2, dueDate);
			preparedStatement.setString(3, book.getIsbn());
			preparedStatement.setString(4, userId);
			boolean add = preparedStatement.execute();
			preparedStatement = connection.prepareStatement("UPDATE gaga_library_book_test SET book_stock=? WHERE isbn=?");
			preparedStatement.setInt(1, book.getBookStock() - 1);
			preparedStatement.setString(2, book.getIsbn());
			int update = preparedStatement.executeUpdate();
			return add && update == 1;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean bookIsBorrow(String bookIsbn, String userId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM gaga_library_borrow_test WHERE is_delete=0 AND book_isbn=? AND user_id=?");
			preparedStatement.setString(1, bookIsbn);
			preparedStatement.setString(2, userId);

			if (preparedStatement.executeQuery().next()) {
				return true;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean dueBook(String bookIsbn, String userId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE gaga_library_borrow_test SET is_delete=1 WHERE user_id=? AND book_isbn=?");
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, bookIsbn);
			int updateDelete = preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement("SELECT book_stock FROM gaga_library_book_test WHERE isbn=?");
			preparedStatement.setString(1, bookIsbn);
			ResultSet resultSet = preparedStatement.executeQuery();
			int book_stock = 0;
			while (resultSet.next()) {
				book_stock = resultSet.getInt(1);
			}
			preparedStatement = connection.prepareStatement("UPDATE gaga_library_book_test SET book_stock=? WHERE isbn=?");
			preparedStatement.setInt(1, book_stock + 1);
			preparedStatement.setString(2, bookIsbn);
			int updateStock = preparedStatement.executeUpdate();
			return updateDelete == 1 && updateStock == 1;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean renewBook(String bookIsbn, User user, String borrowDate, String renewDate) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE gaga_library_borrow_test SET due_date=? WHERE book_isbn=? AND user_id=?");
			preparedStatement.setString(1, renewDate);
			preparedStatement.setString(2, bookIsbn);
			preparedStatement.setString(3, user.getUserId());
			int update = preparedStatement.executeUpdate();
			return update == 1;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return false;
	}
}
