package com.svi.indexing;

//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetExpectedResults {
    private Properties properties;        
    
    public GetExpectedResults() {
        loadProperties();
    }
    
    private void loadProperties() {
        properties = new Properties();
//        try (InputStream input = new FileInputStream("config.properties")) {
//            properties.load(input);
            
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config/expectedresults.properties")) {
            properties.load(inputStream);  
        } catch (IOException e) {
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
