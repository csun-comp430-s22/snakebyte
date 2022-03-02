package lexer;

public class RightSqrBrktToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof RightSqrBrktToken;
    }

    public int hashCode() {
        return 13;
    }

    public String toString() {
        return "]";
    }

}
