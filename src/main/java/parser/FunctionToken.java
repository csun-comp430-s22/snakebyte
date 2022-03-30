package parser;

public class FunctionToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof FunctionToken;
    }

    public int hashCode() {
        return 27;
    }

    public String toString() {
        return "FunctionToken";
    }
}