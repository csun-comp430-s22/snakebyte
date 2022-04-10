package typechecker.parser;
public class OpExp implements Expression{
    pbulic final Expression left;
    public final Operator operator;
    public final Expression right;
    public OpExp(final Expression left, final Operator operator, final Expression right){
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    public boolean equals(final Object other){
        if(other instanceof OpExp){
            final OpExp otherExp = (OpExp)other;
            return left.equals(otherExp.left) &&
                   operator.equals(otherExp.operator) &&
                   right.equals(otherExp.right);
        }else{
            return false;
        }
    }
    public int hashCode(){
        return left.hashCode() + operator.hashCode() + right.hashCode();
    }
    public String toString(){
        return "OpExp(" + left.toString() + ", " + operator.toString() + ", " + right.toString() + ")";
    }
}