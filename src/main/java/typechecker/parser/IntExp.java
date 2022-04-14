package typechecker.parser;

public class IntExp implements Expression {
    public final int value;

    public IntExp(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (other instanceof IntExp &&
                value == ((IntExp) other).value);
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntExp(" + value + ")";
    }
}