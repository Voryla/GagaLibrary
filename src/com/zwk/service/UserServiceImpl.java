package com.zwk.service;

import com.zwk.dao.UserDAO;
import com.zwk.dao.UserDAOImpl;
import com.zwk.dao.UserPropertyEnum;
import com.zwk.model.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UserServiceImpl implements UserService {
	private final UserDAO userDAO = new UserDAOImpl();
	private User user;

	@Override
	public User userLogin(String userId, String pwd, ServiceMessage serviceMessage) {
		user = userDAO.userLogin(userId, pwd);
		if (null == user) {
			serviceMessage.showMessage("账号或密码错误", false);
		} else {
			serviceMessage.showMessage("登录成功..", true);
		}
		return user;
	}

	@Override
	public User loginAsAdmin(String adminId, String pwd, ServiceMessage serviceMessage) {
		user = userDAO.loginAsAdmin(adminId, pwd);
		if (null == user) {
			serviceMessage.showMessage("账号或密码错误", false);
		} else {
			serviceMessage.showMessage("登录成功..", true);
		}
		return user;
	}

	@Override
	public boolean userRegister(String userId, String pwd, ServiceMessage serviceMessage) {
		if (userDAO.userIsExist(userId)) {
			serviceMessage.showMessage("账号已存在！", false);
			return false;
		}
		if (userDAO.userRegister(userId, pwd)) {
			serviceMessage.showMessage("注册成功！", true);
			return true;
		}
		return false;
	}

	@Override
	public User getCurrentUser() {
		return user;
	}

	@Override
	public void setCurrentUser(User user) {
		this.user = user;
	}

	@Override
	public boolean changeUserImg(File imageFile, ServiceMessage serviceMessage) {
		// 若修改成功，将当前用户的img属性更改
		if (userDAO.userChangeImg(imageFile, user.getUserId())) {
			BufferedImage bi;
			try {
				bi = ImageIO.read(imageFile);
				user.setImg(new ImageIcon(bi));
			} catch (IOException e) {
				e.printStackTrace();
			}
			serviceMessage.showMessage("头像修改成功", true);
			return true;
		} else {
			serviceMessage.showMessage("头像修改失败", true);
			return false;
		}
	}

	@Override
	public String getUserPwd(String userId, ServiceMessage serviceMessage) {
		String userPwd = userDAO.getUserPwd(userId);
		if (null != userPwd) {
			serviceMessage.showMessage("获取密码成功", true);
			return userPwd;
		} else {
			serviceMessage.showMessage("获取密码失败", false);
			return null;
		}
	}

	@Override
	public boolean updateUserProperty(String userId, UserPropertyEnum userPropertyEnum, Object newValue, ServiceMessage serviceMessage) {

		if (userDAO.updateUserProperty(userId, userPropertyEnum, newValue)) {
			serviceMessage.showMessage("更改用户" + userPropertyEnum.toString() + "成功", true);
			return true;
		} else {
			serviceMessage.showMessage("更改用户" + userPropertyEnum.toString() + "失败", false);
			return false;
		}
	}
}
