package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Processor {
    
    private String fileContent = new String();
    private String tempFileContent = new String();
    public String fileName;
    
    public Processor(String fileName) {
        this.fileName = fileName;
    }

    public void readFile(){

        // check if File exists or not
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(this.fileName));
            String line;
            while ((line = file.readLine()) != null) {
                fileContent += line + "!\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public void processFile(){
        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            // String[] characters = line.split(" ");
            // for (String chars : characters){
            this.tempFileContent += line;
            //}
        }
        this.tempFileContent += '$';
        this.fileContent = tempFileContent;
        this.tempFileContent = "";

    }

    public void writeFile() throws IOException{
       //this.formateFileContent();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LexicalAnalyzer/files/out2.txt"))) {
            writer.write(fileContent);
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            throw e;
        }
    }

    public void displayContent(){
        System.out.println(fileContent);
    }

    
    
}
