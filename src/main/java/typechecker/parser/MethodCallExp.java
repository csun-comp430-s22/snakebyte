package typechecker.parser;

import java.util.List;
public class MethodCallExp implements Expression{
     public final Expression target;
    public final MethodName methodName;
    public final List<Expression> params;

    public MethodCallExp(final Expression target,
                         final MethodName methodName,
                         final List<Expression> params) {
        this.target = target;
        this.methodName = methodName;
        this.params = params;
    }

    public int hashCode() {
        return (target.hashCode() +
                methodName.hashCode() +
                params.hashCode());
    }

    public boolean equals(final Object other) {
        if (other instanceof MethodCallExp) {
            final MethodCallExp call = (MethodCallExp)other;
            return (target.equals(call.target) &&
                    methodName.equals(call.methodName) &&
                    params.equals(call.params));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("MethodCallExp(" + target.toString() + ", " +
                methodName.toString() + ", " +
                params.toString() + ")");
    }
}