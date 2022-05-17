package codegenerator.lexer;

public class VoidToken {
    public int hashCode() { return 106; }
    public boolean equals(final Object other) {
        return other instanceof VoidToken;
    }
    public String toString() {
        return "VoidToken";
    }
}
