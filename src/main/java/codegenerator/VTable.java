package codegenerator;
import typechecker.parser.MethodName;
import typechecker.parser.ClassName;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
public class VTable {
    public final ClassName className;
    private final List<FunctionName> functionNames;
    private final Map<MethodName,Integer> methodIndex;
    public VTable(final ClassName className,
                final List<FunctionName> functionNames,
                final Map<MethodName,Integer> methodIndex){
        this.className = className;
        this.functionNames = functionNames;
        this.methodIndex = methodIndex;
    }
    //empty table
    public VTable(final ClassName className){
        this(className,new ArrayList<FunctionName>(),new HashMap<MethodName,Integer>());
    }
    public int indexMethod(final MethodName methodname){
        final Integer retval = methodIndex.get(methodname);
        assert(retval != null);
        return retval;
    }
    public void addOrUpdateMethod(final MethodName methodname){
        final Integer index = methodIndex.get(methodname);
        final FunctionName functionName = 
                    CodeGenerator.nameManglFunctionName(className, methodname);
        if(index == null){
            functionNames.add(functionName);
            methodIndex.put(methodname,functionNames.size()-1);
        }
        else{
            functionNames.set(index.intValue(),functionName);
        }
    }
    public VTable copyTable(final ClassName className){
        final List<FunctionName> copyfunctionNames = new ArrayList<FunctionName>();
        final Map<MethodName,Integer> copymethodIndex = new HashMap<MethodName,Integer>();
        copyfunctionNames.addAll(this.functionNames);
        copymethodIndex.putAll(this.methodIndex); 
        return new VTable(className,copyfunctionNames,copymethodIndex);
    }
    public TargetVariable targetVariable(){
        return new TargetVariable("table"+className.name);
    }
    //print table
    //we expected the following format:
    // Type test = new Int();
    //or such as: Int a, Bool a, Str a, etc.
    // we don't have keyword such as: let, var, const, thus we use Type instead of them
    //we actually have the following format:
    // Type test = something;
    public void printTable(final PrintWriter outputWriter) throws IOException{
        outputWriter.print("Type ");
        outputWriter.print(targetVariable().name);
        outputWriter.print(" = ");
        final int numLeftFuc = functionNames.size();
        final Iterator<FunctionName> iter = functionNames.iterator();
        for(int index = 1; iter.hasNext() && index < numLeftFuc;index++){
            outputWriter.print(iter.next().name);
            outputWriter.print(", ");
        }
        if(iter.hasNext()){
            outputWriter.print(iter.next().name);
        }

        outputWriter.println(";");
    }
}
