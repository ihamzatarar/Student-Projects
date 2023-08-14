package LexicalAnalyzer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer extends FileManagment {

    public ArrayList<ArrayList<String>> lexemesList ;

    public LexicalAnalyzer(String fileName) {
        super(fileName);
        this.lexemesList = genLexemsList();
    }

    public void analysisFile(){
        System.out.println("\n+-------- Lexical Analyzer ----------+");
        System.out.println("+--------------------+---------------+");
        System.out.printf("| %-18s | %-13s |%n", "Lexeme", "Token");
        System.out.println("+--------------------+---------------+");
        String[] lines = this.fileContent.split("!\n");

        //Get all lines in file
        for (String line : lines) {
            String[] words = line.split(" ");
            //Divde the line into words 
            for (String word : words ){
                ArrayList<String> lexems = getLexems(word);
                for(String lex : lexems){
                    // Check for sentinal node
                    if (!lex.contains("$") ){
                       System.out.print(String.format("| %-18s | %-13s |%n", lex, getCategoryName(lex))) ;
                    }
                    
                }

            }
        }
        this.fileContent = tempFileContent;
        this.tempFileContent = "";
        System.out.println("+--------------------+---------------+");
        

    }

    // Get name of token form 2d ArrayList
    public String getCategoryName(String word){
        for (int i = 0; i < lexemesList.size(); i++) {

            List<String> categoryList = this.lexemesList.get(i);
            for (String lexeme : categoryList) {
                if(word == lexeme){
                    switch (i) {
                            case 0:
                                 return "Keywords";
                            case 1:
                                return "Operators";
                            case 2:
                                return "Punctuators";   
                            case 3:
                                return "Literals";
                            case 4:
                                return "Comments";
                            case 5:
                                return "Annotations";
                            case 6:
                                return "Import statements";
                            default:
                                return "Unknown";
                    }
                }
            }
            
        }
        char firstchar = word.charAt(0);
        if (Character.isDigit(firstchar)){
            return "Literals";
        }
        else{
            return "Identifiers";
        }
    }


    // Creates  2d Array list of some possible lexems provide in documentations categ by tokens using index
    public static ArrayList<ArrayList<String>> genLexemsList() {
        ArrayList<ArrayList<String>> lexemesList = new ArrayList<>();

        // Keywords
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "if", "else", "double","while", "for", "do", "int", "float",  "char", 
            "void", "boolean", "true", "false", "return", "class", "public", 
            "private", "protected", "static", "final", "try", "catch", "throw", 
            "interface"
        )));

        // Operators
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "++", "--", "*", "/", "%", "==", "+=", "-=", "*=", "/=", "!+", 
            "<", ">", "<=", ">=", "+", "-", "&&", "`","="
        )));

        // Punctuators
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "{", "}", "[", "]", "(", ")", ",", ";", ":", "."
        )));

        // Literals
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "true", "false", "null"
        )));
      
        // Comments
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "//", "/*","*/"
        )));

        // Annotations
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "@Override", "@Deprecated", "@SuppressWarnings"
        )));

        // Import statements
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "import"
        )));
        // Identifiers
        lexemesList.add(new ArrayList<>(Arrays.asList(
            "x", "y", "z", "sum", "count", "temp", "myVar", "myFunction"
        )));



        return lexemesList;
       
    }

    //Prints list of lexems
    public void displayLexemList(){

        // Display the 2D list
        for (int i = 0; i < this.lexemesList.size(); i++) {
            List<String> categoryList = this.lexemesList.get(i);
            for (String lexeme : categoryList) {
                System.out.print(lexeme + ", ");
            }
            System.out.println();
        }
    }
    
     


    // Returns an Array list of all lexems in a sub string
    public ArrayList<String> getLexems(String input) {
        ArrayList<String> result = new ArrayList<>();
        int currentIndex = 0;

        while (currentIndex < input.length()) {
            String currentSubstring = input.substring(currentIndex);

            // Match literals (int and double)
            Matcher numericMatcher = Pattern.compile("\\d+(\\.\\d+)?").matcher(currentSubstring);
            if (numericMatcher.lookingAt()) {
                result.add(numericMatcher.group());
                currentIndex += numericMatcher.end();
                continue;
            }

            // Check for matching lexemes
            String match = findMatchingLexeme(currentSubstring);
            if (match != null) {
                result.add(match);
                currentIndex += match.length();
            } 
            else {
                // If no lexeme is found, consider the current character as an identifier or variable name
                int endIndex = currentIndex + 1;
                while (endIndex < input.length() && isIdentifierChar(input.charAt(endIndex))) 
                {
                    endIndex++;
                }
                result.add(input.substring(currentIndex, endIndex));
                currentIndex = endIndex;
            }
        }

        return result;
    }

    // Check a word form lexem list
    private String findMatchingLexeme(String substring) {
        for (List<String> categoryList : lexemesList) {
            for (String lexeme : categoryList) {
                if (substring.startsWith(lexeme)) {
                    return lexeme;
                }
            }
        }
        return null; // No lexeme found
    }

    private boolean isIdentifierChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}


