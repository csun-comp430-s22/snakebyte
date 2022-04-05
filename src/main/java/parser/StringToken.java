package parser;

public class StringToken implements Token {

    public final String value;

    public StringToken(final String value) {
        this.value = value;
    }

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