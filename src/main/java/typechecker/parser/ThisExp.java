package typechecker.parser;
public class ThisExp implements Expression {
    public int hashCode() { return 0; }
    public boolean equals(final Object other) {
        return other instanceof ThisExp;
    }
    public String toString() { return "ThisExp"; }
}