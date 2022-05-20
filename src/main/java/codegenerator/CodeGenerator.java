package codegenerator;
import typechecker.TypeErrorException;
import typechecker.*;

import typechecker.parser.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import codegenerator.parser.*;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

import java.io.PrintWriter;
import java.io.IOException;
public class CodeGenerator  {
    //the following code could change according to the language
    public static final String SELF_NAME = "this";
    public static final String MAKE_OBJ = "class Test{ Type a, Type b}";
    public static final String DOCALHELPER = "void method(){println(\"hello\");}";
    public static final String OBJCONSTRUCTOR = "Test(Int a){this.a = a}";
    public final Program program;
    public final PrintWriter outputWriter;
    public final Map<ClassName,ClassDef> classesMap;
    public final Map<ClassName, Map<MethodName,MethodDef>> methods;
    public final Map<ClassName, VTable> vtables;
    public CodeGenerator(final Program program,
                         final PrintWriter outputWriter) throws TypeErrorException{
        this.program = program;
        this.outputWriter = outputWriter;
        this.classesMap = Typechecker.makeClassMap(program.classes);
        this.methods = Typechecker.makeMethodMap(classesMap);
        this.vtables = new HashMap<ClassName,VTable>();
        for(final ClassName className : classesMap.keySet()){
            makeVTableForClass(className);
        }
    }
    public static FunctionName nameManglFunctionName(final ClassName className,
                                                    final MethodName methodName) {
        return new FunctionName(className.name+"_"+methodName.name);
        
    }
    public static FunctionName nameMangleConstructor(final ClassName className) {
        return new FunctionName(className.name+"Constructor");
    }
    private VTable makeVTableForClass(final ClassName className) throws TypeErrorException{
        VTable vt = vtables.get(className);
        if(vt == null){
            if(className.name.equals(Typechecker.BASE_CLASS_NAME)){
                //empty table
                vt = new VTable(className);
            }else{
                final ClassDef classDef = Typechecker.getClass(className, classesMap);
                vt = makeVTableForClass(classDef.extendsClassName).copyTable(className);
                for(final MethodDef methodDef : classDef.methods){
                    vt.addOrUpdateMethod(methodDef.methodName);
                }
            }
            vtables.put(className,vt);
        }
        return vt;
    }
    public VTable getVTable(final ClassName className){
        final VTable vt = vtables.get(className);
        assert(vt != null);
        return vt;

    }
    public void writeIntLiteralExpression(final IntLiteralExp intExp) throws IOException {
        outputWriter.print(intExp.value);
    }
    public void writeStringLiteralExpression(final StringLiteralExp stringExp) throws IOException {
        outputWriter.print(stringExp.value);
    }
    public void writeVar(final Var var, final Set<Var> localVar) throws IOException {
        if(!localVar.contains(var)){
            outputWriter.print(SELF_NAME);
            outputWriter.print(".");
        }
        outputWriter.print(var.name);
    }
    public void writeVarExp(final VarExp varExp, final Set<Var> localVar) throws IOException{
        writeVar(varExp.variable, localVar);
    }
    public void writeBoolLiteralExp(final BoolLiteralExp boolLiteralExp) throws IOException {
        outputWriter.print(boolLiteralExp.value);
    }
    //include: +,-,*,/,<,>,<=,>=,==
    public void writeOp(final Operator operator) throws CodeGeneratorException, IOException {
        if(operator instanceof PlusOp){
            outputWriter.print("+");
        }else if(operator instanceof MinusOP){
            outputWriter.print("-");
        }else if(operator instanceof TimesOp){
            outputWriter.print("*");
        }else if(operator instanceof DivideOp){
            outputWriter.print("/");
        }else if(operator instanceof LessThanOp){
            outputWriter.print("<");
        }else if(operator instanceof GreaterThanOp){
            outputWriter.print(">");
        }else if(operator instanceof LessThanEqualOp){
            outputWriter.print("<=");
        }else if(operator instanceof GreaterThanEqualOp){
            outputWriter.print(">=");
        }else if(operator instanceof EqualsEqualsOp){
            outputWriter.print("==");
        }else{
            throw new CodeGeneratorException("Unknown operator");
        }
    }
    //include: bool, int, var, op, this
    //misssing: methodcallexp, newexp
    public void writeExp(final Expression expression, final Set<Var> localVar) 
                    throws CodeGeneratorException, IOException{
        if(expression instanceof IntLiteralExp){
            writeIntLiteralExpression((IntLiteralExp)expression);
        }else if(expression instanceof StringLiteralExp) {
            writeStringLiteralExpression((StringLiteralExp)expression);
        }else if(expression instanceof VarExp){
            writeVarExp((VarExp)expression, localVar);
        }else if(expression instanceof BoolLiteralExp){
            writeBoolLiteralExp((BoolLiteralExp)expression);
        }else if(expression instanceof OpExp){
            writeOpExp((OpExp)expression, localVar);
        }else if(expression instanceof ThisExp){
            outputWriter.print(SELF_NAME);
        }else if(expression instanceof MethodCallExpCodeGeneratorModified){
            writeMethodCall((MethodCallExpCodeGeneratorModified)expression, localVar);
        }else if(expression instanceof NewExp){
            writeNewExp((NewExp)expression, localVar);
        }else{
            throw new CodeGeneratorException("Unknown expression");
        }
    }
    //expresion with comma
    public void writeExpWithComma(final List<Expression> expression,final Set<Var> localVar)
                                throws CodeGeneratorException, IOException{
        final int numExp = expression.size();
        final Iterator<Expression> expIt = expression.iterator();
        for(int index =1; expIt.hasNext()&& index < numExp;index ++){
            writeExp(expIt.next(), localVar);
            outputWriter.print(",");
        }
        if(expIt.hasNext()){
            writeExp(expIt.next(), localVar);
        }
    }
    public void writeOpExp(final OpExp exp, final Set<Var> localVar) 
                    throws CodeGeneratorException, IOException {
        outputWriter.print("(");
        writeExp(exp.left, localVar);
        outputWriter.print(" ");
        writeOp(exp.operator);
        outputWriter.print(" ");
        writeExp(exp.right, localVar);
        outputWriter.print(")");
    }
    public void writeMethodCall(final MethodCallExpCodeGeneratorModified methodCallExp,final Set<Var> localVar)
                                throws CodeGeneratorException, IOException{
        assert(methodCallExp.target != null);     
        final VTable vt = getVTable(methodCallExp.targetTypeName.className);
        outputWriter.print("call(");
        writeExp(methodCallExp.target, localVar);
        outputWriter.print(",");
        outputWriter.print(vt.indexMethod(methodCallExp.methodName));
        if(! methodCallExp.params.isEmpty()){
            outputWriter.print(",");
            writeExpWithComma(methodCallExp.params, localVar);
        }
        outputWriter.print(")");
    }
    public void writeNewExp(final NewExp newExp, final Set<Var> localVar) 
                    throws CodeGeneratorException, IOException {
        final VTable vt = getVTable(newExp.className);
        outputWriter.print("new(");
        outputWriter.print(vt.targetVariable().name);
        outputWriter.print(",");
        outputWriter.print(nameMangleConstructor(newExp.className).name);
        if(! newExp.params.isEmpty()){
            outputWriter.print(",");
            writeExpWithComma(newExp.params, localVar);
        }
        outputWriter.print(")");
    }
    public static Set<Var> addVar(final Set<Var> vars, final Var var){
        final Set<Var> returnValue = new HashSet<Var>();
        returnValue.addAll(vars);
        returnValue.add(var);
        return returnValue;
    }
    public Set<Var> writeExpStmt(final ExpStmt stmt, final Set<Var> localVar) throws CodeGeneratorException, IOException {
        writeExp(stmt.expression, localVar);
        outputWriter.print(";");
        return localVar;
    }
    public Set<Var> writeVarInitialStmt(final VariableInitializationStmt stmt, final Set<Var> localVar)
    throws CodeGeneratorException,IOException{
        final typechecker.parser.Var var = stmt.vardec.var;
        if(stmt.vardec.type instanceof IntType){
            outputWriter.print("int ");
        }else if(stmt.vardec.type instanceof BoolType){
            outputWriter.print("bool ");
        }else if(stmt.vardec.type instanceof StringType){
            outputWriter.print("Str ");
        }else{
            throw new CodeGeneratorException("Unknown type");
        }
        outputWriter.print(var.name);
        outputWriter.print(" = ");
        writeExp(stmt.expression, localVar);
        outputWriter.print(";");
        return addVar(localVar, var);
    }
    public Set<Var> writeAssignStmt(final AssignStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        writeVar(stmt.variable, localVar);
        outputWriter.print(" = ");
        writeExp(stmt.exp, localVar);
        outputWriter.print(";");
        return localVar;
    }
    public Set<Var> writeIfStmt(final IfStatement  stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("if(");
        writeExp(stmt.guard, localVar);
        outputWriter.print("){");
        writeStmt(stmt.trueBranch, localVar);
        outputWriter.print("else{");
        writeStmt(stmt.falseBranch, localVar);
        outputWriter.print("}");
        return localVar;
    }
    public Set<Var> writeWhileStmt(final WhileStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("while(");
        writeExp(stmt.guard, localVar);
        outputWriter.print("){");
        writeStmt(stmt.body, localVar);
        outputWriter.print("}");
        return localVar;
    }
    public Set<Var> writeReturnNonVoidStmt(final ReturnNonVoidStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("return ");
        writeExp(stmt.exp, localVar);
        outputWriter.print(";");
        return localVar;
    }
    public Set<Var> writeReturnVoidStmt(final ReturnVoidStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("return;");
        return localVar;
    }
    //original Python not support println() method
    //added \n after print()
    public Set<Var> writePrintlnStmt(final PrintlnStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("print(");
        writeExp(stmt.expression, localVar);
        outputWriter.print(");");
        outputWriter.print("print(\"\n\")");
        return localVar;
    }
    public Set<Var> writeBlockStmt(final BlockStmt stmt, final Set<Var> localVar) throws CodeGeneratorException,IOException{
        outputWriter.print("{");
        writeNestStmt(stmt.body.iterator(), localVar);
        outputWriter.print("}");
        return localVar;
    }
    public void writeNestStmt(final Iterator<Statement> stmt, Set<Var> localVar) throws CodeGeneratorException,IOException{
        if(stmt.hasNext()){
            localVar = writeStmt(stmt.next(), localVar);
            outputWriter.print("{");
            writeNestStmt(stmt, localVar);
            outputWriter.print("}");
        }
    }
    public Set<Var> writeStmt(final Statement statement,final Set<Var> localVar) 
                        throws CodeGeneratorException,IOException{
        // was: if(statement instanceof ExpStmt){
        //    return writeStmt((ExpStmt)statement, localVar);
        if(statement instanceof ExpStmt){
            return writeExpStmt((ExpStmt)statement, localVar);
        }else if(statement instanceof VariableInitializationStmt){
            return writeVarInitialStmt((VariableInitializationStmt)statement, localVar);
        }else if(statement instanceof AssignStmt){
            return writeAssignStmt((AssignStmt)statement, localVar);
        }else if(statement instanceof IfStatement ){
            return writeIfStmt((IfStatement )statement, localVar);
        }else if(statement instanceof WhileStmt){
            return writeWhileStmt((WhileStmt)statement, localVar);
        }else if(statement instanceof ReturnNonVoidStmt){
            return writeReturnNonVoidStmt((ReturnNonVoidStmt)statement, localVar);
        }else if(statement instanceof ReturnVoidStmt){
            return writeReturnVoidStmt((ReturnVoidStmt)statement, localVar);
        }else if(statement instanceof PrintlnStmt){
            return writePrintlnStmt((PrintlnStmt)statement, localVar);
        }else if(statement instanceof BlockStmt){
            return writeBlockStmt((BlockStmt)statement, localVar);
        }else{
            throw new CodeGeneratorException("Unknown statement: "+ statement.toString());
        }
    }
    public void writeFormalParams(final List<VarDec> varDecs) throws IOException{
        final int numParams = varDecs.size();
        final Iterator<VarDec> varDecsIterator = varDecs.iterator();
        for(int index = 1; varDecsIterator.hasNext() && index < numParams;index++){
            outputWriter.print(varDecsIterator.next().var.name);
            outputWriter.print(", ");
        }
        if(varDecsIterator.hasNext()){
            outputWriter.print(varDecsIterator.next().var.name);
        }
    }
    public static Set<Var> initializeVar(final List<VarDec> varDecs){
        final Set<Var> returnValue = new HashSet<Var>();
        for(final VarDec var : varDecs){
            returnValue.add(var.var);
        }
        return returnValue;
    }
    public void writeMethod(final ClassName forClass, final MethodDef methodDef)
    throws IOException, CodeGeneratorException{
        outputWriter.print("def ");
        outputWriter.print(nameManglFunctionName(forClass, methodDef.methodName).name);
        outputWriter.print("(");
        if(!methodDef.arguments.isEmpty()){
            outputWriter.print(",");
            writeFormalParams(methodDef.arguments);
        }
        outputWriter.print("){");
        writeStmt(methodDef.body, initializeVar(methodDef.arguments));
        outputWriter.print("}");
    }
    public void writeConstructor(final ClassDef classDef) throws CodeGeneratorException, IOException{
        outputWriter.print("def ");
        outputWriter.print(nameMangleConstructor(classDef.className).name);
        outputWriter.print("(");
        outputWriter.print(SELF_NAME);
        if(! classDef.constructorArguments.isEmpty()){
            outputWriter.print(",");
            writeFormalParams(classDef.constructorArguments);
        }
        outputWriter.print("){");
        //inheritance constructor
        final Set<Var> localVars = initializeVar(classDef.constructorArguments);
        outputWriter.print(nameMangleConstructor(classDef.extendsClassName).name);
        outputWriter.print("(");
        outputWriter.print(SELF_NAME);
        if(!classDef.superParams.isEmpty()){
            outputWriter.print(",");
            writeExpWithComma(classDef.superParams, localVars);
        }
        outputWriter.print(");");
        writeNestStmt(classDef.constructorBody.iterator(), localVars);
        outputWriter.print("}");
    }
    public void writeClass(final ClassDef classDef) throws CodeGeneratorException, IOException{
        writeConstructor(classDef);
        for(final MethodDef methodDef : classDef.methods){
            writeMethod(classDef.className, methodDef);
        }

    }
    //run time
    public void writeRunTime() throws IOException{
        outputWriter.print(MAKE_OBJ);
        outputWriter.print(DOCALHELPER);
        outputWriter.print(OBJCONSTRUCTOR);
    }
    public void generateCode() throws IOException, CodeGeneratorException{
        writeRunTime();
        for(final VTable vt : vtables.values()){
            vt.printTable(outputWriter);
        }
        for(final ClassDef classDef : program.classes){
            writeClass(classDef);
        }
        writeStmt(program.entryPoint, new HashSet<Var>());
    }
    public static void generateCode(final Program program, final PrintWriter output) 
                throws IOException, CodeGeneratorException, TypeErrorException{
        new CodeGenerator(program, output).generateCode();
    }
}