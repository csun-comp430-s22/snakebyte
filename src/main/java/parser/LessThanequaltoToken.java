package parser;

public class LessThanequaltoToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof LessThanequaltoToken;
    }

    public int hashCode() {
        return 35;
    }
    
    public String toString() {
        return "LessThanequaltoToken";
    }
}