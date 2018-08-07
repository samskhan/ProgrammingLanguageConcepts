// Developer: Nathan Kurz
// Instructor: Betty Kretlow
// File: Block.java

public class Block {
   private Block b;
   private Statement s;
   
   // Constructor
   public Block(Block b, Statement s) {
      this.b = b;
      this.s = s;
   }
   
   // Constructor
   public Block(Statement s) {
      this.s = s;
   }
}