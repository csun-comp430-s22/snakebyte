package codegenerator.lexer;
import parser.Token;
public class SemicolonToken implements Token{
    public int hashCode() { return 102; }
    public boolean equals(final Object other) {
        return other instanceof SemicolonToken;
    }
    public String toString() {
        return "SemicolonToken";
    }
}
