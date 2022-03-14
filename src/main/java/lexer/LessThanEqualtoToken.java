package lexer;

public class LessThanEqualtoToken implements Token {

    public boolean equals(final Object other) {
        return other instanceof LessThanEqualtoToken;
    }

    public int hashCode() {
        return 35;
    }

    public String toString() {
        return "<=";
    }

}
