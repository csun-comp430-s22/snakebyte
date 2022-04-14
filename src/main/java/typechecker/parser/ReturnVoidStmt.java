package typechecker.parser;
public class  ReturnVoidStmt implements Statement {
     public int hashCode() { return 0; }
    public boolean equals(final Object other) {
        return other instanceof ReturnVoidStmt;
    }
    public String toString() {
        return "ReturnVoidStmt";
    }
}