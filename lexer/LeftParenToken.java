package lexer;

public class LeftParenToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof LeftParenToken;
    }

    public int hashCode() {
        return 16;
    }

    public String toString() {
        return "(";
    }

}
