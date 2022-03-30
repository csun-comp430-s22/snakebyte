package parser;
public class StringToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof StringToken;
    }
    public int hashCode() {
        return 10;
    }
    public String toString() {
        return "StringToken";
    }
}