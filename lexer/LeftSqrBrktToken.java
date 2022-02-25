package lexer;
public class LeftSqrBrktToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof LeftSqrBrktToken;
    }

    public int hashCode() {
        return 12;
    }

    public String toString() {
        return "[";
    }

}