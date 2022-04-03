package parser;


public class LessThanEqualOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof LessThanEqualOp;
    }

    public int hashCode() {
        return 106;
    }

    public String toString() {
        return "LessThanEqualOp";
    }
}