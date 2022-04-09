package parser;

public class WhileStatement implements Statement {
    public final Expression guard;
    public final Statement trueBranch;

    public WhileStatement(final Expression guard, final Statement trueBranch) {
        this.guard = guard;
        this.trueBranch = trueBranch;
    }

    public boolean equals(final Object other) {
        if (other instanceof WhileStatement) {
            final WhileStatement otherWhile = (WhileStatement) other;
            return (guard.equals(otherWhile.guard) &&
                    trueBranch.equals(otherWhile.trueBranch));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (guard.hashCode() + trueBranch.hashCode());
    }

    public String toString() {
        return ("WhileStatement(" +
                guard.toString() + ", " +
                trueBranch.toString() + ")");
    }
}
