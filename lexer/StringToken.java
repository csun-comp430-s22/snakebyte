package lexer;

public class StringToken implements Token {

    public int hashCode() {
        return 10;
    }

    public boolean equals(final Object other) {
        return other instanceof StringToken;
    }

    public String toString() {

        return "Str";
    }

}
