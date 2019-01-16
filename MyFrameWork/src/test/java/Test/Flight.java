package Test;

import java.util.HashMap;

import org.testng.annotations.Test;

import Base.Base;

public class Flight extends Base {
	
	
	@Test(dataProvider = "get-test-data-method")
	public void flightReservation(HashMap<String, String> testData) {
		
	}
	
	
	@Test(dataProvider = "get-test-data-method")
	public void login(HashMap<String, String> testData) {
		
	}
	

}
