package codegenerator.lexer;
import parser.Token;
public class PrintlnToken implements Token {
    public int hashCode() { return 101; }
    public boolean equals(final Object other) {
        return other instanceof PrintlnToken;
    }
    public String toString() {
        return "PrintlnToken";
    }
    
}
