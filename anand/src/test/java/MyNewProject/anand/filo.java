package MyNewProject.anand;

import java.util.HashMap;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class filo {

	public static void main(String[] args) throws FilloException {
		// TODO Auto-generated method stub
		Fillo fillo = new Fillo();
		String path ="C:\\Users\\amule\\Desktop\\EnvironmentConfigLocal.xlsx";
		HashMap<String ,HashMap<String,String>> datamap= new HashMap<String ,HashMap<String,String>>();
		Connection conn= fillo.getConnection(path);
		Recordset rs = conn.executeQuery("Select * from EnvironmentInfo");
		
		Object[] list = new Object[rs.getFieldNames().size()];
		list = rs.getFieldNames().toArray();
		HashMap<String ,String> data = new HashMap<String ,String>();
		while(rs.next()){
			for(Object obj:list) {
				String key = obj.toString();
				data.put(key, rs.getField(key));
				//System.out.println(data);
			}
			datamap.put(data.get("EnvironmentName"), data);
		}
		
		HashMap<String, String > temp=datamap.get("182INTMAIN");
		System.out.println(temp.get("LC_UserKey"));
	}

}
