package dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String>{

}
