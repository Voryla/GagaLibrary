package com.zwk.model;

import javax.swing.ImageIcon;

/**
 * 用户model
 */
public class User {
	// 用户id
	private String userId;
	// 用户名
	private String userName;
	// 性别
	private String gender;
	// 专业
	private String major;
	//	借阅数
	private Byte borrowCount;
	// 头像
	private ImageIcon img;
	// 联系方式
	private String telephone_number;
	// 信誉值
	private Byte trust;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		if (null == userName)
			userName = "";
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		if (null == gender)
			gender = "保密";
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMajor() {
		if (null == major)
			major = "";
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public void setTrust(Byte trust) {
		this.trust = trust;
	}

	public Byte getBorrowCount() {
		if (null == borrowCount)
			borrowCount = 0;
		return borrowCount;
	}

	public void setBorrowCount(Byte borrowCount) {
		this.borrowCount = borrowCount;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public String getTelephone_number() {
		if (null == telephone_number)
			telephone_number = "";
		return telephone_number;
	}

	public void setTelephone_number(String telephone_number) {
		this.telephone_number = telephone_number;
	}

	public Byte getTrust() {
		return trust;
	}
}
