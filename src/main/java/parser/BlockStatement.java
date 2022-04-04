package parser;

import java.util.List;

public class BlockStatement implements Statement {
    public final List<Statement> stmts;

    public BlockStatement(final List<Statement> stmts) {
        this.stmts = stmts;
    }
}
// need to implement equals and to string and hash code