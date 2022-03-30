package parser;

public class ClassToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof ClassToken;
    }

    public int hashCode() {
        return 26;
    }

    public String toString() {
        return "ClassToken";
    }
}