package com.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		 Properties prop;
			prop = new Properties();
			
			try {
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/config/config.properties");
				try {
					prop.load(fis);
					System.out.println(prop.getProperty("URL"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }
	    

	}


