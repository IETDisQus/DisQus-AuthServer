package dto;

public class User {
	
	public User() {
		super();
	}
	private String userId;
	private String userName;
	private String userEmail;
	private String pictureUrl;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return userEmail;
	}
	public void setEmail(String email) {
		this.userEmail = email;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	public String getName() {
		return userName;
	}
	public void setName(String name) {
		this.userName = name;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + userEmail + ", pictureUrl=" + pictureUrl + "]";
	}

}
