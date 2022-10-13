package spring.com.security.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;



@Document(collection = "users")
public class User implements UserDetails {
	@Id
    @GeneratedValue(generator = "String2")
	private String id;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String adresse;
    private String codePostale;
    private String sexe;
    private String numTel;
    private Date dateNaissance;
    private String password;
    private boolean locked=false;
    private boolean enabled=false;
    private Collection<String> roles=new ArrayList<>();
    
    
    
    
    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	



	public User(String nom, String prenom, String username, String email, String adresse, String codePostale,
			String sexe, String numTel, Date dateNaissance, String password, boolean locked, boolean enabled,
			Collection<String> roles) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.email = email;
		this.adresse = adresse;
		this.codePostale = codePostale;
		this.sexe = sexe;
		this.numTel = numTel;
		this.dateNaissance = dateNaissance;
		this.password = password;
		this.locked = locked;
		this.enabled = enabled;
		this.roles = roles;
	}







	public User(String id, String nom, String prenom, String username, String email, String adresse, String codePostale,
			String sexe, String numTel, Date dateNaissance, String password, boolean locked, boolean enabled,
			Collection<String> roles) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.email = email;
		this.adresse = adresse;
		this.codePostale = codePostale;
		this.sexe = sexe;
		this.numTel = numTel;
		this.dateNaissance = dateNaissance;
		this.password = password;
		this.locked = locked;
		this.enabled = enabled;
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

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getCodePostale() {
		return codePostale;
	}



	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}



	public String getSexe() {
		return sexe;
	}



	public void setSexe(String sexe) {
		this.sexe = sexe;
	}



	public String getNumTel() {
		return numTel;
	}



	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}



	public Date getDateNaissance() {
		return dateNaissance;
	}



	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});

		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	
	
	
  
}
