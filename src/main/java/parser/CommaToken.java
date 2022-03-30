package parser;

public class CommaToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof CommaToken;
    }

    public int hashCode() {
        return 28;
    }

    public String toString() {
        return "CommaToken";
    }
}