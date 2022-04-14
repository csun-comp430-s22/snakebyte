package typechecker.parser;

public class VarDecStatement implements Statement {
    public final VarDec vardec;
    public final Expression exp;

    public VarDecStatement(final VarDec vardec,
            final Expression exp) {
        this.vardec = vardec;
        this.exp = exp;
    }

    public int hashCode() {
        return vardec.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VarDecStatement) {
            final VarDecStatement otherStmt = (VarDecStatement) other;
            return (vardec.equals(otherStmt.vardec) &&
                    exp.equals(otherStmt.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("VardecStmt(" + vardec.toString() + ", " +
                exp.toString() + ")");
    }

}
