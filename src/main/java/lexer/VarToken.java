package lexer;
public class VarToken implements Token {
    public final String name;
    public VarToken(String name){
        this.name = name;
    }
    public int hashCode(){
        return this.name.hashCode();
    }
    public String toString(){
        return "Variable("+ this.name +")";
    }
    public boolean equals(final Object other){
        if(other instanceof VarToken){
            final VarToken asVar = (VarToken) other;
            return this.name.equals(asVar.name);
        }else{
            return false;
        }
    }
}