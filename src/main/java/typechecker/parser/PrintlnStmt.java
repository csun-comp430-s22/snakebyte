package typechecker.parser;
public class PrintlnStmt implements Statement {
    public final Expression expression;

    public PrintlnStmt(final Expression expression) {
        this.expression = expression;
    }

    public int hashCode() { return expression.hashCode(); }

    public boolean equals(final Object other) {
        return (other instanceof PrintlnStmt &&
                expression.equals(((PrintlnStmt)other).expression));
    }

    public String toString() {
        return "PrintlnStmt(" + expression.toString() + ")";
    }
}