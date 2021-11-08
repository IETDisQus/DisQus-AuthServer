package customservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import configs.MapperConfig;
import dao.UserRepository;
import dto.User;
import dto.UserDTO;
import exceptions.UserAlreadyExistsException;
import models.UserEntity;

@Service
@EnableJpaRepositories("dao")
@EntityScan("models")
@Import(MapperConfig.class)
public class CustomUserServiceImpl implements CustomUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	 private ModelMapper modelMapper;
	
	private static final Logger log = 
		    org.slf4j.LoggerFactory.getLogger(CustomUserServiceImpl.class);

	@Override
	public List<User> getAllUsers() {
		try {
			List<UserEntity> userEntities =  (List<UserEntity>)userRepository.findAll();
			
			System.out.println("^^^^^^^^^^^^^^^^^^ "+userEntities.get(0).getUserName()+" ^^^^^^^^^^^^^^^^^^^^^^^");
			
			List<User> userList = new ArrayList<>();
			for(UserEntity ue : userEntities) {
				userList.add(convertToDto(Optional.of(ue)));
			}
			return userList;
		}
		catch(Exception e) {
			log.error("Exception while fetching data from database",e);
			return null;
		}
		
	}

	@Override
	public User getUserById(String userId) {
		try {
			if(userRepository.existsById(userId)) {
				 return convertToDto(userRepository.findById(userId));
			}
			else {
				log.info("User with userId: ",userId," does not exist");
				return null;
			}
		}
		catch(Exception e){
			log.error("Exception while fetching data for user: ",userId);
			return null;
		}
		
	}

	@Override
	public void deleteUser(String userId) {
		
		try {
			if(userRepository.existsById(userId)) {
				userRepository.deleteById(userId);
			}
			else {
				log.info("User with userId: ",userId," does not exist");
			}
		}
		catch(Exception e) {
			log.error("Exception while deleting user with userId: ",userId);
		}
		
	}
	
	public void createUser(Map<String, Object> attributes) {
		UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail((String)attributes.get("email"));
        userEntity.setUserId((String)attributes.get("sub"));
        userEntity.setPictureUrl((String)attributes.get("picture"));
        userEntity.setUserName((String)attributes.get("name"));
        
        try {
        	if(!userRepository.existsById(userEntity.getUserId())) {
        		userRepository.save(userEntity);
        	}
        	else {
        		throw new UserAlreadyExistsException("user already exist");
        	}
        	
        }
        catch(Exception e) {
        	log.error("Exception while saving data to DB ",e);
        }
		
	}
	

	@Override
	public UserDTO processUserAuthentication(Map<String, Object> attributes, OidcIdToken idToken,
			OidcUserInfo userInfo){
		
		System.out.println("@@@@@@@@@@@ Inside method processUserAuthentication @@@@@@@@@@@@@@");
		User user = new User();
		user.setEmail((String) attributes.get("email"));
        user.setUserId((String) attributes.get("sub"));
        user.setImageUrl((String) attributes.get("picture"));
        user.setName((String) attributes.get("name"));
		
        UserDTO userDto = new UserDTO(user, attributes,idToken,userInfo);
        
        createUser(attributes);
        
        return userDto;
		
	}
	
	private User convertToDto(Optional<UserEntity> ue) {
		User user = modelMapper.map(ue.get() ,User.class);
		return user;
	}
	
}
