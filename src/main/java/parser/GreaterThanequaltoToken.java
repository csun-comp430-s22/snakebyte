package parser;

public class GreaterThanequaltoToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof GreaterThanequaltoToken;
    }

    public int hashCode() {
        return 36;
    }
    
    public String toString() {
        return "GreaterThanequaltoToken";
    }
}