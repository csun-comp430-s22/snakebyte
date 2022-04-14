package typechecker.parser;
public class BlockStmt implements Statement{
     public final List<Statement> body;

    public BlockStmt(final List<Statement> body) {
        this.body = body;
    }

    public int hashCode() { return body.hashCode(); }

    public boolean equals(final Object other) {
        return (other instanceof BlockStmt &&
                body.equals(((BlockStmt)other).body));
    }

    public String toString() {
        return "BlockStmt(" + body.toString() + ")";
    }
}