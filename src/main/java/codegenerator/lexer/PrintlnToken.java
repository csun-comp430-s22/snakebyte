package codegenerator.lexer;
import lexer.*;
public class PrintlnToken implements Token {
    public int hashCode() { return 101; }
    public boolean equals(final Object other) {
        return other instanceof PrintlnToken;
    }
    public String toString() {
        return "PrintlnToken";
    }
    
}
