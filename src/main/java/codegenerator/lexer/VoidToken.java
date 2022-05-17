package codegenerator.lexer;

import lexer.Token;

public class VoidToken implements Token {
    public int hashCode() { return 106; }
    public boolean equals(final Object other) {
        return other instanceof VoidToken;
    }
    public String toString() {
        return "VoidToken";
    }
}
