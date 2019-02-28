package siga.capau.springsecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeraSenha {
	
	public static void main(String[] args) {
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
	    System.out.println(passwordEncoder.encode("cd"));  
	}

}
