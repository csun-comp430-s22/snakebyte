package lexer;

public class SuperToken implements Token {
    public int hashCode() {
        return 32;
    }

    public boolean equals(final Object other) {
        return other instanceof SuperToken;
    }

    public String toString() {

        return "Super";
    }

}
