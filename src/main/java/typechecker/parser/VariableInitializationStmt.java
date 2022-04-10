pack typechecker.parser;
public class VariableInitializationStmt implements Statement{
    public final VarDec vardec;
    public final Expression expression;
    public VariableInitializationStmt(final VarDec vardec, final Expression expression){
        this.vardec = vardec;
        this.expression = expression;
    }
    public int hashCode(){
        return vardec.hashCode() + expression.hashCode();
    }
    public boolean equals(final Object other){
        if(other instanceof VariableInitializationStmt){
            final VariableInitializationStmt otherStmt = (VariableInitializationStmt)other;
            return vardec.equals(otherStmt.vardec) && expression.equals(otherStmt.expression);
        }else{
            return false;
        }
    }
    public  String toString(){
        return "VariableInitializationStmt(" + vardec.toString() + ", " + expression.toString() + ")";
    }
}