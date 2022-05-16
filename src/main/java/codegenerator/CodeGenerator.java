package codegenerator;
import typechecker.*;
import typechecker.parser.ClassName;
import typechecker.parser.MethodName;
import lexer.*;
import parser.*;
public class CodeGenerator  {
    public static FunctionName nameManglFunctionName(final ClassName className,
                                                    final MethodName methodName) {
        return new FunctionName(className.name+""+methodName.name);
        
    }
}
