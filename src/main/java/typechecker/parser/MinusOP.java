package typechecker.parser;

public class MinusOP implements Operator {
    public boolean equals(final Object other) {
        return other instanceof MinusOP;
    }

    public int hashCode() {
        return 101;
    }

    public String toString() {
        return "MinusOp";
    }
}