package typechecker.parser;

public class EqualsEqualsOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof EqualsEqualsOp;
    }

    public int hashCode() {
        return 109;
    }

    public String toString() {
        return "EqualsEqualsOp";
    }
}
