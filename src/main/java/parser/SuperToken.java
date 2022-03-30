package parser;

public class SuperToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof SuperToken;
    }

    public int hashCode() {
        return 32;
    }
    
    public String toString() {
        return "SuperToken";
    }
}