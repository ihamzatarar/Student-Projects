package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Preprocessor {

    // Inputs file opened as file to use to different methods
    // Modified File
    private String fileContent = new String();
    private String tempFileContent = new String();


    //Opening file in constructor and saving it as class variable
    public Preprocessor(String fileName) {

        // check if File exists or not
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = file.readLine()) != null) {
                fileContent += line + "!\n";
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
       
    }


    // 1. Eliminate any blank lines in the program.
    public void EliminateBlankLines() throws IOException{

        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            if (!line.trim().isEmpty())
                    this.tempFileContent += line + "!\n";
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }
                


    //2. Identify and eliminate all double slash and slash star comments.
    public void EliminateComments() throws IOException{

        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            // Remove comments from each line
                int slashIndex = line.indexOf("//");
                int starIndex = line.indexOf("/*");
                String modifiedLine = line;
                if (starIndex != -1){
                    modifiedLine = line.substring(0, starIndex);
                }
                if (slashIndex != -1){
                    modifiedLine = line.substring(0, slashIndex);
                }
                if (modifiedLine.trim() != ""){
                    this.tempFileContent += line + "!\n";
                }
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }


        //3. Eliminate unnecessary tabs and spaces in the program
        public void EliminateSpaces() throws IOException{

        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            line = line.replaceAll("\\s+", " ").replace(" ;",";").trim();
            this.tempFileContent += line+ "!\n";
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }

        //4. Eliminate import statements and annotations.
        public void EliminateImports() throws IOException{

        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            if(!line.contains("import")){
                this.tempFileContent += line.trim() + "!\n";
            }
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }

        //5. Write the updated program to a separate file named "out1.txt".
        public void saveFileContent() throws IOException {

        this.formateFileContent();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LexicalAnalyzer/files/out1.txt"))) {
            writer.write(fileContent);
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
            throw e;
        }
    }
        

    
        // 6. Display the contents of the output file on the console.
        public void displayContent(){
            System.out.println(fileContent);
        }

        //7, Change "!\n" to "\n"

        private void formateFileContent() throws IOException{

        String[] lines = fileContent.split("!\n");
        for (String line : lines) {
            this.tempFileContent += line + "\n";
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }
}
