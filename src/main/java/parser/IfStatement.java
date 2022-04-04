
package parser;

public class IfStatement implements Statement {
    public final Expression guard;
    public final Statement trueBranch;
    public final Statement falseBranch;

    public IfStatement(final Expression guard, final Statement trueBranch, final Statement falseBranch) {
        this.guard = guard;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public boolean equals(final Object other) {
        if (other instanceof IfStatement) {
            final IfStatement otherif = (IfStatement) other;
            return (guard.equals(otherif.guard) &&
                    trueBranch.equals(otherif.trueBranch) &&
                    falseBranch.equals(otherif.falseBranch));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (guard.hashCode() +
                trueBranch.hashCode() +
                falseBranch.hashCode());
    }

    public String toString() {
        return ("IfStatement(" +
                guard.toString() + ", " +
                trueBranch.toString() + ", " +
                falseBranch.toString() + ")");
    }
}