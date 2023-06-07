package com.svi.indexing;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

//import com.svi.indexing.Main.ScannerWrapper;

import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


class IndexingTest {  
    GetExpectedResults expected = new GetExpectedResults();  //get parameters of expectedresults.properties
    String expectedInputDirectory = expected.getInputDirectory();
    String expectedOutputDirectory = expected.getOutputDirectory();
    String expectedPdfName = expected.getPdfName();
    String expectedVersion = expected.getVersion();
	
	@Test
    public void test1AccessConfigInfo() {
        // Test inputDirectory functionality
        ConfigInfo configInfo = new ConfigInfo();
        String inputDirectory = configInfo.getInputDirectory();
        String outputDirectory = configInfo.getOutputDirectory();
        String pdfName = configInfo.getPdfName();
        String version = configInfo.getVersion();
        
        // Add assertions or other testing logic
        assertNotNull(inputDirectory);
        assertNotNull(outputDirectory);
        assertNotNull(pdfName);
        assertNotNull(version);
        
        String inputPath = expectedInputDirectory;
//        String inputPath = "C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\";
        assertEquals(inputDirectory,inputPath);
        String outputPath = expectedOutputDirectory;
//        String outputPath = "C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\OUTPUT\\";
        assertEquals(outputDirectory,outputPath);  
        String outPdfName = expectedPdfName;
        assertEquals(pdfName,outPdfName); 
        String outPgmVersion = expectedVersion;
        assertEquals(version,outPgmVersion);
    }    
    
    @Test
    public void test2PopulateMainConfigFields() {
//    	Main main = new Main();
    	Main.populateMainConfigFieldsAndFileList();    	
    }
    
    @Test
    public void test3GetListofExcelFiles() {
//    	Main main = new Main();
    	String inputDirectory = expectedInputDirectory;
//    	String inputDirectory = "C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\";
    	
    	List<File> expectedFiles = new ArrayList<>();
    	File file1 = new File(expectedInputDirectory +"Random2.xlsx");
        File file2 = new File(expectedInputDirectory +"Random1.xlsx");
        File file3 = new File(expectedInputDirectory +"Dog.xlsx");
        File file4 = new File(expectedInputDirectory +"Cat.xlsx");
        File file5 = new File(expectedInputDirectory +"BearBoarBird.xlsx");
//        File file1 = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Random2.xlsx");
//        File file2 = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Random1.xlsx");
//        File file3 = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Dog.xlsx");
//        File file4 = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Cat.xlsx");
//        File file5 = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\BearBoarBird.xlsx");
    	
        expectedFiles.add(file1);
        expectedFiles.add(file2);
        expectedFiles.add(file3);
        expectedFiles.add(file4);
        expectedFiles.add(file5);        
    	
    	List<File> actualFiles = Main.getListOfExcelFiles(inputDirectory);
    	
    	// Assert the size and contents of the list
        Assertions.assertEquals(5, expectedFiles.size());
        Assertions.assertTrue(expectedFiles.contains(file1));
        Assertions.assertTrue(expectedFiles.contains(file2));
        Assertions.assertTrue(expectedFiles.contains(file3));
        Assertions.assertTrue(expectedFiles.contains(file4));
        Assertions.assertTrue(expectedFiles.contains(file5));
        
        // Assert the results
        Assertions.assertEquals(expectedFiles.size(), actualFiles.size());
        Assertions.assertTrue(expectedFiles.containsAll(actualFiles));
        Assertions.assertTrue(actualFiles.containsAll(expectedFiles));        
    }        
    
   
	@Test
	public void test4SearchExcel() {
		// Prepare test data
		File testFile = new File(expectedInputDirectory +"Random1.xlsx");
//		File testFile = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Random1.xlsx");
        String searchTerm = "chick";         
        
        //build data of expectedResults array
        List<SearchInfo> expectedResults = new ArrayList<>();
        expectedResults.add(new SearchInfo(1, "Random1.xlsx", "Random1-Sheet1",16,"G"));
        expectedResults.add(new SearchInfo(2, "Random1.xlsx", "Random1-Sheet1",17,"G"));
        expectedResults.add(new SearchInfo(3, "Random1.xlsx", "Random1-Sheet1",18,"G"));
        expectedResults.add(new SearchInfo(4, "Random1.xlsx", "Random1-Sheet1",19,"G"));
        expectedResults.add(new SearchInfo(5, "Random1.xlsx", "Random1-Sheet1",22,"D"));
        expectedResults.add(new SearchInfo(6, "Random1.xlsx", "Random1-Sheet1",23,"B"));
        expectedResults.add(new SearchInfo(7, "Random1.xlsx", "Random1-Sheet1",23,"C"));
        
        ArrayList<SearchInfo> searchResults = new ArrayList<>();
        SearchExcelFile search = new SearchExcelFile();        
        search.searchExcelFile(testFile, searchTerm, searchResults);
        
        List<SearchInfo> actualResults = new ArrayList<>();
     
        //build data of actualResults array
        for (SearchInfo searchInfo : searchResults) {
        	int resultnum = searchInfo.getResultNum();
        	String filename = searchInfo.getFileName();
        	String sheetname = searchInfo.getSheetName();
        	int cellrow = searchInfo.getCellRow();
        	String cellcol = searchInfo.getCellCol();
        	
        	actualResults.add(new SearchInfo(resultnum, filename, sheetname, cellrow, cellcol));
        }
        
        System.out.println("Search Results Size1: " +actualResults.size());
        
//        // Assert the results
        Assertions.assertEquals(expectedResults.size(), actualResults.size());
        for (int i = 0; i < expectedResults.size(); i++) {
            SearchInfo expected = expectedResults.get(i);
            SearchInfo actual = actualResults.get(i);
            Assertions.assertEquals(expected.getResultNum(), actual.getResultNum());
            Assertions.assertEquals(expected.getFileName(), actual.getFileName());
            Assertions.assertEquals(expected.getSheetName(), actual.getSheetName());
            Assertions.assertEquals(expected.getCellRow(), actual.getCellRow());
            Assertions.assertEquals(expected.getCellCol(), actual.getCellCol());            
        }
        
	}	
	
	
	@Test
	public void test4bSearchExcel() {
		// Prepare test data
		File testFile = new File(expectedInputDirectory +"Dog.xlsx");
//		File testFile = new File("C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\INPUT\\Random1.xlsx");
        String searchTerm = "dog";         
        
        ArrayList<SearchInfo> searchResults = new ArrayList<>();
        SearchExcelFile search = new SearchExcelFile();        
        search.searchExcelFile(testFile, searchTerm, searchResults);
        
        List<SearchInfo> actualResults = new ArrayList<>();
        
	}	
	
	
	
	@Test
	public void test5BuildSearchResultMssg() {
		BuildSearchResultMssg build = new BuildSearchResultMssg();
		//test data
		String mocksearchTerm = "chicken";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		mocksearchResults.add(new SearchInfo(1, "Random1.xlsx", "Random1-Sheet1",16,"G"));
		mocksearchResults.add(new SearchInfo(2, "Random1.xlsx", "Random1-Sheet1",17,"G"));
		mocksearchResults.add(new SearchInfo(3, "Random1.xlsx", "Random1-Sheet1",18,"G"));
		mocksearchResults.add(new SearchInfo(4, "Random1.xlsx", "Random1-Sheet1",19,"G"));
		mocksearchResults.add(new SearchInfo(5, "Random1.xlsx", "Random1-Sheet1",22,"D"));
		mocksearchResults.add(new SearchInfo(6, "Random1.xlsx", "Random1-Sheet1",23,"B"));
		mocksearchResults.add(new SearchInfo(7, "Random1.xlsx", "Random1-Sheet1",23,"C"));	
		
		build.processSearchResults(mocksearchTerm, mocksearchResults);
	}
	
	@Test
	public void test6CreatePDF() {
		CreatePDF create = new CreatePDF();
		//test data
		String mocksearchTerm = "chicken";
		String mockpdfName ="Mock_Search_Results_of_";
		String mockoutputDirectory =expectedOutputDirectory;
//		String mockoutputDirectory ="C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\OUTPUT\\";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		mocksearchResults.add(new SearchInfo(1, "Random1.xlsx", "Random1-Sheet1",16,"G"));
		mocksearchResults.add(new SearchInfo(2, "Random1.xlsx", "Random1-Sheet1",17,"G"));
		mocksearchResults.add(new SearchInfo(3, "Random1.xlsx", "Random1-Sheet1",18,"G"));
		mocksearchResults.add(new SearchInfo(4, "Random1.xlsx", "Random1-Sheet1",19,"G"));
		mocksearchResults.add(new SearchInfo(5, "Random1.xlsx", "Random1-Sheet1",22,"D"));
		mocksearchResults.add(new SearchInfo(6, "Random1.xlsx", "Random1-Sheet1",23,"B"));
		mocksearchResults.add(new SearchInfo(7, "Random1.xlsx", "Random1-Sheet1",23,"C"));	
				
		create.createPdf(mocksearchTerm, mockpdfName, mockoutputDirectory, mocksearchResults);
	}
	
	@Test
    public void test7SetStartTimeandClearSearchResults() {
//    	Main main = new Main();
    	Main.setStartTimeandClearSearchResults();    	
    }
	
	

	@Test
    public void test8SubmitSearchTaskForEachExcelFile() throws InterruptedException, ExecutionException {
		//test data
		List<File> mockFiles = new ArrayList<>();
	 	File file1 = new File(expectedInputDirectory +"Random2.xlsx");
        File file2 = new File(expectedInputDirectory +"Random1.xlsx");
        File file3 = new File(expectedInputDirectory +"Dog.xlsx");
        File file4 = new File(expectedInputDirectory +"Cat.xlsx");
        File file5 = new File(expectedInputDirectory +"BearBoarBird.xlsx");
        mockFiles.add(file1);
        mockFiles.add(file2);
        mockFiles.add(file3);
        mockFiles.add(file4);
        mockFiles.add(file5);
        
        // Mock the search term
        String mockSearchTerm = "chicken";
		
//        ArrayList<SearchInfo> mockSearchResults = new ArrayList<>();
        
		//build data of expectedResults array
        List<SearchInfo> expectedResults = new ArrayList<>();
        expectedResults.add(new SearchInfo(1, "Random1.xlsx", "Random1-Sheet1",16,"G"));
        expectedResults.add(new SearchInfo(2, "Random1.xlsx", "Random1-Sheet1",17,"G"));
        expectedResults.add(new SearchInfo(3, "Random1.xlsx", "Random1-Sheet1",18,"G"));
        expectedResults.add(new SearchInfo(4, "Random1.xlsx", "Random1-Sheet1",19,"G"));
        expectedResults.add(new SearchInfo(5, "Random1.xlsx", "Random1-Sheet1",22,"D"));
        expectedResults.add(new SearchInfo(6, "Random1.xlsx", "Random1-Sheet1",23,"B"));
        expectedResults.add(new SearchInfo(7, "Random1.xlsx", "Random1-Sheet1",23,"C"));
        
        // Call the method under test
        Main.submitSearchTaskForEachExcelFile(mockSearchTerm, mockFiles);
        
        // Assert the search results
        ArrayList<SearchInfo> actualResults = Main.getSearchresults();
        Assertions.assertEquals(expectedResults.size(), actualResults.size());
        System.out.println("actualResults.size(): "+actualResults.size());
        
        for (int i = 0; i < expectedResults.size(); i++) {
            SearchInfo expected = expectedResults.get(i);
            SearchInfo actual = actualResults.get(i);
            Assertions.assertEquals(expected.getResultNum(), actual.getResultNum());
            Assertions.assertEquals(expected.getFileName(), actual.getFileName());
            Assertions.assertEquals(expected.getSheetName(), actual.getSheetName());
            Assertions.assertEquals(expected.getCellRow(), actual.getCellRow());
            Assertions.assertEquals(expected.getCellCol(), actual.getCellCol());            
        }
    	
    }
	
	//calculateSearchDuration()
	@Test
    public void test9CalculateSearchDuration() {
		Main.calculateSearchDuration();		
	}
	
	@Test
    public void test10DisplayHome() {
	    // Create a mock input reader
        InputReader mockInputReader = mock(InputReader.class);

        // Set up mock behavior
        String mockSearchTerm = "mockTerm";
        when(mockInputReader.nextLine()).thenReturn(mockSearchTerm);

        // Call the method under test
        String result = Main.displayHome(mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual search terms
        assertEquals(mockSearchTerm, result);
    }
	
	@Test
    public void test11AskCreatePdfYZeroSearchResults() {
		String mockSearchTerm = "mockTerm";
		InputReader mockInputReader = mock(InputReader.class);
		
		// Mock the behavior of the InputReader
        when(mockInputReader.nextLine()).thenReturn("Y");
		
		Main.askCreatePdf(mockSearchTerm,mockInputReader);
		
		// Verify that the expected methods are called
        Mockito.verify(mockInputReader, times(1)).nextLine();
	}
	
	@Test
	public void test12DisplayCreatePDFOptY() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockCreatePDFind = "Y";
		String mocksearchTerm = "chicken";
		String mockpdfName ="Mock_Search_Results_of_";
		String mockoutputDirectory =expectedOutputDirectory;
//		String mockoutputDirectory ="C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\OUTPUT\\";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		mocksearchResults.add(new SearchInfo(1, "Random1.xlsx", "Random1-Sheet1",16,"G"));
		mocksearchResults.add(new SearchInfo(2, "Random1.xlsx", "Random1-Sheet1",17,"G"));
		mocksearchResults.add(new SearchInfo(3, "Random1.xlsx", "Random1-Sheet1",18,"G"));
		mocksearchResults.add(new SearchInfo(4, "Random1.xlsx", "Random1-Sheet1",19,"G"));
		mocksearchResults.add(new SearchInfo(5, "Random1.xlsx", "Random1-Sheet1",22,"D"));
		mocksearchResults.add(new SearchInfo(6, "Random1.xlsx", "Random1-Sheet1",23,"B"));
		mocksearchResults.add(new SearchInfo(7, "Random1.xlsx", "Random1-Sheet1",23,"C"));
		
		when(mockInputReader.nextLine()).thenReturn(mockCreatePDFind);
		// Call the method under test
        boolean result = Main.displayCreatePDFOpt(mocksearchTerm, mockpdfName, mockoutputDirectory, mocksearchResults, mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(true, result);
    }
	
	@Test
	public void test13DisplayCreatePDFOptYZeroSearchResults() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockCreatePDFind = "Y";
		String mocksearchTerm = "chicken";
		String mockpdfName ="Mock_Search_Results_of_";
		String mockoutputDirectory =expectedOutputDirectory;
//		String mockoutputDirectory ="C:\\Users\\USER\\Desktop\\BA Training - Java\\Indexing\\OUTPUT\\";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		
		when(mockInputReader.nextLine()).thenReturn(mockCreatePDFind);
		// Call the method under test
        boolean result = Main.displayCreatePDFOpt(mocksearchTerm, mockpdfName, mockoutputDirectory, mocksearchResults, mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(true, result);
    }
	
	@Test
	public void test14DisplayCreatePDFOptN() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockCreatePDFind = "N";
		when(mockInputReader.nextLine()).thenReturn(mockCreatePDFind);
		// Call the method under test
        boolean result = Main.displayCreatePDFOpt("searchTerm", "pdfName", "outputDirectory", new ArrayList<>(), mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(true, result);
    }
	
	@Test
	public void test15DisplayCreatePDFOptx() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockCreatePDFind = "x";
		when(mockInputReader.nextLine()).thenReturn(mockCreatePDFind);
		// Call the method under test
        boolean result = Main.displayCreatePDFOpt("searchTerm", "pdfName", "outputDirectory", new ArrayList<>(), mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(false, result);
    }
   
	@Test  //commented out due to conflict in testSubmitSearchTaskForEachExcelFile() 
	public void test16AskSearchAgainN() {
//		InputReader mockInputReader = Mockito.mock(InputReader.class);
//		
//		String mockReSearchind = "n";
//		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
//		
//		when(mockInputReader.nextLine()).thenReturn(mockReSearchind);
//        
//        // Call the method under test
//        Main.askSearchAgain(mockInputReader);
////        Main.displayReSearchOpt(mocksearchResults, mockInputReader);
//
//        // Assert the behavior
////        Assertions.assertTrue(Main.executorService.isShutdown());
        
	}

		
	@Test
	public void test17DisplayReSearchOptY() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockReSearchind = "Y";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		
		when(mockInputReader.nextLine()).thenReturn(mockReSearchind);
		// Call the method under test
        boolean result = Main.displayReSearchOpt( mocksearchResults, mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(false, result);
		
	}
   
	@Test
	public void test18DisplayReSearchOptN() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockReSearchind = "n";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		
		when(mockInputReader.nextLine()).thenReturn(mockReSearchind);
		// Call the method under test
        boolean result = Main.displayReSearchOpt( mocksearchResults, mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(true, result);
		
	}
	
	@Test
	public void test19DisplayReSearchOptx() {
		InputReader mockInputReader = mock(InputReader.class);
		
		String mockReSearchind = "x";
		ArrayList<SearchInfo> mocksearchResults = new ArrayList<>();		
		
		when(mockInputReader.nextLine()).thenReturn(mockReSearchind);
		// Call the method under test
        boolean result = Main.displayReSearchOpt(mocksearchResults, mockInputReader);

        // Verify that the inputReader.nextLine() method was called
        verify(mockInputReader).nextLine();

        // Assert the expected and actual result
        assertEquals(false, result);
		
	}
	
	 
	@Test
	public void test20GetListofExcelFilesInvalidDirectoryPath() { 
		String mockPath = "C:\\NonExisting\\Folder";
		List<File> mockExcelFiles = new ArrayList<>();

		// Redirect System.out to capture the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
		
		Path mockDirectoryPath = Mockito.mock(Path.class);
		
		// Mock the behavior of the toFile() method to return a non-directory file
        Mockito.when(mockDirectoryPath.toFile()).thenReturn(new File(mockPath));
        
        // Call the method under test
        List<File> result = Main.getListOfExcelFiles(mockPath);
		
        // Restore the original System.out
        System.setOut(System.out);
        
        // Assert the behavior
        Assertions.assertEquals(mockExcelFiles, result);
        String consoleOutput = outputStream.toString().trim();
        Assertions.assertEquals("Invalid directory path: " + mockPath, consoleOutput);
    }
	
	@Test
    public void test21DisplayMssgForZeroFiles() {
		// Redirect System.out to capture the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // Create a mock list of files with size 0
        List<File> mockFiles = new ArrayList<>();
        // Call the method under test
        Main.displayMssgForZeroFiles(mockFiles);
        // Assert the console output
        String consoleOutput = outputStream.toString().trim();
        assertEquals("No excel files found in Input Folder:" + Main.inputDirectory, consoleOutput);
        
        // Reset System.out
        System.setOut(System.out);        
	}
	
} // for MainIndexingTest


