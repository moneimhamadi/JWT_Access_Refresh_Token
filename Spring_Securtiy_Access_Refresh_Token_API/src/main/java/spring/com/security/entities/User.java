package spring.com.security.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
	@Id
    @GeneratedValue(generator = "String2")
	private String id;
    private String nom;
    private String username;
    private String password;
    private Collection<String> roles=new ArrayList<>();
    
    
    
	public User(String id, String nom, String username, String password, Collection<String> roles) {
		super();
		this.id = id;
		this.nom = nom;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Collection<String> getRoles() {
		return roles;
	}



	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}
	
	
    
    
    
}
