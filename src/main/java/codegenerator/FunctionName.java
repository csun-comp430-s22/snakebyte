package codegenerator;

public class FunctionName {
    public final String name;
    public FunctionName(String name) {
        this.name = name;
    }
    public int hashCode() {
        return name.hashCode();
    }
    public boolean equals(final Object others){
        return (others instanceof FunctionName) && 
            name.equals(((FunctionName)others).name);
    }
    public String toString() {
        return "FunctinName(" + name + ")";
    }
}
