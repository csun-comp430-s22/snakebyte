package codegenerator.parser;

import typechecker.parser.*;
public class AssignStmt implements Statement{
    public final Var variable;
    public final Expression exp;

    public AssignStmt(final Var variable,
                      final Expression exp) {
        this.variable = variable;
        this.exp = exp;
    }

    public int hashCode() {
        return variable.hashCode() + exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof AssignStmt) {
            final AssignStmt asAssign = (AssignStmt)other;
            return (variable.equals(asAssign.variable) &&
                    exp.equals(asAssign.exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("AssignStmt(" + variable.toString() + ", " +
                exp.toString() + ")");
    }
}
