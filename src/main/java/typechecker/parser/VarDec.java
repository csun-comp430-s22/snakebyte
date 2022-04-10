package typechecker.parser;
public class VarDec{
    public final Type type;
    public final Var var;
    public VarDec(final Type type, final Var var){
        this.type = type;
        this.var = var;
    }
    public int hashCode(){
        return type.hashCode() + var.hashCode();
    }
    public boolean equals(final Object other){
        if(other instanceof VarDec){
            final VarDec otherDec = (VarDec)other;
            return type.equals(otherDec.type) && var.equals(otherDec.var);
        }else{
            return false;
        }
    }
}