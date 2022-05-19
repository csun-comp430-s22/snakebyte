package parser;

import lexer.*;
public class IdentifierToken implements Token{
    public final String identifier;
    
    public IdentifierToken(final String identifier) {
        this.identifier = identifier;
    }

    public int hashCode() {
        return identifier.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof IdentifierToken &&
                identifier.equals(((IdentifierToken)other).identifier));
    }
    
    public String toString() {
        return "IdentifierToken(" + identifier + ")";
    }
}
