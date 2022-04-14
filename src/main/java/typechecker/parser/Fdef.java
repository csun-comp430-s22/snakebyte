package typechecker.parser;

import java.util.List;

public class Fdef {
    public final Type returnType;
    public final FunctionName fname;
    public final List<VarDec> arguments;
    public final Statement body;

    public Fdef(final Type returnType,
            final FunctionName fname,
            final List<VarDec> arguments,
            final Statement body) {
        this.returnType = returnType;
        this.fname = fname;
        this.arguments = arguments;
        this.body = body;
    }

    public int hashCode() {
        return (returnType.hashCode() +
                fname.hashCode() +
                arguments.hashCode() +
                body.hashCode());
    }

    public boolean equals(final Object other) {
        if (other instanceof Fdef) {
            final Fdef otherDef = (Fdef) other;
            return (returnType.equals(otherDef.returnType) &&
                    fname.equals(otherDef.fname) &&
                    arguments.equals(otherDef.arguments) &&
                    body.equals(otherDef.body));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("Fdef(" + returnType.toString() + ", " +
                fname.toString() + ", " +
                arguments.toString() + ", " +
                body.toString() + ")");
    }

}
