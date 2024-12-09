package com.svi.process;

//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.svi.indexing.Main;

public class GetExpectedConfig {
    private Properties properties;  
    String projectPath = "";
    public String inputDirectory ="";
    public String outputDirectory ="";
    
    public GetExpectedConfig() {
        loadProperties();
    }
    
    private void loadProperties() {
        properties = new Properties();
            
//        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config/expectedresults.properties")) {
//            properties.load(inputStream);  
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        try 
    	(InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config/expectedresults.properties")) {
          properties.load(inputStream);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        // Get the project path
        projectPath = System.getProperty("user.dir");

        // Replace the placeholder with the project path in the properties
        inputDirectory = properties.getProperty("Input_directory");
        inputDirectory = inputDirectory.replace("${projectPath}", projectPath);

        outputDirectory = properties.getProperty("Output_directory");
        outputDirectory = outputDirectory.replace("${projectPath}", projectPath);

        // Use the updated directory paths for further processing
        System.out.println("Input Directory: " + inputDirectory);
        System.out.println("Output Directory: " + outputDirectory);
    }
        
    
    public String getInputDirectory() {
        return properties.getProperty("Input_directory");
    }
    
    public String getOutputDirectory() {
        return properties.getProperty("Output_directory");
    }
    
    public String getPdfName() {
        return properties.getProperty("PDF_name");
    }
    
    public String getVersion() {
        return properties.getProperty("version");
    }
    
}
