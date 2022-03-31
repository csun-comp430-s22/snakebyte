package parser;


public class PrintStatement implements Statement{
    public final Expression exp;

    public PrintStatement(final Expression exp){
        this.exp = exp;
    }
    public boolean equals(final Object other){
        if(other instanceof PrintStatement){
            final PrintStatement otherstatement= (PrintStatement)other;
            return (exp.equals(otherstatement));
        }
        else{
            return false;
        }
    }
    public int hashCode() {
        return (exp.hashCode());
    }

    public String toString() {
        return ("IfStmt(" + exp.toString()+ ")");
    }
}
