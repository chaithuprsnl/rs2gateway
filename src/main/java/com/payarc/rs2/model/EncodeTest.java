package com.payarc.rs2.model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import com.payarc.rs2.service.IsoUtils;

public class EncodeTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
/*		String str = "Hello";
		byte[] b = str.getBytes();
		byte[] bb = str.getBytes("cp037");
		byte[] bbb = str.getBytes("cp1047");
		//BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes("cp037")),"cp037"));
		//System.out.println(br.readLine());
		*/	
		String str2 = "723C448108E18010";
		String binary = hexToBinary(str2);
		System.out.println("Binary: "+binary);
		String hex = IsoUtils.binary2hex(binary);
		System.out.println("Hex: "+hex);
    }
		
		//data.getBytes("cp037"),	StandardCharsets.ISO_8859_1);
		
		/*
		 * Iterator<String> iterator = Charset.availableCharsets().keySet().iterator();
		 * while(iterator.hasNext()) { String key = iterator.next();
		 * System.out.println("key: "+key); }
		 */
	
	public void binaryToHex(String binary) {
		/*
		 * 1. Convert each 8 bits of binary to decimal. 
		 * 2. Convert decimal to hex
		 * 3. Make sure each byte converts to 2 hexadecimal characters.
		 */
		String hex="", temp = "";
		for(int i=0; i<binary.length(); i+=8) {
			String bytes = binary.substring(i, i+8);
			int dec = 0;
			for(int j=0, k=bytes.length()-1; k>=0; k--) {
				dec += Math.pow(2, k) * Integer.parseInt(""+bytes.charAt(k));
			}
			temp = "0" + Integer.toHexString(dec);
			hex += temp.substring(temp.length()-2);
		}
		
	}
	
	public static String hexToBinary(String hex) {
		/*
		 * 0. If hex string is odd length make it even by adding a leading 0
		 * 1. Convert each character of hexadecimal to decimal. Use Character.digit
		 * 2. Convert decimal to binary
		 * 3. Make sure each character of hex converts to 4 bits binary.
		 */
		if(hex.length()%2!=0) {
			hex = "0"+hex;
		}
		String binary = "", temp="";
		for(int i=0;i<hex.length();i++) {
			int decimal = Character.digit(hex.charAt(i), 16);
			temp = "0000" + Integer.toBinaryString(decimal);
			binary += temp.substring(temp.length() - 4);
		}
		return binary;
	}
	
	}


