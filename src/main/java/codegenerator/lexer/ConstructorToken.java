package codegenerator.lexer;
import lexer.*;
public class ConstructorToken implements Token{
    public int hashCode() { return 105; }
    public boolean equals(final Object other) {
        return other instanceof ConstructorToken;
    }
    public String toString() {
        return "ConstructorToken";
    }
}
