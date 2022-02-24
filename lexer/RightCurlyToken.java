package lexer;

public class RightCurlyToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof RightCurlyToken;
    }

    public int hashCode() {
        return 15;
    }

    public String toString() {
        return "}";
    }

}
