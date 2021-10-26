package domain;

public class User {
	
	private Long userId;
	private String email;
	private String imageUrl;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public User(Long userId, String email, String imageUrl) {
		super();
		this.userId = userId;
		this.email = email;
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", imageUrl=" + imageUrl + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
