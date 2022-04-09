package parser;

public class VarDecStatement<A, B> implements Statement {
    final String name;
    final A type;
    B value;

    public VarDecStatement(final String name, final A type, B value) {
        this.name = name;
        this.type = type;
        this.value = value;

    }

    public int hashCode() {
        return value.hashCode() + name.hashCode() + type.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof VarDecStatement<?, ?>) {
            final VarDecStatement<?, ?> otherResult = (VarDecStatement<?, ?>) other;
            return (otherResult.name == name && otherResult.type.equals(type) && otherResult.value.equals(value));
        } else {
            return false;
        }
    }

    public String toString() {
        return "VarDecStatement(" + name + ", " + type.toString() + ", " + value.toString() + ")";
    }

}
