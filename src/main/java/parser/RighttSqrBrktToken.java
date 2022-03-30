package parser;
public class RighttSqrBrktToken implements Token{
    public boolean equals(final Object other) {
        return other instanceof RighttSqrBrktToken;
    }
    public int hashCode() {
        return 13;
    }
    public String toString() {
        return "RighttSqrBrktToken";
    }
}