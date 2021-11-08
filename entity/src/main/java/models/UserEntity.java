package models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity {
	
	public UserEntity() {
		super();
	}

	public UserEntity(String userId, String userName, String userEmail, String pictureUrl) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.pictureUrl = pictureUrl;
	}

	@Id
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}
