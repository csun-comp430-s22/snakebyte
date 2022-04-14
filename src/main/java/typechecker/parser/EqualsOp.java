package typechecker.parser;
public class EqualsOp implements Operator {
    public int hashCode() { return 1; }
    public boolean equals(final Object other) {
        return other instanceof EqualsOp;
    }
    public String toString() {
        return "EqualsOp";
    }
}