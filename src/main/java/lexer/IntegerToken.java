package lexer;

public class IntegerToken implements Token {

    public boolean equals(final Object other) {
        return other instanceof IntegerToken;
    }

    public int hashCode() {
        return 9;
    }

    public String toString() {
        return "Int";
    }

}
