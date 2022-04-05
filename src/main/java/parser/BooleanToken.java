package parser;

public class BooleanToken implements Token {

    public final Boolean value;

    public BooleanToken(final Boolean value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return other instanceof BooleanToken;
    }

    public int hashCode() {
        return 11;
    }

    public String toString() {
        return "BooleanToken";
    }
}