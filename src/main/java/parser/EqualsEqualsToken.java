package parser;

public class EqualsEqualsToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof EqualsEqualsToken;
    }

    public int hashCode() {
        return 114;
    }

    public String toString() {
        return "EqualsEqualsToken";
    }
}
