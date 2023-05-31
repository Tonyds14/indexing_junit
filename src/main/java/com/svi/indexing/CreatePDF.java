package com.svi.indexing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePDF {
	
	public void createPdf(String searchTerm, String pdfName, String outputDirectory, ArrayList<SearchInfo> searchResults) {	
		//build output file name of PDF
		LocalDateTime timestamp = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
		String formattedTimestamp = timestamp.format(formatter);
        
		StringBuilder genfilename = new StringBuilder();
		genfilename.append(pdfName+searchTerm+"_");
		genfilename.append(formattedTimestamp); 		
		genfilename.append(".pdf");
		String outputFileName = genfilename.toString(); 	      	
		String outputPDF = (outputDirectory+outputFileName); 
		//	String file_name = "C:\\BA Training\\OUTPUT\\Search_Results_of_term_yyyy.MM.dd.HH.mm.ss.pdf";
		String file_name = outputPDF;
   	
		Document document = new Document();
	
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file_name));		
			document.open();			
		
			//font attributes
			Font fontHeader =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f, Font.UNDERLINE); 	
//			Font fontDtlHead =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f, Font.NORMAL, BaseColor.BLUE);
			Font fontDtlHead =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f, Font.NORMAL);
			Font fontDtl =  FontFactory.getFont(FontFactory.HELVETICA, 10f, Font.NORMAL); 				
			Font fontFooter =  FontFactory.getFont(FontFactory.HELVETICA, 8f, Font.ITALIC);  
			Font fontSearchTerm =  FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f, Font.UNDERLINE, BaseColor.BLUE);
		
			//Print Header Line of PDF
			int countresult = searchResults.size();
		
//			String pdfline1 = "Search keyword \"" +searchTerm + "\" found (" +countresult +") time/s in the ff locations:";  
//			Paragraph header1 = new Paragraph(pdfline1,fontHeader);	
//			document.add(header1);	
		
			//other style for line 1 in pdf
			String pdfline21 = "Search keyword \"";
			String pdfline22 = "\" found (" +countresult +") time/s in the ff locations:";
		
			Paragraph header2 = new Paragraph();
			header2.add(new Chunk(pdfline21, fontHeader));
			header2.add(new Chunk(searchTerm,fontSearchTerm));
			header2.add(new Chunk(pdfline22, fontHeader));			
			document.add(header2);				
		
			//define Table properties		
			PdfPTable table = new PdfPTable(5);  //table with 5 columns. search results table
			table.setWidthPercentage(80f); //Set table Width as 90%
			table.setSpacingBefore(20f); //Space before table
//      	table.setSpacingAfter(10f); //Space after table            
			float[] columnWidths = { 1f, 3f, 3f, 1f, 1f }; //Set Column widths of table. 2nd col will be 3x as 1st                                                  
			table.setWidths(columnWidths);
        
			// Add table headers column            
			PdfPCell hdrcol1 = new PdfPCell(new Paragraph("No.",fontDtlHead));
			PdfPCell hdrcol2 = new PdfPCell(new Paragraph("File Name",fontDtlHead));
			PdfPCell hdrcol3 = new PdfPCell(new Paragraph("Sheet",fontDtlHead));
			PdfPCell hdrcol4 = new PdfPCell(new Paragraph("Row",fontDtlHead));
			PdfPCell hdrcol5 = new PdfPCell(new Paragraph("Column",fontDtlHead));
		
			hdrcol1.setHorizontalAlignment(Element.ALIGN_CENTER);
			hdrcol4.setHorizontalAlignment(Element.ALIGN_CENTER);
			hdrcol5.setHorizontalAlignment(Element.ALIGN_CENTER);
        
			hdrcol1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hdrcol2.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hdrcol3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hdrcol4.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hdrcol5.setBackgroundColor(BaseColor.LIGHT_GRAY);            
        
			table.addCell(hdrcol1);
			table.addCell(hdrcol2);
			table.addCell(hdrcol3);
			table.addCell(hdrcol4);
			table.addCell(hdrcol5);   
		
			//print body of PDF
			if (countresult > 0) {
				for (SearchInfo innerList : searchResults) {
//					int resultnum = (int) innerList.getResultNum(); 
					String resultnum = String.valueOf(innerList.getResultNum()); //convert int type to string
					String filename = (String) innerList.getFileName();
					String sheetname = (String) innerList.getSheetName();
//					int cellrow = (int) innerList.getCellRow();  //convert int type to string
					String cellrow = String.valueOf(innerList.getCellRow());
					String cellcol = (String) innerList.getCellCol();
				
					PdfPCell dtlcol1 = new PdfPCell(new Paragraph(resultnum,fontDtl));
					PdfPCell dtlcol2 = new PdfPCell(new Paragraph(filename,fontDtl));
					PdfPCell dtlcol3 = new PdfPCell(new Paragraph(sheetname,fontDtl));
					PdfPCell dtlcol4 = new PdfPCell(new Paragraph(cellrow,fontDtl));
					PdfPCell dtlcol5 = new PdfPCell(new Paragraph(cellcol,fontDtl));
				
					dtlcol1.setHorizontalAlignment(Element.ALIGN_CENTER);
					dtlcol4.setHorizontalAlignment(Element.ALIGN_CENTER);
					dtlcol5.setHorizontalAlignment(Element.ALIGN_CENTER);
	            
					table.addCell(dtlcol1);
					table.addCell(dtlcol2);
					table.addCell(dtlcol3);
					table.addCell(dtlcol4);
					table.addCell(dtlcol5);					
				}			
			}		
			document.add(table);
		
			//print Footer of PDF		
			Paragraph footer = new Paragraph("\n* * * * * * * * * * N O T H I N G  F O L L O W S * * * * * * * * * *",fontFooter);
			footer.setAlignment(Element.ALIGN_CENTER);
			document.add(footer);	
		
			document.close();
			System.out.println("\nOutput File Created: "+outputFileName);
			System.out.println("Output File Directory: "+outputDirectory);
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}    
	}
} //for createPDF
