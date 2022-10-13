package spring.com.security.service;

import spring.com.security.entities.ConfirmationToken;

public interface IConfirmationTokenService {

	public void saveConfirmationToken(ConfirmationToken confirmToken,String idUser);
	public String confirmToken(String token);
}
