package com.projeto.agenda_pessoal.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//System.out.println(encoder.encode("angul@r"));
		System.out.println(encoder.encode("m0b1l30"));
	}

}
