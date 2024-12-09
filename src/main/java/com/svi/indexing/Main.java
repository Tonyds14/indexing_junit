package com.svi.indexing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.svi.object.ConfigInfo;
import com.svi.object.InputReader;
import com.svi.object.ScannerInputReader;
import com.svi.object.SearchInfo;
import com.svi.process.BuildSearchResultMssg;
import com.svi.process.CreatePDF;
import com.svi.process.SearchExcelFile;

public class Main {
	
	static boolean flgexit = false;
	static boolean flgsearch = false;
	static boolean flgvalidopt = false;
	private static final int NTHREDS = 10;
	
	static String inputDirectory = "";
	static String outputDirectory = "";
	static String pdfName = "";
	static String version ="";	
	static List<File> files = new ArrayList<>();
	static long startTime;
	static long totalTime;
	
	static String createPDFind ="";
	
	//CREATE THREAD POOL FOR "executorService"
	static ExecutorService executorService = Executors.newFixedThreadPool(NTHREDS); //10 threads      
    //CREATE ARRAYLIST TO STORE Search Results
	static final ArrayList<SearchInfo> searchResults = new ArrayList<>();            
	//CREATE "completionService" to submit method that will call class SearchExcelFile,  method searchExcelFile()"
	static CompletionService<ArrayList<SearchInfo>> completionService = new ExecutorCompletionService<>(executorService);
	
	static Scanner scanner = new Scanner(System.in);
	static ScannerInputReader inputReader = new ScannerInputReader(scanner);
	
    public static void main(String[] args) throws Exception {
    	populateMainConfigFieldsAndFileList();    

    	displayMssgForZeroFiles(files);
    	
    	
    	while (flgexit == false) {
    		String searchTerm = displayHome(inputReader);
    		
    		setStartTimeandClearSearchResults();    		
    		
    		submitSearchTaskForEachExcelFile(searchTerm, files); 	    
            
//    		collectResults();
    		
            calculateSearchDuration();            		
    		
            BuildSearchResultMssg searchResultMssg = new BuildSearchResultMssg();
            searchResultMssg.processSearchResults(searchTerm, searchResults);           
                        
            askCreatePdf(searchTerm, inputReader);
    		
            askSearchAgain(inputReader);
    		  		
    	}   //for while loop statement
    } //end for main string args
    
    public static void populateMainConfigFieldsAndFileList() {
    	//Access config.properties
    	ConfigInfo configInfo = new ConfigInfo();
//    	inputDirectory = configInfo.getInputDirectory();
//    	outputDirectory = configInfo.getOutputDirectory();
    	inputDirectory = configInfo.inputDirectory;
    	outputDirectory = configInfo.outputDirectory;
    	pdfName = configInfo.getPdfName();
    	version =configInfo.getVersion();

    	System.out.println("Program Version: "+version);   
//    	String directoryPath = inputDirectory;    	
//    	List<File> files = getListOfExcelFiles(directoryPath);  
    	files = getListOfExcelFiles(inputDirectory);
    }
    
    public static void displayMssgForZeroFiles(List<File> files) {
    	if (files.size() == 0) {
        	System.out.println("No excel files found in Input Folder: " +inputDirectory);
        	flgexit=true;
        }
    }
  
     
    public static String displayHome(InputReader inputReader) {        
//        @SuppressWarnings("resource")
//    	Scanner scanner = new Scanner(System.in);
        System.out.print("\n\n\nPlease input the search term: ");
//        String searchTerm = scanner.nextLine();      
        String searchTerm = inputReader.nextLine();
        return searchTerm;           
    }    
    
    
    public static void setStartTimeandClearSearchResults() {
    	// Capture the starting time
	    startTime = System.currentTimeMillis();
	    searchResults.clear();
    }
    
    
    public static void submitSearchTaskForEachExcelFile(String searchTerm, List<File> files) throws InterruptedException, ExecutionException {
        // Submit search tasks for each Excel file
		for (final File file : files) {
        	completionService.submit(
        		new Callable<ArrayList<SearchInfo>>() {
        			public ArrayList<SearchInfo> call() throws Exception {
        				SearchExcelFile searchExcel = new SearchExcelFile();
        				return searchExcel.searchExcelFile(file, searchTerm, searchResults);
        			}
        		}
        	);
        }	
		
		//WAIT FOR ALL TASKS TO COMPLETE AND COLLECT RESULTS
		for (int i = 0; i < files.size(); i++) {
            Future<ArrayList<SearchInfo>> future = completionService.take();
            searchResults.addAll(future.get());
        }  
		
    }    
    
    
    public static void calculateSearchDuration() {
    	// Calculate Search Duration
        totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Search Duration: " + totalTime + " ms");    
    }
    
    
    public static void askCreatePdf(String searchTerm, InputReader inputReader) {
    	flgvalidopt = false;    		
		while(flgvalidopt == false) {    
			displayCreatePDFOpt(searchTerm, pdfName, outputDirectory, searchResults, inputReader);
			}
    }
    
    
    public static boolean displayCreatePDFOpt(String searchTerm, String pdfName, String outputDirectory, ArrayList<SearchInfo> searchResults, InputReader inputReader) {
//    	@SuppressWarnings("resource")
//		Scanner scanner = new Scanner(System.in);
    	
//    	String createPDFind = scanner.nextLine();
    	
    	System.out.println("\nPrint Search Results in PDF File? [Y/N] ");
    	createPDFind = inputReader.nextLine();
    	
    	switch(createPDFind.toUpperCase()) {
		case "Y":
			int chkresult = searchResults.size();			
			if (chkresult>0) {
				CreatePDF createpdf = new CreatePDF();
				createpdf.createPdf(searchTerm, pdfName, outputDirectory, searchResults);
			} else  
				System.out.println("\nPrint PDF disabled. Input Term \""+searchTerm+ "\" returned 0 search results.");
			flgvalidopt = true;
			break;
		case "N":
			flgvalidopt = true;
			break;
		default:
			System.out.println("Invalid input. Select between Y or N only");
			flgvalidopt = false;
			break;
    	}
		return flgvalidopt;
			
    } //for displayCreatePDFOpt
   
    
    public static void askSearchAgain(InputReader inputReader) {
    	flgvalidopt = false;
		while(flgvalidopt == false) {
			displayReSearchOpt(searchResults, inputReader);
			if (flgexit == true) {
				// Shutdown the thread pool
	            executorService.shutdown();
			}
		}  
    }
    
    
    public static boolean displayReSearchOpt(ArrayList<SearchInfo> searchResults,InputReader inputReader) {
//    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("\nSearch again for another term?  [Y/N] ");
//    	String researchind = scanner.nextLine();
    	String researchind = inputReader.nextLine();
    	
		switch(researchind.toUpperCase()) {
		case "Y":
			flgexit = false;
			flgvalidopt = true;
//			searchResults.clear();
			break;
		case "N":
			flgexit = true;
			flgvalidopt = true;
			scanner.close();
			System.out.println("\nEnd of Program");
			break;
		default:
			System.out.println("Invalid input. Select between Y or N only");
			flgvalidopt = false;
			break;	
		}
		return flgexit; 
    	
    } //for displayReSearchOpt
    
    
    public static List<File> getListOfExcelFiles(String path) {
    	//path is directoryPath
    	//initialize excelFiles as array list
    	List<File> excelFiles = new ArrayList<>();
    	Path directoryPath = Paths.get(path);
    	
    	//to catch non-existing folder path
    	if (!directoryPath.toFile().isDirectory()) {
            System.out.println("Invalid directory path: " + path);
            return excelFiles;
        }
    	
    	File[] files = directoryPath.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {
                    excelFiles.add(file);
                }
            }
        }         
		return excelFiles;
    }

	
    public static ArrayList<SearchInfo> getSearchresults() {
		return searchResults;
	}	
    
    
} //for Main
