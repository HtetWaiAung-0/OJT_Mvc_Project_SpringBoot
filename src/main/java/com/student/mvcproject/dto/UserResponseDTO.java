package com.student.mvcproject.dto;

public class UserResponseDTO {
	private String userId;
	private String userMail;
	private String userRole;
	private String userPassword;
	private String userConPassword;
	private String searchUserId;
	private String searchUserMail;
	public UserResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserMail() {
		return userMail;
	}
	public UserResponseDTO(String userId, String userMail,String userPassword,String userRole) {
		super();
		this.userId = userId;
		this.userMail = userMail;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserConPassword() {
		return userConPassword;
	}
	public void setUserConPassword(String userConPassword) {
		this.userConPassword = userConPassword;
	}
	public String getSearchUserId() {
		return searchUserId;
	}
	public void setSearchUserId(String searchUserId) {
		this.searchUserId = searchUserId;
	}
	public String getSearchUserMail() {
		return searchUserMail;
	}
	public void setSearchUserMail(String searchUserMail) {
		this.searchUserMail = searchUserMail;
	}
}
