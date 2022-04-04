package parser;
public class Var{
    public final String name;
    public Var(final String name){
        this.name = name;
    }
    public int hashCode(){
        return name.hashCode();
    }
    public boolean equals(final Object other){
        return (other instanceof Var &&
                name.equals(((Var)other).name));
    }
    public String toString(){
        return "Var(" + name + ")";
    }
}