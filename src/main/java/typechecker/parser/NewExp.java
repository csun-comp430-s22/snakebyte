package typechecker.parser;

import java.util.List;
public class NewExp implements Expression{
    public final ClassName className;
    public final List<Expression> params;

    public NewExp(final ClassName className,
                  final List<Expression> params) {
        this.className = className;
        this.params = params;
    }

    public int hashCode() {
        return className.hashCode() + params.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof NewExp) {
            final NewExp otherNew = (NewExp)other;
            return (className.equals(otherNew.className) &&
                    params.equals(otherNew.params));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("NewExp(" + className.toString() + ", " +
                params.toString() + ")");
    }
}