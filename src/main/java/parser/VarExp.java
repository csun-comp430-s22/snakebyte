package parser;



public class VarExp  implements Expression{
    //new modified code:
    public final Var variable;
    public VarExp(final Var variable){
        this.variable = variable;
    }
    public boolean equals(final Object other){
        return (other instanceof VarExp &&
                variable.equals(((VarExp)other).variable));
    }
    public int hashCode(){
        return variable.hashCode();
    }
    public String toString(){
        return "VarExp(" + variable + ")";
    }
    //original code was:
    // public final String name;

    // public  VarExp(final String name) {
    //     this.name = name;
    // }

    // public boolean equals(final Object other) {
    //     return (other instanceof VarExp &&
    //             name.equals(((VarExp)other).name));
    // }

    // public int hashCode() {
    //     return name.hashCode();
    // }

    // public String toString() {
    //     return "VarExp(" + name + ")";
    // } 
}
