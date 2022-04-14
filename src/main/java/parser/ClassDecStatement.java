package parser;

public class ClassDecStatement implements Statement {
    final Expression exp;
   

    public ClassDecStatement(final Expression exp) {
        this.exp= exp;
    }

    public int hashCode() {
        return exp.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof ClassDecStatement) {
            final ClassDecStatement otherResult = (ClassDecStatement) other;
            return (otherResult.equals(exp));
        } else {
            return false;
        }
    }

    public String toString() {
        return "ClassDecStatement(" + exp.toString() + ")";
    }

}
