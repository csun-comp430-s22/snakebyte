package parser;

import java.util.List;

public class BlockStatement implements Statement {
    public final List<Statement> stmts;

    public BlockStatement(final List<Statement> stmts) {
        this.stmts = stmts;
    }
    public boolean equals(final Object other) {
        if(other instanceof BlockStatement) {
            final BlockStatement otherstatement = (BlockStatement)other;
            return (otherstatement.stmts.equals(this.stmts));
        } else {
            return false;
        }
    }
    public String toString() {
        return "BlockStatement(" +
                stmts.toString() + ")";
    }
    public int hashCode() {
        return (stmts.hashCode());
    }
}
// need to implement equals and to string and hash code