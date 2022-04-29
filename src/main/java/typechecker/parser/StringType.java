package typechecker.parser;

public class StringType implements Type {
    public StringType() {
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(final Object other) {
        return other instanceof StringType;
    }

    public String toString() {
        return "StringType";
    }
}
    
}
