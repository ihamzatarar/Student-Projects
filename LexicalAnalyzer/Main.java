package LexicalAnalyzer;

import java.io.*;
import java.util.Scanner;

public class Main {
  
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.println("\n-------- Lexical Analyzer ----------");
        System.out.println("Enter File Name for Preprocessing phase or enter 'd' for defualt file");
        String filename1 = scan.nextLine();
        switch(filename1){
            case "d":
                filename1 = "LexicalAnalyzer/files/in1.java";
        }
        
        // Preprocessor
        Preprocessor per = new Preprocessor(filename1);
        per.readFile();
        per.EliminateBlankLines();
        per.EliminateComments();
        per.EliminateSpaces();
        per.EliminateImports();
        per.writeFile("LexicalAnalyzer/files/out1.txt");
        per.displayFile();


        // Processor
        System.out.println("\nEnter File Name for Processing phase or enter 'd' for defualt file");
        String filename2 = scan.nextLine();
        switch(filename2){
            case "d":
                filename2 = "LexicalAnalyzer/files/out1.txt";
        }
        
        Processor pro = new Processor(filename2);
        pro.readFile();
        pro.processFile();
        pro.writeFile("LexicalAnalyzer/files/out2.txt");
        pro.displayFile();


        // Lexical Analysis
        System.out.println("\nEnter File Name for Lexical Analysis or enter 'd' for defualt file ");
        String filename3 = scan.nextLine();
        switch(filename3){
            case "d":
                filename3 = "LexicalAnalyzer/files/out2.txt";
        }
        
        LexicalAnalyzer lex = new LexicalAnalyzer(filename3);
        lex.readFile();
        lex.analysisFile();

        scan.close();

    }
}
