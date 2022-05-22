package codegenerator.parser;
import typechecker.parser.Expression;
import typechecker.parser.MethodName;
import typechecker.parser.ClassNameType;

import java.util.List;
public class MethodCallExpCodeGeneratorModified implements Expression {
    public final Expression target;
    public ClassNameType targetTypeName;
    public final MethodName methodName;
    public final List<Expression> params;
    public MethodCallExpCodeGeneratorModified(final Expression target,
                                            final MethodName methodName,
                                            final ClassNameType classNameType,
                                            final List<Expression> params){
        this.target = target;
        this.targetTypeName = classNameType;
        this.methodName = methodName;
        this.params = params;
    }
    public int hashCode() {
        return (target.hashCode() +
                ((targetTypeName == null) ? 0 : targetTypeName.hashCode()) +
                methodName.hashCode() +
                params.hashCode());
    }
    public boolean equals(final Object other){
        if(other instanceof MethodCallExpCodeGeneratorModified){
            final MethodCallExpCodeGeneratorModified call = (MethodCallExpCodeGeneratorModified)other;
            return (target.equals(call.target) &&
                    ((targetTypeName == null && call.targetTypeName == null) ||
                     (targetTypeName != null && targetTypeName.equals(call.targetTypeName))) &&
                    methodName.equals(call.methodName) &&
                    params.equals(call.params));
        }else{
            return false;
        }
    }
    public String toString(){
        return ("MethodCallExp(" + target.toString() + ", " +
                ((targetTypeName == null) ? "null" : targetTypeName.toString()) + ", " +
                methodName.toString() + ", " +
                params.toString() + ")");
    }
}
