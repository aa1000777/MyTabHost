package com.corpit.qr.tools;

public class Security {

	public static String encript(String string, int key){
		String result = "";
		for(int i = 0; i < string.length(); i++){
			char tempChar = string.charAt(i);
			if((int)tempChar + key > 126){
				result += (char)((((int)tempChar + key)-127)+32);
			}
			else{
				result += (char)((int)tempChar + key);
			}
		}
		return result;
	}
	
	public static String decrypt(String string, int key){
		String result = "";
		for(int i = 0; i < string.length(); i++){
			char tempChar = string.charAt(i);
			if((int)tempChar - key < 32){
				result += (char)((((int)tempChar - key)+127)-32);
			}
			else{
				result += (char)((int)tempChar - key);
			}
		}
		return result;
	}
}
