package lexer;
public class ThisToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof ThisToken;
    }

    public int hashCode() {
        return 30;
    }

    public String toString() {
        return "this";
    }

}