package com.managersystem.sisclinica.api.model;

import java.util.Random;

public class GerarToken {
	
	public static String gerarToken () {
		
		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghiklmnopqrstuvxywz0123456789";
		Random random = new Random();
		 
		String token = "";
		int index = -1;
		for( int i = 0; i < 40; i++ ) {
		   index = random.nextInt( letras.length() );
		   token += letras.substring( index, index + 1 );
		}

		return token;
	}

}
