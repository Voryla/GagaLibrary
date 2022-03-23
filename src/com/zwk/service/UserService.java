package com.zwk.service;

import com.zwk.dao.UserPropertyEnum;
import com.zwk.model.User;
import java.io.File;

public interface UserService {
	/**
	 * 用户登录
	 *
	 * @param userId
	 * @param pwd
	 * @param serviceMessage 提示信息
	 */
	User userLogin(String userId, String pwd, ServiceMessage serviceMessage);

	/**
	 * 管理员登录
	 *
	 * @param adminId
	 * @param pwd
	 * @param serviceMessage 提示信息
	 * @return
	 */
	User loginAsAdmin(String adminId, String pwd, ServiceMessage serviceMessage);

	/**
	 * 用户注册
	 *
	 * @param userId
	 * @param pwd
	 * @param serviceMessage 提示信息
	 */
	boolean userRegister(String userId, String pwd, ServiceMessage serviceMessage);

	/**
	 * View 层获取当前用户信息
	 *
	 * @return
	 */
	User getCurrentUser();

	/**
	 * 设置当前用户
	 * @param user
	 */
	void setCurrentUser(User user);

	/**
	 * 修改用户头像
	 * @param imageFile
	 * @return
	 */
	boolean changeUserImg(File imageFile,ServiceMessage serviceMessage);

	/**
	 * 获取用户密码
	 * @param userId
	 * @param serviceMessage
	 * @return
	 */
	String getUserPwd(String userId,ServiceMessage serviceMessage);

	/**
	 * 更改用户属性
	 * @param userId
	 * @param userPropertyEnum
	 * @param newValue
	 * @param serviceMessage
	 * @return
	 */
	boolean updateUserProperty(String userId, UserPropertyEnum userPropertyEnum, Object newValue, ServiceMessage serviceMessage);
}
