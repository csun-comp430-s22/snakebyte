package codegenerator.lexer;
import lexer.*;
public class DotToken implements Token{
    public int hashCode() { return 100; }
    public boolean equals(final Object other) {
        return other instanceof DotToken;
    }
    public String toString() {
        return "DotToken";
    }
}
