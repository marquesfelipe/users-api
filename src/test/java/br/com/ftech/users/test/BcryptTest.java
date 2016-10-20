package br.com.ftech.users.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {
	
	public static void main(String[] args) {
		
		
		String pass = new BCryptPasswordEncoder().encode("123");
		System.out.println(pass);

	}

}
