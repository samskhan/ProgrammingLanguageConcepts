// Developer: Nathan Kurz
// Instructor: Betty Kretlow
// File: Token.java

public class Token {
    private int rowNumber;
    private String lexeme;
    private TokenType tokType;
    
    public Token (int rowNumber, String lexeme, TokenType tokType){
        if(rowNumber <= 0)
            throw new IllegalArgumentException("invalid row number argument");
        if(lexeme == null || lexeme.length() == 0)
            System.out.println("invalid lexeme argument"); // Error Message
        if(tokType == null)
            System.out.println("invalid TokenType argument"); // Error Message
        this.rowNumber = rowNumber;
        this.lexeme = lexeme;
        this.tokType = tokType;
    }
    
    public int getRowNumber() {
        return rowNumber;
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenType getTokenType() {
        return tokType;
    }
}