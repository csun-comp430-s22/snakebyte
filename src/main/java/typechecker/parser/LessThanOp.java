package typechecker.parser;

public class LessThanOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof LessThanOp;
    }

    public int hashCode() {
        return 104;
    }

    public String toString() {
        return "LessThanOp";
    }
}