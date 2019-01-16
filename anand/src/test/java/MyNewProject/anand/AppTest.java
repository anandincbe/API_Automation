package MyNewProject.anand;

import java.io.File;
import java.util.HashMap;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class AppTest {

	public static void main(String[] args) throws FilloException {
		// TODO Auto-generated method stub
		Fillo fillo = new Fillo();
		HashMap<String, HashMap<String, String>> userListDataMap  = new HashMap<String, HashMap<String, String>>();
		
		//Recordset recordSet = null;
		String excelFilePath="C:\\Users\\amule\\Desktop\\EnvironmentConfigLocal.xlsx";
		Connection connection = fillo.getConnection(excelFilePath);
		Recordset recordSet = connection.executeQuery("Select * from UserList");
		
		Object[] list =	new Object[ recordSet.getFieldNames().size()];
		HashMap<String, String> userData = new HashMap<String, String>();
		list = recordSet.getFieldNames().toArray();
		
		while (recordSet.next()) {
			for(Object obj :list) {
				String key = obj.toString();
				userData.put(key, recordSet.getField(key));		
			}
			userListDataMap.put(userData.get("UserKey"), new HashMap<>(userData));
			userData.clear();
			//System.out.println(userData);
			
		}	
		System.out.println(userListDataMap);
	}

}
