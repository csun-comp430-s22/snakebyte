package parser;


public class IfExp implements Expression{
    public final Expression gaurd;
    public final Expression truebranch;
    public final Expression falsebranch;
    
    public IfExp(final Expression Guard,final Expression trueBranch,final Expression falseBranch){
        this.gaurd= Guard;
        this.truebranch=trueBranch;
        this.falsebranch= falseBranch;
    }
    public boolean equals(final Object other) {
        if (other instanceof IfExp) {
            final IfExp otherif = (IfExp)other;
            return (gaurd.equals(otherif.gaurd) &&
                    truebranch.equals(otherif.truebranch) &&
                    falsebranch.equals(otherif.falsebranch));
        } else {
            return false;
        }
    }
    public int hashCode() {
        return (gaurd.hashCode() +
                truebranch.hashCode() +
                falsebranch.hashCode());
    }

    public String toString() {
        return ("IfExp(" +
                gaurd.toString() + ", " +
                truebranch.toString() + ", " +
                falsebranch.toString() + ")");
    }
    
}
