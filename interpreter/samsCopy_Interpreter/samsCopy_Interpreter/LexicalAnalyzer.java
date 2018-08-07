// Developer: Nathan Kurz
// Instructor: Betty Kretlow
// File: LexicalAnalyzer.java

import java.io.*;
import java.util.*;

public class LexicalAnalyzer {

   private List<Token> tokens;
   private LinkedList<Token> t_List;
   private ArrayList<String> k_words;
   private ArrayList<String> errors;
   
   HashMap<String, TokenType> hashMap = new HashMap<String, TokenType>();
   
   // Constructor
   public LexicalAnalyzer(String fileName) throws FileNotFoundException {
      assert(fileName != null);
      
      // create ArrayLists
      k_words = new ArrayList<String>();
      errors = new ArrayList<String>();
      tokens = new ArrayList<Token>();
      
      try {
         // Scan contents of reserveWords.txt
         Scanner k_file = new Scanner(new File("reserveWords.txt"));
         
         while (k_file.hasNext()) {
            String line = k_file.nextLine();
            int index = noWhiteSpaces(line, 0);
            
            String key = sepKeyword(line, index);
            key.toLowerCase();
            index += key.length() + 1;
            
            String Token_Type_String = line.substring(index, line.length());
            
            TokenType tokenType = TokenType.IDEN_TKN;
            hashMap.put(key, tokenType);
            
         }
         k_file.close();
      } catch(Exception e) {
         System.out.println(e);
         System.exit(0);
      }
      
      t_List = new LinkedList<Token>();
      
      // Create Scanner for reading text file
      Scanner scan = new Scanner(new File(fileName));
      
      int lineNumber = 0;
      while (scan.hasNext()) {
         String line = scan.nextLine();
         Comp_Line(lineNumber, line);
         lineNumber++;
      }
      t_List.add(new Token(lineNumber, "EOS", TokenType.EOS_TKN));
      scan.close();
      //for (int i = 0; i < tokens.size(); i++) {
         //if (tokens.get(i).getTokenType() != null) {
            //System.out.println("Next token is: " + tokens.get(i).getTokenType()
                    //+ " Next lexeme is " + tokens.get(i).getLexeme());
         //}
      //} // end for loop
   } // end constructor
   
   
   private void Comp_Line(int LineNum, String L)
  {
    assert(LineNum >= 0);//make sure the line number is not less than or equal to 0
    assert(L != null);
    
    int ind = 0;//start at 0
    ind =  noWhiteSpaces(L,ind);//references method
    
    while(ind<L.length())
    {
      String lexeme = getLex(L,ind); //reference method to get the lexume
      lexeme = lexeme.toLowerCase();
      TokenType tokt = getTokenType(lexeme,LineNum);//get the lexume and line number it's on
      
      ind += lexeme.length();
      ind =  noWhiteSpaces(L,ind);
      
      t_List.add(new Token(LineNum,lexeme,tokt));
    }
  }
  
  private String getLex(String L, int ind)
  {
    assert(L != null && ind>=0);
    int end = ind;
    while(end < L.length() && !CheckForWhiteSpaces(L.charAt(end)))
    {
      end++;
    }
    String lex = L.substring(ind,end);
    
    return lex;
  }
   
   private TokenType getTokenType(String lex, int LineNum)
  {
    assert(lex!=null && LineNum>=0);
    TokenType tok = null;
    
    if(is_alphabet(lex.charAt(0)))
    {
      if(lex.length() == 1)
      {
        tok = TokenType.IDEN_TKN;
      }
      
      else if(hashMap.containsKey(lex))
      {
        tok = hashMap.get(lex);
      }
      
      else   
      {
        errors.add("Invalid lexeme:" + lex + " on line " + LineNum); 
      }
      //either special keyword or identifier
      
    }
    
    else if(is_digit(lex.charAt(0)))
    {
      int ind = 1;//start at 1
      boolean checkDig = true;
      while(ind < lex.length())
      {
        if(!(is_digit(lex.charAt(ind))))
        {
          checkDig=false;
          break;
        }
        ind++;
      }
      
      //if it is a digit, then this needs a Literal Integer Token
      if(checkDig)
      {
        tok = TokenType.LITERAL_INTEGER_TKN; 
      }
      
      else
      {
        errors.add("Invalid lexeme:" + lex + " on line " + LineNum); 
      }
    }
    
    //check what operators are used
    if(tok == null && lex.length() == 1)
    {
      if((lex.charAt(0) == '+' || lex.charAt(0) == '-' || lex.charAt(0) == '*' || lex.charAt(0)== '/'))
      {//check arithmetic operator
        tok = TokenType.ARITH_OP_TKN;
      }
      
      else if((lex.charAt(0) == '<' || lex.charAt(0) == '>'))
      {
        //relative operator
        tok = TokenType.RELATIVE_OP_TKN;
        
      }
      
      else if(lex.charAt(0) == '=')
      {
        //assignment op
        tok = TokenType.ASSIGNMENT_OP_TKN;
      }
      
      else if(lex.charAt(0) == '(')
      {
        //left parenthesis
        tok = TokenType.LEFT_PAREN_TKN;
      }
      
      else if(lex.charAt(0)==')')
      {
        //right parenthesis
        tok = TokenType.RIGHT_PAREN_TKN;
      }}
      
      
    if(tok == null && lex.length() == 2)
    {
      if(((lex.charAt(0) == '<' && lex.charAt(1) == '=') || (lex.charAt(0) == '>' && lex.charAt(1) == '=') || (lex.charAt(0) == '=' && lex.charAt(1) == '=') || (lex.charAt(0) == '~' && lex.charAt(1) == '=')))
      {
        tok = TokenType.RELATIVE_OP_TKN;
      }
    }
    
    if(tok == null)
    {
      tok = TokenType.INVALID_TKN; 
    }
    return tok;
    
  } // end getTokenType
    
    private boolean allDigits(String s) {
        assert (s != null);
        int i = 0;
        while (i < s.length() && Character.isDigit((s.charAt(i))))
            i++;
        return i == s.length();
    }
    
    private String getLexeme(String line, int lineNumber, int index) {
        assert(line != null && lineNumber >= 1 && index >= 0);
        int i = index;
        while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
            i++;
        return line.substring(index, i);
    }
    
    private int skipWhiteSpace(String line, int index) {
        assert (line != null && index >= 0);
        while (index < line.length() && Character.isWhitespace(line.charAt(index)))
            index++;
        return index;
    }
    
    private String sepKeyword(String L, int ind) {
      assert(L != null && ind >= 0);
      int end = ind;
      while(end < L.length() && !!CheckForWhiteSpaces(L.charAt(end))) {
         end++;
      }
      String k_word = L.substring(ind, end);
      return k_word;
    }
    
    private int noWhiteSpaces(String L,int ind)
  {
    assert(L != null && ind >= 0);
    while(ind<L.length() && CheckForWhiteSpaces(L.charAt(ind)))
    {
      ind++;
    }
    return ind;
  }
    
    private boolean CheckForWhiteSpaces(char c) {
      if(c == ' ' || c =='\t') {
         return true; 
      }
      return false;
    }
  
    private boolean is_alphabet(char c) {
      if(c >= 'a' && c <= 'z') {
         return true; 
      }
      return false;
    }
    
    private boolean is_digit(char c) {
      if(c >= '0' && c <= '9') {
         return true; 
      }
      return false;
    }
    
    public LinkedList<Token> getTokenList()
  {
    if(!errors.isEmpty())
    {
      this.printErr();
      throw new IllegalArgumentException("Invalid Lexeme");
    }
    return this.t_List; 
  }
   
  public void printErr()
  {
    Iterator iterate = errors.iterator();
    while (iterate.hasNext())
    {
      String err = (String)iterate.next();
      System.err.println(err);
    }
   
  }
   
   
} // end class