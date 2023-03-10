package com.service;

import org.springframework.stereotype.Service;

@Service
public class Tokenservice {
	public String generateToken(int size) {
		if(size>50) {
			size=45;
		}
		 String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "0123456789"
		         + "abcdefghijklmnopqrstuvxyz";
		 StringBuilder sb = new StringBuilder(size);

		  for (int i = 0; i < size; i++) {
		   int index
		    = (int)(AlphaNumericString.length()
		      * Math.random());
	
		   sb.append(AlphaNumericString
		      .charAt(index));
		  }
		 
		  return sb.toString();
		 }
		
	}