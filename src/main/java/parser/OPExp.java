package parser;


public class OPExp implements Expression {
    public final Expression left;
    public final Operator op;
    public final Expression right;

    public OPExp(final Expression left,
                 final Operator op,
                 final Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public boolean equals(final Object other) {
        if (other instanceof OPExp) {
            final OPExp otherExp = (OPExp)other;
            return (left.equals(otherExp.left) &&
                    op.equals(otherExp.op) &&
                    right.equals(otherExp.right));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (left.hashCode() +
                op.hashCode() +
                right.hashCode());
    }
    
    public String toString() {
        return ("OPExp(" +
                left.toString() + ", " +
                op.toString() + ", " +
                right.toString() + ")");
    }
}