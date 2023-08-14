package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagment {
    
    // Variables to save file content
    protected String fileContent = new String();
    protected String tempFileContent = new String();
    public String fileName;

    public FileManagment(String fileName) {
        this.fileName = fileName;
    }

    //Open file and save its content to class variable
    public void readFile(){

        // check if File exists or not
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(this.fileName));
            String line;
            while ((line = file.readLine()) != null) {

                //Using "!\n" as new line delimiter to deal with "\n" inside quotes given as string
                this.fileContent += line + "!\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Write modified file content to output file 
    public void writeFile(String filename) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(this.fileContent);
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            throw e;
        }
    }

    // Display the content of opened file
    public void displayFile(){
        System.out.println(this.fileContent);
    }

    // Changes "!\n" back to "\n" 
    protected void formateFileContent() throws IOException{
        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            this.tempFileContent += line + "\n";
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }
}
