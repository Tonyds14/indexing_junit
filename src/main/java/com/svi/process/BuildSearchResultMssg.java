package com.svi.process;

import java.util.ArrayList;

import com.svi.object.SearchInfo;

public class BuildSearchResultMssg {
	
	public void processSearchResults(String searchTerm, ArrayList<SearchInfo> searchResults) {
		int cntsrchresult = searchResults.size();
		
		System.out.println("Search keyword \"" +searchTerm + "\" found (" +cntsrchresult +") time/s in the ff locations:" );  
		
		for (SearchInfo innerList : searchResults) {
			int resultnum = (int) innerList.getResultNum();
			String filename = (String) innerList.getFileName();              
			String sheetname = (String) innerList.getSheetName();          
			int cellrow = (int) innerList.getCellRow();          
			String cellcol = (String) innerList.getCellCol();
		
			StringBuilder consolemssg = new StringBuilder(); // Append strings to the StringBuilder
			consolemssg.append("(" +resultnum+ ")File = \"" +filename +"\",   " );
			consolemssg.append("Sheet = \"" +sheetname+"\",   ");
			consolemssg.append("Row = \"" + cellrow+"\",   ");
			consolemssg.append("Col = \"" +cellcol+"\",   ");
			String finalmssg = consolemssg.toString();
			System.out.println(finalmssg);
		}
    }
	    	
}
