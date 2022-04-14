package parser;

public class ReturnStatement  implements Statement{
    public final Expression exp;

    public ReturnStatement(final Expression exp){
        this.exp = exp;
    }
    public boolean equals(final Object other){
        if(other instanceof ReturnStatement){
            final ReturnStatement otherstatement= (ReturnStatement)other;
            return (otherstatement.exp.equals(exp));
        }
        else{
            return false;
        }
    }
    public int hashCode() {
        return (exp.hashCode());
    }

    public String toString() {
        return ("Return(" + exp.toString()+ ")");
    }
    
}
