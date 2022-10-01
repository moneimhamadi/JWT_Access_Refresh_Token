package spring.com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import spring.com.security.entities.User;

public interface IUserRepository extends MongoRepository<User, String> {

	public User findByUsername(String username);
}
