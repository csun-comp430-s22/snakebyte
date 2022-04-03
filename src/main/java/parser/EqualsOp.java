package parser;

public class EqualsOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof EqualsOp;
    }

    public int hashCode() {
        return 108;
    }

    public String toString() {
        return "EqualsOp";
    }
}
