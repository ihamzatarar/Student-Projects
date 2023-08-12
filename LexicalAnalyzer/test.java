package LexicalAnalyzer;

import java.io.*;

public class test {
  
    public static void main(String[] args) throws IOException {
        
        Preprocessor p = new Preprocessor("LexicalAnalyzer/files/in1.java");
        p.EliminateBlankLines();
        p.EliminateComments();
        p.EliminateSpaces();
        p.EliminateImports();
        p.saveFileContent();
        p.displayContent();
    }
}
