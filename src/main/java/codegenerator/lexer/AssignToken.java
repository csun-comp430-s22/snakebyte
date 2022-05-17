package codegenerator.lexer;

import lexer.Token;

public class AssignToken implements Token{
    public int hashCode() { return 104; }
    public boolean equals(final Object other) {
        return other instanceof AssignToken;
    }
    public String toString() {
        return "AssignToken";
    }
}
