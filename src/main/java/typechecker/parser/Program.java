package typechecker.parser;

public class Program implements Node {
    public final Statement stmt;

    public Program(final Statement stmt) {
        this.stmt = stmt;
    }

    public boolean equals(final Object other) {
        if (other instanceof Program) {
            Program otherProg = (Program) other;
            return (otherProg.stmt.equals(this.stmt));
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 110;
    }

    public String toString() {
        return ("Program(" + stmt.toString() + ")");
    }

}
