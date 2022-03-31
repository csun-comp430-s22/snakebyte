package src.main.java.parser;



public class VarExp  implements Expression{

    public final String name;

    public  VarExp(final String name) {
        this.name = name;
    }

    public boolean equals(final Object other) {
        return (other instanceof VarExp &&
                name.equals(((VarExp)other).name));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return "VarExp(" + name + ")";
    } 
}
