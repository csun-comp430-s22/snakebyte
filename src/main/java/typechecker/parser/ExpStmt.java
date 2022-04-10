package typechecker.parser;
public class ExpStmt implements Statement {
    public final Expression expression;
    public ExpStmt(final Expression expression){
        this.expression = expression;
    }
    public boolean equals(final Object other){
        return (other instanceof ExpStmt &&
                expression.equals(((ExpStmt)other).expression));
    }
    public int hashCode(){
        return expression.hashCode();
    }
    public String toString(){
        return "ExpStmt(" + expression.toString() + ")";
    }
}