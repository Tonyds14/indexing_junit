package com.svi.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.svi.object.SearchInfo;

public class SearchExcelFile {
	private ArrayList<SearchInfo> results = new ArrayList<>();
//	private ArrayList<SearchInfo> searchResults = new ArrayList<>();  
	
	public ArrayList<SearchInfo> searchExcelFile(File file, String searchTerm, ArrayList<SearchInfo> searchResults) {
        
        FileInputStream fis = null;
        int resultnum = 0;
        try {
            fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis);

            for (Sheet sheet : workbook) {  // Iterate over each sheet in the workbook
                for (int rowNum = 0; rowNum <= 30; rowNum++) {  //iterate through rows 1-30
                	Row row = sheet.getRow(rowNum);
                	if (row != null) {
                		for (int colNum = 0; colNum <= 7; colNum++) {  //iterate through col A-H
                			Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                			String cellValue = cell.getStringCellValue();
                			// Check if the cell value contains the search term
                            if (cellValue.toLowerCase().contains(searchTerm.toLowerCase())) {   //sets both cell value and search term into lower case
                            	resultnum++;                  	
                            	String filename = file.getName();
                            	String sheetname = sheet.getSheetName();
                            	int cellrow = (row.getRowNum() + 1);
                            	
                                int cellcolvalue = (cell.getColumnIndex() + 1);
                                String cellcol;                            
                                switch (cellcolvalue) {
                                case 1: cellcol = "A";
    								break;
                                case 2: cellcol = "B";
    								break;
                                case 3: cellcol = "C";
    								break;
                                case 4: cellcol = "D";
    								break;
                                case 5: cellcol = "E";
    								break;
                                case 6: cellcol = "F";
    								break;
                                case 7: cellcol = "G";
    								break;
                                case 8: cellcol = "H";
    								break;
    							default: cellcol = "data not within A-H column";
    								break;                         
                                	}
                                searchResults.add(new SearchInfo(resultnum, filename, sheetname, cellrow, cellcol));
                            	}
                			} //for column loop
                		}
                	} //for row loop
            	} //for sheet loop
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return searchResults;
          return results;
    }

	public ArrayList<SearchInfo> getResults() {
		return results;
	}

	public void setResults(ArrayList<SearchInfo> results) {
		this.results = results;
	}

	
}
