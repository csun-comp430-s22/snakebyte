package codegenerator;

public class TargetVariable {
    public final String name;
    public TargetVariable(String name) {
        this.name = name;
    }
    public int hashCode() {
        return name.hashCode();
    }
    public boolean equals(final Object others){
        return (others instanceof TargetVariable) && 
            name.equals(((TargetVariable)others).name);
    }
    public String toString() {
        return "TargetVariable(" + name + ")";
    }
}
