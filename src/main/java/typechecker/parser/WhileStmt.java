package typechecker.parser;

public class WhileStmt implements Statement {
    public final Expression guard;
    public final Statement body;

    public WhileStmt(final Expression guard,
                     final Statement body) {
        this.guard = guard;
        this.body = body;
    }

    public int hashCode() {
        return guard.hashCode() + body.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof WhileStmt) {
            final WhileStmt otherStmt = (WhileStmt)other;
            return (guard.equals(otherStmt.guard) &&
                    body.equals(otherStmt.body));
        } else {
            return false;
        }
    }
}