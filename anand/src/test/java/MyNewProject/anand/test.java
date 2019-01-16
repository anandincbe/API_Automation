package MyNewProject.anand;

import java.util.HashMap;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class test {
	
	public static class CustomException extends Exception {
		
		 CustomException(String mesg)
		{
			System.out.println(mesg); 
		}
	}

	public static void main(String[] args) throws FilloException {
		// TODO Auto-generated method stub

		

		/*String excelFilePath="C:\\Users\\amule\\Desktop\\EnvironmentConfig_latest.xlsx";
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(excelFilePath);
		Recordset recordSet = connection.executeQuery("select * from UserList");
		HashMap<String , HashMap<String,String>> userData = new HashMap<String , HashMap<String,String>>();
		Object[]  list =new Object[recordSet.getFieldNames().size() ];
		list =recordSet.getFieldNames().toArray(); 
		HashMap<String , String> user = new HashMap<String , String>();
		while(recordSet.next()) {
			for(Object obj:list) {
				String key=obj.toString();
				user.put(key,recordSet.getField(key));
			}
			userData.put(user.get("UserKey"), new HashMap<>(user));
			user.clear();
			
		}
		*/
	
		
	
		

}
}
