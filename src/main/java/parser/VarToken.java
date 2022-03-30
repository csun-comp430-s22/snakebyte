package parser;

public class VarToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof VarToken;
    }

    public int hashCode() {
        return 31;
    }

    public String toString() {
        return "VarToken";
    }
}