package com.zwk.dao;

import com.zwk.model.User;
import com.zwk.utils.JDBCUtils;
import com.zwk.utils.Log4jInit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UserDAOImpl implements UserDAO {
	private static Connection connection = null;
	static {
		try {
			connection = JDBCUtils.getConnection();
			// 连接数据库成功
			Log4jInit.getLogger(UserDAOImpl.class).info("Mysql Connection success " + connection.getClass());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public UserDAOImpl() {

	}

	@Override
	public User userLogin(String userId, String pwd) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT user_pwd,name,gender,trust,major,borrow_count,img,telephone_number" +
							" FROM gaga_library_user_test WHERE user_id =?");
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			// 登录成功，加载账户信息
			if (resultSet.next()) {
				if (pwd.equals(resultSet.getString("user_pwd"))) {
					User user = new User();
					user.setUserId(userId);
					user.setTrust((byte)resultSet.getInt("trust"));
					user.setUserName(resultSet.getString("name"));
					user.setGender(resultSet.getString("gender"));
					user.setMajor(resultSet.getString("major"));
					user.setBorrowCount(resultSet.getByte("borrow_count"));
					user.setTelephone_number(resultSet.getString("telephone_number"));
					InputStream binaryStream = resultSet.getBinaryStream("img");
					if (null != binaryStream) {
						BufferedImage bufferedImage = ImageIO.read(binaryStream);
						user.setImg(new ImageIcon(bufferedImage));
					}
					if (user.getUserName() == null) {
						user.setUserName("user");
					}
					resultSet.close();
					preparedStatement.close();
					System.out.println(user.getUserId());
					System.out.println(user.getUserName());
					return user;
				}
			}

		} catch (SQLException | IOException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean userRegister(String userId, String pwd) {
		try {
			if (userIsExist(userId)) {
				return false;
			}
			PreparedStatement preparedStatement = this.connection.prepareStatement(
					"INSERT INTO gaga_library_user_test(user_id,user_pwd) VALUES (?,?)");
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, pwd);
			return preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User loginAsAdmin(String adminId, String pwd) {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
					"SELECT admin_pwd FROM gaga_library_admin_test WHERE admin_id =?");
			preparedStatement.setString(1, adminId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (pwd.equals(resultSet.getString("admin_pwd"))) {
					User user = new User();
					user.setUserId(adminId);
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean userIsExist(String userId) {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
					"SELECT id FROM gaga_library_user_test WHERE user_id=?");
			preparedStatement.setString(1, userId);
			if (preparedStatement.executeQuery().next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// new File(TodoImages.class.getResource("/images/login_backGround_img.png").getPath());
	@Override
	public boolean userChangeImg(File imageFile, String userId) {
		int count = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE  gaga_library_user_test SET  img= ? WHERE user_id=?");
			try {
				InputStream fileInputStream = new FileInputStream(imageFile);
				preparedStatement.setBinaryStream(1, fileInputStream, fileInputStream.available());
				preparedStatement.setString(2, userId);
			} catch (IOException | SQLException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			}
			count = preparedStatement.executeUpdate();

		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		return count == 1;
	}

	@Override
	public String getUserPwd(String userId) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT user_pwd FROM gaga_library_user_test WHERE user_id=?");
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("user_pwd");
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUserProperty(String userId, UserPropertyEnum userPropertyEnum, Object newValue) {
		try {
			String filedName = "";
			switch (userPropertyEnum) {
				case USER_PWD:
					filedName = "user_pwd";
					break;
				case USER_NAME:
					filedName = "name";
					break;
				case USER_MAJOR:
					filedName = "major";
					break;
				case USER_GENDER:
					filedName = "gender";
					break;
				case USER_TELEPHONE:
					filedName = "telephone_number";
					break;
				case TRUST:
					filedName="trust";
					break;
				case USER_BORROW_COUNT:
					filedName="borrow_count";
					break;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE gaga_library_user_test SET " + filedName + "=? WHERE user_id=?");
			preparedStatement.setString(1, newValue.toString());
			preparedStatement.setString(2, userId);
			int resultCount = preparedStatement.executeUpdate();
			if (resultCount == 1)
				return true;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return false;
	}
}
