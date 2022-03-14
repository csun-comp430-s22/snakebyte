package lexer;

public class GreaterThanEqualtoToken implements Token {
    public int hashCode() {
        return 36;
    }

    public boolean equals(final Object other) {
        return other instanceof GreaterThanEqualtoToken;
    }

    public String toString() {

        return ">=";
    }

}
