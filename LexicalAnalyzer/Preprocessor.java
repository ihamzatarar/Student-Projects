package LexicalAnalyzer;

import java.io.IOException;

public class Preprocessor extends FileManagment {

    public Preprocessor(String fileName) {
        super(fileName);
    }

    //Eliminate any blank lines in the program.
    public void EliminateBlankLines() throws IOException{

        String[] lines = this.fileContent.split("!\n");
        for (String line : lines) {
            if (!line.trim().isEmpty())
                    this.tempFileContent += line + "!\n";
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        }
                
    //Identify and eliminate all double slash and slash star comments.
    public void EliminateComments() throws IOException{

        String[] lines = this.fileContent.split("!\n");
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
                    this.tempFileContent += modifiedLine + "!\n";
                }
            }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
    }


    //Eliminate unnecessary tabs and spaces in the program
    public void EliminateSpaces() throws IOException{

    String[] lines = this.fileContent.split("!\n");
    for (String line : lines) {
        line = line.replaceAll("\\s+", " ").replace(" ;",";").trim();
        this.tempFileContent += line+ "!\n";
        }
    this.fileContent = tempFileContent;
    this.tempFileContent = "";
    }

    //Eliminate import statements and annotations.
    public void EliminateImports() throws IOException{

    String[] lines = this.fileContent.split("!\n");
    for (String line : lines) {
        if(!line.contains("import") && !line.contains("@")){
            this.tempFileContent += line.trim() + "!\n";
        }
        }
    this.fileContent = tempFileContent;
    this.tempFileContent = "";
    }

    @Override
    public void writeFile(String filename) throws IOException{
        super.formateFileContent();
        super.writeFile(filename);
    }
        
}
