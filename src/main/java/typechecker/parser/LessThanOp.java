package typechecker.parser;
public class LessThanOp implements Operator{
    public int hashCode() { return 0; }
    public boolean equals(final Object other) {
        return other instanceof LessThanOp;
    }
    public String toString() {
        return "LessThanOp";
    }
}