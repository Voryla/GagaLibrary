package com.zwk.dao;

import com.zwk.model.User;
import java.io.File;

public interface UserDAO {
	/**
	 * 用户登录
	 *
	 * @param userId
	 * @param pwd
	 * @return 用户model
	 */
	User userLogin(String userId, String pwd);

	/**
	 * 用户注册
	 *
	 * @param userId
	 * @param pwd
	 * @return 标识是否执行成功
	 */
	boolean userRegister(String userId, String pwd);

	/**
	 * 管理员登录
	 *
	 * @param adminId
	 * @param pwd
	 * @return
	 */
	User loginAsAdmin(String adminId, String pwd);

	/**
	 * 查询用户是否存在
	 *
	 * @param userId
	 * @return 标识是否执行成功
	 */
	boolean userIsExist(String userId);

	/**
	 * 修改用户头像
	 * @param imageFile
	 * @return
	 */
	boolean userChangeImg(File imageFile,String userId);

	/**
	 * 获取用户密码
	 * @param userId
	 * @return
	 */
	String getUserPwd(String userId);

	/**
	 * 根据UserProperty更改用户信息
	 * @param userId
	 * @param userPropertyEnum
	 * @param newValue
	 * @return
	 */
	boolean updateUserProperty(String userId, UserPropertyEnum userPropertyEnum, Object newValue);

}
