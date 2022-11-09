package spring.com.security.service;

public interface EmailSender {

	void send (String to,String email);
	String buildEmail(String name,String link);
	String buildForgetPasswordEmail(String name,String link);
}
