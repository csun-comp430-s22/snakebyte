package parser;


public class GreaterThanEqualOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof GreaterThanEqualOp;
    }

    public int hashCode() {
        return 107;
    }

    public String toString() {
        return "GreaterThanEqualOp";
    }
}