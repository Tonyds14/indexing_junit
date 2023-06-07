package com.svi.indexing;

//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetExpectedConfig {
    private Properties properties;  
    String projectPath = "";
    String inputDirectory ="";
    String outputDirectory ="";
    
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
