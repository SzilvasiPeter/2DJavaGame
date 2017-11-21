package com.thechema.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {

	private FileUtils(){
		
	}
	public static String loadAsString(String file){
		StringBuffer result = new StringBuffer();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = reader.readLine()) != null){
				result.append(buffer + '\n');
			}
			reader.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result.toString();
	}
	
}
