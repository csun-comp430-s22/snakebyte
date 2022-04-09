package parser;

public class VarDecStatement<A> implements Statement {
    final String name;
    final A type;

    public VarDecStatement(final String name, final A type) {
        this.name = name;
        this.type = type;
    }

    public int hashCode() {
        return name.hashCode() + type.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VarDecStatement<?>) {
            final VarDecStatement<?> otherResult = (VarDecStatement<?>) other;
            return (otherResult.name == name && otherResult.type.equals(type));
        } else {
            return false;
        }
    }

    public String toString() {
        return "VarDecStatement(" + type.toString() + ", " + name + ")";
    }

}
