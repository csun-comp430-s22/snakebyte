package typechecker;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import typechecker.parser.*;
// typechecks: well-typed: no type errors
// doesn't typecheck: ill-typed: some number of type errors (>0)

public class Typechecker {
    // Things to track:
    // 1.) Variables in scope, and their types
    // 2.) Classes available, parameters constructors take, methods they have, what their parent
    //     class is.
    //
    // Sorts of queries we want to make to class information:
    // 1. Is this a valid class?
    // 2. For this class, what are the argument types for the constructor?
    // 3. Does this class support a given method?  If so, what are the parameter
    //    types for the method?
    //    - Need to take inheritance into account
    // 4. Is this given class a subclass of another class?
    // 5. Does our class hierarchy form a tree?

    public final List<ClassDef> classes;
    public final Program program;
    
    // recommended: ClassName -> All Methods on the Class
    // recommended: ClassName -> ParentClass
    public Typechecker(final Program program) {
        this.program = program;
        this.classes = program.classes;
        // TODO: check that class hierarchy is a tree
    }

    public Type typeofVariable(final VarExp exp,
                               final Map<Var, Type> typeEnvironment) throws TypeErrorException {
        final Type mapType = typeEnvironment.get(exp.variable);
        if (mapType == null) {
            throw new TypeErrorException("Used variable not in scope: " + exp.variable.name);
        } else {
            return mapType;
        }
    }

    public Type typeofThis(final ClassName classWeAreIn) throws TypeErrorException {
        if (classWeAreIn == null) {
            throw new TypeErrorException("this used in the entry point");
        } else {
            return new ClassNameType(classWeAreIn);
        }
    }

    public Type typeofOp(final OpExp exp,
                         final Map<Var, Type> typeEnvironment,
                         final ClassName classWeAreIn) throws TypeErrorException {
        final Type leftType = typeof(exp.left, typeEnvironment, classWeAreIn);
        final Type rightType = typeof(exp.right, typeEnvironment, classWeAreIn);
        // (leftType, exp.op, rightType) match {
        //   case (IntType, PlusOp, IntType) => IntType
        //   case (IntType, LessThanOp | EqualsOp, IntType) => Booltype
        //   case _ => throw new TypeErrorException("Operator mismatch")
        // }
        if (exp.operator instanceof PlusOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType();
            } else {
                throw new TypeErrorException("Operand type mismatch for +");
            }
        } else if (exp.operator instanceof LessThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for <");
            }
        } else if (exp.operator instanceof EqualsOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for ==");
            }
        } else {
            throw new TypeErrorException("Unsupported operation: " + exp.operator);
        }
    }

    public Type expectedReturnTypeForClassAndMethod(final ClassName className,
                                                    final MethodName methodName) {
        // WRONG - needs to find the given class and method, and return the expected
        // return type for this
        return null;
    }

    // Doesn't handle access modifiers right now; would be to know which class we
    // are calling from.
    //
    // class Base extends Object {
    //   public void basePublic() {}
    //   protected void baseProtected() {}
    //   private void basePrivate() {}
    // }
    // class Sub extends Base {
    //   public void foobar() {
    //     this.basePublic();  // should be ok
    //     this.baseProtected(); // should be ok
    //     this.basePrivate(); // should give an error
    //   }
    // }
    // class SomeOtherClass extends Object {
    //   public void test() {
    //     Sub sub = new Sub();
    //     sub.basePublic(); // should be ok
    //     sub.baseProtected(); // should give an error
    //     sub.basePrivate(); // should give an error
    //   }
    // }
    //
    // doesn't handle inherited methods
    // for every class:
    //   - Methods on that class
    //   - Methods on the parent of that class
    public List<Type> expectedParameterTypesForClassAndMethod(final ClassName className,
                                                              final MethodName methodName)
        throws TypeErrorException {
        for (final ClassDef candidateClass : classes) {
            if (candidateClass.className.equals(className)) {
                for (final MethodDef candidateMethod : candidateClass.methods) {
                    if (candidateMethod.methodName.equals(methodName)) {
                        final List<Type> expectedTypes = new ArrayList<Type>();
                        for (final VarDec vardec : candidateMethod.arguments) {
                            expectedTypes.add(vardec.type);
                        }
                        return expectedTypes;
                    }
                }
            }
        }

        throw new TypeErrorException("No method named " + methodName + " on class " + className);
    }

    public boolean isSubtypeOf(final Type first, final Type second) throws TypeErrorException {
        // WRONG: needs to check this
        return true;
    }
    
    public void isEqualOrSubtypeOf(final Type first, final Type second) throws TypeErrorException {
        if (!(first.equals(second) || isSubtypeOf(first, second))) {
            throw new TypeErrorException("types incompatible: " + first + ", " + second);
        }
    }

    // List<Type> - expected types
    // List<Exp> - received expressions
    public void expressionsOk(final List<Type> expectedTypes,
                              final List<Expression> receivedExpressions,
                              final Map<Var, Type> typeEnvironment,
                              final ClassName classWeAreIn) throws TypeErrorException {
        if (expectedTypes.size() != receivedExpressions.size()) {
            throw new TypeErrorException("Wrong number of parameters");
        }
        for (int index = 0; index < expectedTypes.size(); index++) {
            final Type paramType = typeof(receivedExpressions.get(index), typeEnvironment, classWeAreIn);
            final Type expectedType = expectedTypes.get(index);
            // myMethod(int, bool, int)
            // myMethod(  2, true,   3)
            //
            // myMethod2(BaseClass)
            // myMethod2(new SubClass())
            isEqualOrSubtypeOf(paramType, expectedType);
        }
    }
    
    // 1.) target should be a class.
    // 2.) target needs to have the methodname method
    // 3.) need to know the expected parameter types for the method
    //
    // exp.methodname(exp*)
    // target.methodName(params)
    public Type typeofMethodCall(final MethodCallExp exp,
                                 final Map<Var, Type> typeEnvironment,
                                 final ClassName classWeAreIn) throws TypeErrorException {
        final Type targetType = typeof(exp.target, typeEnvironment, classWeAreIn);
        if (targetType instanceof ClassNameType) {
            final ClassName className = ((ClassNameType)targetType).className;
            final List<Type> expectedTypes =
                expectedParameterTypesForClassAndMethod(className, exp.methodName);
            expressionsOk(expectedTypes, exp.params, typeEnvironment, classWeAreIn);
            return expectedReturnTypeForClassAndMethod(className, exp.methodName);
        } else {
            throw new TypeErrorException("Called method on non-class type: " + targetType);
        }
    }

    public List<Type> expectedConstructorTypesForClass(final ClassName className)
        throws TypeErrorException {
        // WRONG - needs to grab the expected constructor types for this class
        // throws an exception if this class doesn't exception
        return null;
    }
    
    // new classname(exp*)
    // new className(params)
    public Type typeofNew(final NewExp exp,
                          final Map<Var, Type> typeEnvironment,
                          final ClassName classWeAreIn) throws TypeErrorException {
        // need to know what the constructor arguments for this class are
        final List<Type> expectedTypes = expectedConstructorTypesForClass(exp.className);
        expressionsOk(expectedTypes, exp.params, typeEnvironment, classWeAreIn);
        return new ClassNameType(exp.className);
    }
    
    // classWeAreIn is null if we are in the entry point
    public Type typeof(final Expression exp,
                       final Map<Var, Type> typeEnvironment,
                       final ClassName classWeAreIn) throws TypeErrorException {
        if (exp instanceof IntLiteralExp) {
            return new IntType();
        } else if (exp instanceof VarExp) {
            return typeofVariable((VarExp)exp, typeEnvironment);
        } else if (exp instanceof BoolLiteralExp) {
            return new BoolType();
        } else if (exp instanceof ThisExp) {
            return typeofThis(classWeAreIn);
        } else if (exp instanceof OpExp) {
            return typeofOp((OpExp)exp, typeEnvironment, classWeAreIn);
        } else if (exp instanceof MethodCallExp) {
            return typeofMethodCall((MethodCallExp)exp, typeEnvironment, classWeAreIn);
        } else if (exp instanceof NewExp) {
            return typeofNew((NewExp)exp, typeEnvironment, classWeAreIn);
        } else {
            throw new TypeErrorException("Unrecognized expression: " + exp);
        }
    }

    public static Map<Var, Type> addToMap(final Map<Var, Type> map,
                                               final Var variable,
                                               final Type type) {
        final Map<Var, Type> result = new HashMap<Var, Type>();
        result.putAll(map);
        result.put(variable, type);
        return result;
    }

    public Map<Var, Type> isWellTypedVar(final VariableInitializationStmt stmt,
                                              final Map<Var, Type> typeEnvironment,
                                              final ClassName classWeAreIn) throws TypeErrorException {
        final Type expType = typeof(stmt.expression, typeEnvironment, classWeAreIn);
        isEqualOrSubtypeOf(expType, stmt.vardec.type);
        return addToMap(typeEnvironment, stmt.vardec.var, stmt.vardec.type);
    }

    public Map<Var, Type> isWellTypedIf(final IfStatement stmt,
                                             final Map<Var, Type> typeEnvironment,
                                             final ClassName classWeAreIn,
                                             final Type functionReturnType) throws TypeErrorException {
        if (typeof(stmt.guard, typeEnvironment, classWeAreIn) instanceof BoolType) {
            isWellTypedStmt(stmt.trueBranch, typeEnvironment, classWeAreIn, functionReturnType);
            isWellTypedStmt(stmt.falseBranch, typeEnvironment, classWeAreIn, functionReturnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard of if is not a boolean: " + stmt);
        }
    }

    public Map<Var, Type> isWellTypedWhile(final WhileStmt stmt,
                                                final Map<Var, Type> typeEnvironment,
                                                final ClassName classWeAreIn,
                                                final Type functionReturnType) throws TypeErrorException {
        if (typeof(stmt.guard, typeEnvironment, classWeAreIn) instanceof BoolType) {
            isWellTypedStmt(stmt.body, typeEnvironment, classWeAreIn, functionReturnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard on while is not a boolean: " + stmt);
        }
    }

    public Map<Var, Type> isWellTypedBlock(final BlockStmt stmt,
                                                Map<Var, Type> typeEnvironment,
                                                final ClassName classWeAreIn,
                                                final Type functionReturnType) throws TypeErrorException {
        for (final Statement bodyStmt : stmt.body) {
            typeEnvironment = isWellTypedStmt(bodyStmt, typeEnvironment, classWeAreIn, functionReturnType);
        }
        return typeEnvironment;
    }
    
    // return exp;
    public Map<Var, Type> isWellTypedReturnNonVoid(final ReturnNonVoidStmt stmt,
                                                        final Map<Var, Type> typeEnvironment,
                                                        final ClassName classWeAreIn,
                                                        final Type functionReturnType) throws TypeErrorException {
        if (functionReturnType == null) {
            throw new TypeErrorException("return in program entry point");
        } else {
            final Type receivedType = typeof(stmt.exp, typeEnvironment, classWeAreIn);
            isEqualOrSubtypeOf(receivedType, functionReturnType);
            return typeEnvironment;
        }
    }

    public Map<Var, Type> isWellTypedReturnVoid(final Map<Var, Type> typeEnvironment,
                                                     final ClassName classWeAreIn,
                                                     final Type functionReturnType) throws TypeErrorException {
        if (functionReturnType == null) {
            throw new TypeErrorException("return in program entry point");
        } else if (!functionReturnType.equals(new VoidType())) {
            throw new TypeErrorException("return of void in non-void context");
        } else {
            return typeEnvironment;
        }
    }
    
    // bool x = true;
    // while (true) {
    //   int x = 17;
    //   break;
    // }
    public Map<Var, Type> isWellTypedStmt(final Statement stmt,
                                               final Map<Var, Type> typeEnvironment,
                                               final ClassName classWeAreIn,
                                               final Type functionReturnType) throws TypeErrorException {
        if (stmt instanceof ExpStmt) {
            //this line is not compile
            typeof(((ExpStmt)stmt).expression, typeEnvironment, classWeAreIn, functionReturnType);
            return typeEnvironment;
        } else if (stmt instanceof VariableInitializationStmt) {
            //this line is not compile
            return isWellTypedVar((VariableInitializationStmt)stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof IfStatement) {
            return isWellTypedIf((IfStatement)stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof WhileStmt) {
            return isWellTypedWhile((WhileStmt)stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof ReturnNonVoidStmt) {
            return isWellTypedReturnNonVoid((ReturnNonVoidStmt)stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof ReturnVoidStmt) {
            return isWellTypedReturnVoid(typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof PrintlnStmt) {
            //this line is not compile
            typeof(((PrintlnStmt)stmt).expression, typeEnvironment, classWeAreIn, functionReturnType);
            return typeEnvironment;
        } else if (stmt instanceof BlockStmt) {
            return isWellTypedBlock((BlockStmt)stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else {
            throw new TypeErrorException("Unsupported statement: " + stmt);
        }

    }

    // methoddef ::= type methodname(vardec*) stmt
    public void isWellTypedMethodDef(final MethodDef method,
                                     Map<Var, Type> typeEnvironment, // instance variables
                                     final ClassName classWeAreIn) throws TypeErrorException {
        // starting type environment: just instance variables
        // int addTwo(int x, int y) { return x + y; }
        //
        // int x;
        // int addTwo(bool x, int x) { return x; }
        for (final VarDec vardec : method.arguments) {
            // odd semantics: last variable declaration shadows prior one
            typeEnvironment = addToMap(typeEnvironment, vardec.var, vardec.type);
        }
        
        isWellTypedStmt(method.body,
                        typeEnvironment, // instance variables + parameters
                        classWeAreIn,
                        method.returnType);
    }

    // classdef ::= class classname extends classname {
    //            vardec*; // comma-separated instance variables
    //            constructor(vardec*) {
    //              super(exp*);
    //              stmt* // comma-separated
    //            }
    //            methoddef*
    //          }

    // -Check constructor
    // -Check methods
    public void isWellTypedClassDef(final ClassDef classDef) throws TypeErrorException {
        // TODO: add instance variables from parent classes; currently broken
        // weird: duplicate instance variables
        // class MyClass extends Object {
        //   int x;
        //   bool x;
        //   ...
        // }
        Map<Var, Type> typeEnvironment = new HashMap<Var, Type>();
        for (final VarDec vardec : classDef.instanceVariables) {
            typeEnvironment = addToMap(typeEnvironment, vardec.var, vardec.type);
        }
        
        // check constructor
        Map<Var, Type> constructorTypeEnvironment = typeEnvironment;
        for (final VarDec vardec : classDef.constructorArguments) {
            constructorTypeEnvironment = addToMap(constructorTypeEnvironment, vardec.var, vardec.type);
        }
        // check call to super
        expressionsOk(expectedConstructorTypesForClass(classDef.extendsClassName),
                      classDef.superParams,
                      constructorTypeEnvironment,
                      classDef.className);
        isWellTypedBlock(new BlockStmt(classDef.constructorBody),
                         constructorTypeEnvironment,
                         classDef.className,
                         new VoidType());

        // check methods
        // TODO - this is broken - doesn't check for methods with duplicate names
        //
        // int foo(int x) { ... }
        // int foo(bool b) { ... }
        for (final MethodDef method : classDef.methods) {
            isWellTypedMethodDef(method,
                                 typeEnvironment,
                                 classDef.className);
        }
    }

    // program ::= classdef* stmt
    public void isWellTypedProgram() throws TypeErrorException {
        for (final ClassDef classDef : program.classes) {
            isWellTypedClassDef(classDef);
        }

        isWellTypedStmt(program.entryPoint,
                        new HashMap<Var, Type>(),
                        null,
                        null);
    }
}