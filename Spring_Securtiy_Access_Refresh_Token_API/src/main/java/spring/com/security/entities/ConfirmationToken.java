package spring.com.security.entities;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tokens")
public class ConfirmationToken {
	@Id
    @GeneratedValue(generator = "String2")
	private String id;
    private String token;
    private LocalDateTime cretationDate;
    private LocalDateTime expirationDate;
    private LocalDateTime confirmationDate;
    private String idUser;
    
	public ConfirmationToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public ConfirmationToken(String id, String token, LocalDateTime cretationDate, LocalDateTime expirationDate,
			LocalDateTime confirmationDate, String idUser) {
		super();
		this.id = id;
		this.token = token;
		this.cretationDate = cretationDate;
		this.expirationDate = expirationDate;
		this.confirmationDate = confirmationDate;
		this.idUser = idUser;
	}

	



	public ConfirmationToken(String token, LocalDateTime cretationDate, LocalDateTime expirationDate,
			LocalDateTime confirmationDate, String idUser) {
		super();
		this.token = token;
		this.cretationDate = cretationDate;
		this.expirationDate = expirationDate;
		this.confirmationDate = confirmationDate;
		this.idUser = idUser;
	}




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getCretationDate() {
		return cretationDate;
	}
	public void setCretationDate(LocalDateTime cretationDate) {
		this.cretationDate = cretationDate;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}
	public LocalDateTime getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(LocalDateTime confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
    
    
}
