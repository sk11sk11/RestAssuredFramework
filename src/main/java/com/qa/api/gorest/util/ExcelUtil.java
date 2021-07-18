package com.qa.api.gorest.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	public static Workbook book;
	public static Sheet sheet;

	// Create a String:
	public static String TESTDATA_SHEET_PATH = "/Users/shashank/eclipse-workspace/Nov2019RestAssuredFramework/src/main/java/com/qa/api/gorest/testdata";

	public static Object[][] getTestData(String sheetName) {

		// FileInputStream is used to create a collection with any kind of file...just
		// need to give file location.
		try {
			FileInputStream ip = new FileInputStream(TESTDATA_SHEET_PATH); // makes connection
			// workbookfactory loads a local copy of inside of java memory.
			try {
				book = WorkbookFactory.create(ip); // at book level
				sheet = book.getSheet(sheetName); // at sheet level
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//create 2D object array:				//rows				//cols
		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int i =0; i<sheet.getLastRowNum(); i++) {
			for(int k=0; k<sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i+1).getCell(k).toString();
				
			}
		}
		
		return data;
		
	}
}
