package typechecker.parser;

public class FunctionName {
    public final String name;

    public FunctionName(final String name) {
        this.name = name;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(final Object other) {
        return (other instanceof FunctionName &&
                name.equals(((FunctionName) other).name));
    }

    public String toString() {
        return "FunctionName(" + name + ")";
    }
}
