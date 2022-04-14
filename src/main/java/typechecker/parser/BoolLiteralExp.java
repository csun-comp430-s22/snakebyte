package typechecker.parser;
public class BoolLiteralExp implements Expression {
    public final boolean value;

    public BoolLiteralExp(final boolean value) {
        this.value = value;
    }

    public int hashCode() {
        return (value) ? 1 : 0;
    }

    public boolean equals(final Object other) {
        return (other instanceof BoolLiteralExp &&
                value == ((BoolLiteralExp)other).value);
    }

    public String toString() {
        return "BoolLiteralExp(" + value + ")";
    }
}