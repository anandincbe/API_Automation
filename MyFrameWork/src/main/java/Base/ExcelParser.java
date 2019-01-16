package Base;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * <b>Name:</b> ExcelParser
 * <b>Description: </b>This class provide method to retrieve test data from an Excel workbook.
 *
 */
public class ExcelParser {
	private String fileName;
	private String testName;
	private String sheetName;
	public static Logger _log;
	
	public ExcelParser(String dataFilePath, String testName) throws Exception {
		this.testName = testName;
		this.fileName = dataFilePath;		
		this.sheetName="";
	}
	
	public ExcelParser(String dataFilePath, String testName,String sheetName) throws Exception {
		this.testName = testName;
		this.fileName = dataFilePath;		
		this.sheetName= sheetName;
	}

	/**
	 * <b>Name:</b> getLastCol<br>
	 * <b>Description:</b> Get number last column until Test_End.
	 * 
	 * @param sheet
	 * @param lastCol
	 * @return column index
	 */
	private int getLastCol(XSSFSheet sheet, String lastCol) {
		Iterator<?> row = sheet.getRow(0).cellIterator();
		int column = -1;
		while (row.hasNext()) {
			XSSFCell cell = (XSSFCell) row.next();
			if (lastCol.equalsIgnoreCase(cell.toString()))
				column = cell.getColumnIndex();
		}
		return column;
	}

	/**
	 * <b>Name:</b> readTestData<br>
	 * <b>Description:</b> This function will read the whole excel sheet and map
	 * the data into two-dimensional array which is compatible with TestNG
	 * DataProvider to provide real test data driven development.
	 * 
	 * @return testData two-dimensional array
	 * @throws Exception
	 */
	public ArrayList<HashMap<String, String>> readTestData() throws Exception {
		_log= Logger.getLogger(ExcelParser.class + ": readTestData");
		ArrayList<HashMap<String, String>> testData = new ArrayList<HashMap<String, String>>();
		try {
			OPCPackage pkg = OPCPackage.open(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(pkg);
			int i = 0; // ***row
			int j = 0; // ***column
			boolean isExecutedTestCase = true;
			XSSFSheet sheet = null;
			Iterator<?> rows = null;
			XSSFRow row = null;
			HashMap<String, String> testDataMap = null;
			XSSFCell cell = null;
			int maxCol = 0;
			//sheet = workbook.getSheetAt(1);
			if(sheetName=="")
				sheet = workbook.getSheetAt(1);
			else
				sheet = workbook.getSheet(sheetName);
			
			rows = sheet.rowIterator();
			maxCol = getLastCol(sheet, "Test_End");

			// ***Checks the rows
			for (i = 0; rows.hasNext(); i++) {
				row = (XSSFRow) rows.next();
				testDataMap = new HashMap<String, String>();

				// ***don't loop to the maxCol because it is a stopping
				// column(Test_End)
				for (j = 0; j < maxCol; j++) {
					cell = row.getCell(j);

					// ***Checks if column = 0 and cell null
					if (j == 0 && cell == null) {
						isExecutedTestCase = false;
						break;
					}

					// ***Checks the class name and test name's the same
					if (i >= 0 && j == 0) {
						if (!cell.getStringCellValue().startsWith(testName)) {
							isExecutedTestCase = false;
							break;
						}
					}

					// ***Checks if test case is executed or not. If not, go
					// to the next row.
					if (i > 0 && j == 2) {
						if (!cell.getStringCellValue().equalsIgnoreCase("YES")) {
							isExecutedTestCase = false;
							break;
						}
					}
					if (cell == null) {
						testDataMap.put(sheet.getRow(0).getCell(j)
								.getStringCellValue(), "");
					} else {

						String columnKey = sheet.getRow(0)
								.getCell(cell.getColumnIndex())
								.getStringCellValue();
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String columnVal = cell.getStringCellValue();
						testDataMap.put(columnKey, columnVal);
					}
				}
				/*
				 * reset to true if the test case is: not found, not executed,
				 * and test case header which has only 1 column
				 */
				if (!isExecutedTestCase || j == 1) {
					isExecutedTestCase = true;
					continue;
				}
				testData.add(testDataMap);
			}
		} catch (FileNotFoundException f) {
			StringWriter stackTrace = new StringWriter();
			f.printStackTrace(new PrintWriter(stackTrace));
			f.printStackTrace();
			throw f;
		} catch (Exception e) {
			StringWriter stackTrace = new StringWriter();
			e.printStackTrace(new PrintWriter(stackTrace));
			e.printStackTrace();
			throw e;
		}
		return testData;
	}
}

