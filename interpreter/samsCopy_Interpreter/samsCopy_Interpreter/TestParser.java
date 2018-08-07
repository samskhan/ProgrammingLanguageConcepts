// Developer: Nathan Kurz
// Instructor: Betty Kretlow
// File: TestParser.java
//
// Test file for the parser

import java.util.*;
import java.io.*;

public class TestParser
{
  public static void main(String[] args) throws FileNotFoundException
  {
    //get the contents of the file name
    String filename = "parserTest.txt";
    
    LexicalAnalyzer lexA = new LexicalAnalyzer(filename);
    LinkedList<Token> tList = lexA.getTokenList();
    
    // Iterator for file
    Iterator it = tList.iterator();
    
    while(it.hasNext())
    {
      Token t = (Token)it.next();
    }
    
    Parser p = new Parser(filename);
  }
}