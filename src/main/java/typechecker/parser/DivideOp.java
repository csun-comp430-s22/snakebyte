package typechecker.parser;

public class DivideOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof DivideOp;
    }

    public int hashCode() {
        return 103;
    }

    public String toString() {
        return "DivideOp";
    }
}