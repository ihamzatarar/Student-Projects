package LexicalAnalyzer;

public class Processor extends FileManagment{
    
    public Processor(String fileName) {
        super(fileName);
    }
    // write the contents of the file in a buffer as a linear array of characters
    public void processFile(){
        String[] lines = this.fileContent.split("!\n");
        for (String line : lines) {
            this.tempFileContent += line;
        }
        this.tempFileContent += '$';
        this.fileContent = tempFileContent;
        this.tempFileContent = "";

    }
        
}


