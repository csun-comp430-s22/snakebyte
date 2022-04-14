package typechecker.parser;
public class ClassNameType implements Type{
     public final ClassName className;

    public ClassNameType(final ClassName className) {
        this.className = className;
    }

    public int hashCode() { return className.hashCode(); }

    public boolean equals(final Object other) {
        return (other instanceof ClassNameType &&
                className.equals(((ClassNameType)other).className));
    }

    public String toString() {
        return "ClassNameType(" + className.toString() + ")";
    }
}