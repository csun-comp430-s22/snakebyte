package typechecker.parser;

public class BooleanValue implements Token {

    public final Boolean value;

    public BooleanValue(final Boolean value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (Boolean) other == value;
    }

    public int hashCode() {
        return 11;
    }

    public String toString() {
        return "BooleanValue: " + value;
    }
}
