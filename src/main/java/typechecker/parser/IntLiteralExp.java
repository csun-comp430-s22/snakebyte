package typechecker.parser;
public class IntLiteralExp implements Expression {
    public final int value;

    public IntLiteralExp(final int value) {
        this.value = value;
    }

    public int hashCode() { return value; }

    public boolean equals(final Object other) {
        return (other instanceof IntLiteralExp &&
                value == ((IntLiteralExp)other).value);
    }

    public String toString() {
        return "IntLiteralExp(" + value + ")";
    }
}