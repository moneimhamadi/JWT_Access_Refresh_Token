package spring.com.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import spring.com.security.entities.ConfirmationToken;

@Repository
public interface ITokenRepository extends MongoRepository<ConfirmationToken, String> {
	
	List<ConfirmationToken> findByIdUser(String idUser);
	Optional<ConfirmationToken> findByToken(String token);

}
