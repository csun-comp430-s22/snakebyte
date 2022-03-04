package lexer;

public class SuperToken implements Token {
    public int hashCode() {
        return 32;
    }

    public boolean equals(final Object other) {
        return other instanceof StringToken;
    }

    public String toString() {

        return "Super";
    }

}
