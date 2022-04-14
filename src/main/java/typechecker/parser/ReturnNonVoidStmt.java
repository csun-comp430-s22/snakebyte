package typechecker.parser;
public class ReturnNonVoidStmt implements Statement {
    public final Expression exp;

    public ReturnNonVoidStmt(final Expression exp) {
        this.exp = exp;
    }

    public int hashCode() { return exp.hashCode(); }

    public boolean equals(final Object other) {
        return (other instanceof ReturnNonVoidStmt &&
                exp.equals(((ReturnNonVoidStmt)other).exp));
    }

    public String toString() {
        return "ReturnNonVoidStmt(" + exp.toString() + ")";
    }
}