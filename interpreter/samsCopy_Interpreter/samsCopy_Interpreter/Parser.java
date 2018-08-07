// Developer: Nathan Kurz
// Instructor: Betty Kretlow
// File: Parser.java
import java.util.*;
import java.io.*;

public class Parser
{
  //call in the LexAnal file so we can declare LexicalAnalyzer as a variable type
  LexicalAnalyzer lex; 
  
  //declare variables
  LinkedList<Token> t;
  Token tok;
  
  public Parser(String filename) throws FileNotFoundException
  {
    assert(!filename.equals("")); //make sure the file name actually is there
    lex = new LexicalAnalyzer(filename);
    t = lex.getTokenList();
    tok = t.getFirst();
    
    //iterate through the file until it reaches the end
    while(tok.getTokenType() != TokenType.EOS_TKN)
    {
      this.Parse();
    }
    System.out.println("File Parsed Successfully.");
  }
  
  
  private void Parse()
  {
    if(this.tok.getTokenType() != TokenType.FUNCTION_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected FUNCTION on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();
    
    if(this.tok.getTokenType() != TokenType.IDEN_TKN)
    {
      throw new IllegalArgumentException("Expected ID on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();
    
    if(this.tok.getTokenType() != TokenType.LEFT_PAREN_TKN)
    {
      throw new IllegalArgumentException("Expected ( on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();
    
    if(this.tok.getTokenType() != TokenType.RIGHT_PAREN_TKN)
    {
      throw new IllegalArgumentException("Expected ) on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();
    
    this.block();
    
    this.nextToken();
    
    if(this.tok.getTokenType() != TokenType.END_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected END on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();
  }
  // Checks for statement in file
  private void block()
  {
    if(!this.isStatement(tok.getTokenType()))
    {
      throw new IllegalArgumentException("Expected STATEMENT on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.statement();
    
    TokenType nextToken = lookAHead();//call this method
    if(this.isStatement(nextToken))
    {
      this.nextToken();
      this.block();//call this method
    }
    
  }
  
  // Checks if a line is a statement
  private boolean isStatement(TokenType tokType)
  {
    if(tokType == TokenType.IF_STATEMENT_TKN || tokType == TokenType.IDEN_TKN || tokType == TokenType.WHILE_STATEMENT_TKN || tokType == TokenType.PRINT_STATEMENT_TKN || tokType == TokenType.REPEAT_STATEMENT_TKN)
      return true;
    return false;
    
  }
  
  // Checks the different statement types
  private void statement()
  {
    if(this.tok.getTokenType() == TokenType.IF_STATEMENT_TKN)
    {
      this.nextToken();
      this.ifStatement();
    }
    else if(this.tok.getTokenType() == TokenType.IDEN_TKN)
    {
      this.nextToken();
      this.assignmentStatement(); 
    }
    else if(this.tok.getTokenType() == TokenType.WHILE_STATEMENT_TKN)
    {
      this.nextToken();
      this.whileStatement();
    }
    else if(this.tok.getTokenType() == TokenType.PRINT_STATEMENT_TKN)
    {
      this.nextToken();
      this.printStatement();
    }
    else if(this.tok.getTokenType() == TokenType.REPEAT_STATEMENT_TKN)
    {
      this.nextToken();
      this.repeatStatement(); 
    }
    
    // Checks for end of file
    else if(!this.checkEndOfFile())
    {
      throw new IllegalArgumentException("Expected STATEMENT on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    
  }
  
  //method for checking how if statements are constructed in the file, if any
  //if it catches an error, the method will output an error, what it expected and what it got
  private void ifStatement()
  {
    
    this.boolean_expression();
    this.nextToken();
    
    if(this.tok.getTokenType() != TokenType.THEN_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected THEN on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    
    this.nextToken(); 
    this.block();     
    this.nextToken(); 
    if(this.tok.getTokenType() != TokenType.ELSE_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected ELSE on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken(); 
    this.block();     
    this.nextToken(); 
    if(this.tok.getTokenType() != TokenType.END_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected END on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    
  }
  
  // Checks for while statements
  private void whileStatement()
  {
    this.boolean_expression(); 
    this.nextToken();
    if(this.tok.getTokenType() != TokenType.DO_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected DO on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken(); 
    this.block();     
    this.nextToken(); 
    if(this.tok.getTokenType() != TokenType.END_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected END on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
  }
  
  // Checks for assignment operator
  private void assignmentStatement()
  {
    if(this.tok.getTokenType() != TokenType.ASSIGNMENT_OP_TKN)
    {
      throw new IllegalArgumentException("Expected = on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();      //call this method
    this.arithmetic_exp(); //call this method
    
  }
  
  // Checks for the print statement
  private void printStatement()
  {
    if(this.tok.getTokenType() != TokenType.LEFT_PAREN_TKN)
    {
      throw new IllegalArgumentException("Expected ( on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();      
    this.arithmetic_exp();
    this.nextToken();      
    if(this.tok.getTokenType() != TokenType.RIGHT_PAREN_TKN)
    {
      throw new IllegalArgumentException("Expected ) on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    
  }
  
  // Checks for repeat statement
  private void repeatStatement()
  {
    this.block();    
    this.nextToken();
    if(this.tok.getTokenType() != TokenType.UNTIL_STATEMENT_TKN)
    {
      throw new IllegalArgumentException("Expected UNTIL on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();          
    this.boolean_expression(); 
  }
  
  // Checks for boolean expressions and relative operations
  private void boolean_expression()
  {
    if(this.tok.getTokenType() != TokenType.RELATIVE_OP_TKN)
    {
      throw new IllegalArgumentException("Expected RELATIVE OPERATOR on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    this.nextToken();      
    this.arithmetic_exp(); 
    
    this.nextToken();      
    this.arithmetic_exp(); 
  }
  
  // Handles arithmetic expressions
  private void arithmetic_exp()
  {
    if(this.tok.getTokenType() != TokenType.IDEN_TKN && this.tok.getTokenType() != TokenType.LITERAL_INTEGER_TKN && this.tok.getTokenType() != TokenType.ARITH_OP_TKN)
    {
      throw new IllegalArgumentException("Expected ID or INTEGAR or ARITHMETIC OPERATOR on line:" + this.tok.getRowNumber() + " found " + this.tok.getLexeme()); 
    }
    if(this.tok.getTokenType() != TokenType.IDEN_TKN && this.tok.getTokenType() != TokenType.LITERAL_INTEGER_TKN){
      this.nextToken();     
      this.arithmetic_exp();
      
      this.nextToken();     
      this.arithmetic_exp();
    }
    
    
  }
  
  private void nextToken()
  {
    if(!t.isEmpty()){
      this.t.pop();
      this.tok = t.getFirst();
    }
    
  }
  
  //checks whether we have reached the end of the file
  private Boolean checkEndOfFile()
  {
    if(this.tok.getTokenType() == TokenType.EOS_TKN)
    {
      return true; 
    }
    return false;
  }
  
  //keep on iterating through the file until we get to the end of the file
  //in which case, we return an End of File token to indicate the parser has reached the end of the file.
  private TokenType lookAHead()
  {
    if(t.size()>=2)
    {
     return t.get(1).getTokenType(); 
    }
    return TokenType.EOS_TKN;
  }
  
}