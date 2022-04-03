package parser;


public class GreaterThanOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof GreaterThanOp;
    }

    public int hashCode() {
        return 105;
    }

    public String toString() {
        return "GreaterThanOp";
    }
}