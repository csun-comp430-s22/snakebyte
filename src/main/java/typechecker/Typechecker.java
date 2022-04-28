package typechecker;

import typechecker.parser.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
// typechecks: well-typed: no type errors
// doesn't typecheck: ill-typed: some number of type errors (>0)

public class Typechecker {
    public static final String BASE_CLASS_NAME = "Object";
    // public final List<ClassDef> classes;
    // public final Program program;
    public final Map<ClassName, ClassDef> classes;

    // includes inherited methods
    public final Map<ClassName, Map<MethodName, MethodDef>> methods;

    public final Program program;

    public Typechecker(final Program program) throws TypeErrorException {
        this.program = program;
        classes = makeClassMap(program.classes);
        methods = makeMethodMap(classes);
    }

    public static ClassDef getClass(final ClassName className,
            final Map<ClassName, ClassDef> classes) throws TypeErrorException {
        if (className.name.equals(BASE_CLASS_NAME)) {
            return null;
        } else {
            final ClassDef classDef = classes.get(className);
            if (classDef == null) {
                throw new TypeErrorException("no such class: " + className);
            } else {
                return classDef;
            }
        }
    }

    public ClassDef getClass(final ClassName className) throws TypeErrorException {
        return getClass(className, classes);
    }

    public static ClassDef getParent(final ClassName className,
            final Map<ClassName, ClassDef> classes) throws TypeErrorException {
        final ClassDef classDef = getClass(className, classes);
        return getClass(classDef.extendsClassName, classes);
    }

    public ClassDef getParent(final ClassName className) throws TypeErrorException {
        return getParent(className, classes);
    }

    public static void assertInheritanceNonCyclicalForClass(final ClassDef classDef,
            final Map<ClassName, ClassDef> classes) throws TypeErrorException {
        final Set<ClassName> seenClasses = new HashSet<ClassName>();
        seenClasses.add(classDef.className);
        ClassDef parentClassDef = getParent(classDef.className, classes);
        while (parentClassDef != null) {
            final ClassName parentClassName = parentClassDef.className;
            if (seenClasses.contains(parentClassName)) {
                throw new TypeErrorException("cyclic inheritance involving: " + parentClassName);
            }
            seenClasses.add(parentClassName);
            parentClassDef = getParent(parentClassName, classes);
        }
    }

    public static void assertInheritanceNonCyclical(final Map<ClassName, ClassDef> classes) throws TypeErrorException {
        for (final ClassDef classDef : classes.values()) {
            assertInheritanceNonCyclicalForClass(classDef, classes);
        }
    }

    // includes inherited methods
    // duplicates are not permitted within the same class, but it's ok to override a
    // superclass' method
    public static Map<MethodName, MethodDef> methodsForClass(final ClassName className,
            final Map<ClassName, ClassDef> classes) throws TypeErrorException {
        final ClassDef classDef = getClass(className, classes);
        if (classDef == null) {
            return new HashMap<MethodName, MethodDef>();
        } else {
            final Map<MethodName, MethodDef> retval = methodsForClass(classDef.extendsClassName, classes);
            final Set<MethodName> methodsOnThisClass = new HashSet<MethodName>();
            for (final MethodDef methodDef : classDef.methods) {
                final MethodName methodName = methodDef.methodName;
                if (methodsOnThisClass.contains(methodName)) {
                    throw new TypeErrorException("duplicate method: " + methodName);
                }
                methodsOnThisClass.add(methodName);
                retval.put(methodName, methodDef);
            }
            return retval;
        }
    }

    public static Map<ClassName, Map<MethodName, MethodDef>> makeMethodMap(final Map<ClassName, ClassDef> classes)
            throws TypeErrorException {
        final Map<ClassName, Map<MethodName, MethodDef>> retval = new HashMap<ClassName, Map<MethodName, MethodDef>>();
        for (final ClassName className : classes.keySet()) {
            retval.put(className, methodsForClass(className, classes));
        }
        return retval;
    }

    // also makes sure inheritance hierarchies aren't cyclical
    public static Map<ClassName, ClassDef> makeClassMap(final List<ClassDef> classes) throws TypeErrorException {
        final Map<ClassName, ClassDef> retval = new HashMap<ClassName, ClassDef>();
        for (final ClassDef classDef : classes) {
            final ClassName className = classDef.className;
            if (retval.containsKey(classDef.className)) {
                throw new TypeErrorException("Duplicate class name: " + className);
            }
            System.out.println(classes.get(0));

            System.out.println("Made it here!!!");
            retval.put(className, classes.get(0));
            System.out.println(retval);
        }

        assertInheritanceNonCyclical(retval);

        return retval;
    }

    public MethodDef getMethodDef(final ClassName className,
            final MethodName methodName) throws TypeErrorException {
        final Map<MethodName, MethodDef> methodMap = methods.get(className);
        if (methodMap == null) {
            throw new TypeErrorException("Unknown class name: " + className);
        } else {
            final MethodDef methodDef = methodMap.get(methodName);
            if (methodDef == null) {
                throw new TypeErrorException("Unknown method name " + methodName + " for class " + className);
            } else {
                return methodDef;
            }
        }
    }
    // *******************************Old code************ */

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


        /********************************************************arithmetic types************************************************* */
        if (exp.operator instanceof PlusOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType();
            } else {
                throw new TypeErrorException("Operand type mismatch for +");
            }
        }
        else if (exp.operator instanceof MinusOP) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                 return new IntType();
            } else {
                    throw new TypeErrorException("Operand type mismatch for -");
            }
        else if (exp.operator instanceof TimesOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                 return new IntType();
            } else {
                    throw new TypeErrorException("Operand type mismatch for *");
            }
        else if (exp.operator instanceof DivideOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                 return new IntType();
            } else {
                    throw new TypeErrorException("Operand type mismatch for /");
            }

        /*****************************************************************comparitor types *****************************************/
        } else if (exp.operator instanceof LessThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for <");
            }
        else if (exp.operator instanceof LessThanEqualOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for <=");
            }
        else if (exp.operator instanceof GreaterThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for >");
            }
        else if (exp.operator instanceof GreaterThanEqualOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Operand type mismatch for >=");
            }
        } else if (exp.operator instanceof EqualsEqualsOp) {
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
            final MethodName methodName) throws TypeErrorException {
        return getMethodDef(className, methodName).returnType;
    }

    public List<Type> expectedParameterTypesForClassAndMethod(final ClassName className,
            final MethodName methodName)
            throws TypeErrorException {
        final MethodDef methodDef = getMethodDef(className, methodName);
        final List<Type> retval = new ArrayList<Type>();
        for (final VarDec vardec : methodDef.arguments) {
            retval.add(vardec.type);
        }
        return retval;
    }

    public void assertEqualOrSubtypeOf(final Type first, final Type second) throws TypeErrorException {
        if (first.equals(second)) {
            return;
        } else if (first instanceof ClassNameType &&
                second instanceof ClassNameType) {
            final ClassDef parentClassDef = getParent(((ClassNameType) first).className);
            assertEqualOrSubtypeOf(new ClassNameType(parentClassDef.className), second);
        } else {
            throw new TypeErrorException("incompatible types: " + first + ", " + second);
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
            // myMethod( 2, true, 3)
            //
            // myMethod2(BaseClass)
            // myMethod2(new SubClass())
            assertEqualOrSubtypeOf(paramType, expectedType);
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
            final ClassName className = ((ClassNameType) targetType).className;
            final List<Type> expectedTypes = expectedParameterTypesForClassAndMethod(className, exp.methodName);
            expressionsOk(expectedTypes, exp.params, typeEnvironment, classWeAreIn);
            return expectedReturnTypeForClassAndMethod(className, exp.methodName);
        } else {
            throw new TypeErrorException("Called method on non-class type: " + targetType);
        }
    }

    public List<Type> expectedConstructorTypesForClass(final ClassName className)
            throws TypeErrorException {
        final ClassDef classDef = getClass(className);
        final List<Type> retval = new ArrayList<Type>();
        if (classDef == null) { // Object
            return retval;
        } else {
            for (final VarDec vardec : classDef.constructorArguments) {
                retval.add(vardec.type);
            }
            return retval;
        }
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

    // classWeAreIn is null if we are in the entry pointm
    public Type typeof(final Expression exp,
            final Map<Var, Type> typeEnvironment,
            final ClassName classWeAreIn) throws TypeErrorException {
        if (exp instanceof IntLiteralExp) {
            return new IntType();
        } else if (exp instanceof VarExp) {
            return typeofVariable((VarExp) exp, typeEnvironment);
        } else if (exp instanceof BoolLiteralExp) {
            return new BoolType();
        } else if (exp instanceof ThisExp) {
            return typeofThis(classWeAreIn);
        } else if (exp instanceof OpExp) {
            return typeofOp((OpExp) exp, typeEnvironment, classWeAreIn);
        } else if (exp instanceof MethodCallExp) {
            return typeofMethodCall((MethodCallExp) exp, typeEnvironment, classWeAreIn);
        } else if (exp instanceof NewExp) {
            return typeofNew((NewExp) exp, typeEnvironment, classWeAreIn);
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
        assertEqualOrSubtypeOf(expType, stmt.vardec.type);
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
            assertEqualOrSubtypeOf(receivedType, functionReturnType);
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
    // int x = 17;
    // break;
    // }
    public Map<Var, Type> isWellTypedStmt(final Statement stmt,
            final Map<Var, Type> typeEnvironment,
            final ClassName classWeAreIn,
            final Type functionReturnType) throws TypeErrorException {
        if (stmt instanceof ExpStmt) {
            // it was:
            // typeof(((ExpStmt)stmt).expression, typeEnvironment, classWeAreIn,
            // functionReturnType);
            typeof(((ExpStmt) stmt).expression, typeEnvironment, classWeAreIn);
            return typeEnvironment;
        } else if (stmt instanceof VariableInitializationStmt) {
            // it was:
            // return isWellTypedVar((VariableInitializationStmt)stmt, typeEnvironment,
            // classWeAreIn, functionReturnType);
            return isWellTypedVar((VariableInitializationStmt) stmt, typeEnvironment, classWeAreIn);
        } else if (stmt instanceof IfStatement) {
            return isWellTypedIf((IfStatement) stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof WhileStmt) {
            return isWellTypedWhile((WhileStmt) stmt, typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof ReturnNonVoidStmt) {
            return isWellTypedReturnNonVoid((ReturnNonVoidStmt) stmt, typeEnvironment, classWeAreIn,
                    functionReturnType);
        } else if (stmt instanceof ReturnVoidStmt) {
            return isWellTypedReturnVoid(typeEnvironment, classWeAreIn, functionReturnType);
        } else if (stmt instanceof PrintlnStmt) {
            // it was:
            // typeof(((PrintlnStmt)stmt).expression, typeEnvironment, classWeAreIn,
            // functionReturnType);
            typeof(((PrintlnStmt) stmt).expression, typeEnvironment, classWeAreIn);
            return typeEnvironment;
        } else if (stmt instanceof BlockStmt) {
            return isWellTypedBlock((BlockStmt) stmt, typeEnvironment, classWeAreIn, functionReturnType);
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

    public Map<Var, Type> baseTypeEnvironmentForClass(final ClassName className) throws TypeErrorException {
        final ClassDef classDef = getClass(className);
        if (classDef == null) {
            return new HashMap<Var, Type>();
        } else {
            final Map<Var, Type> retval = baseTypeEnvironmentForClass(classDef.extendsClassName);
            for (final VarDec instanceVariable : classDef.instanceVariables) {
                final Var variable = instanceVariable.var;
                if (retval.containsKey(variable)) {
                    throw new TypeErrorException("Duplicate instance variable (possibly inherited): " + variable);
                }
                retval.put(variable, instanceVariable.type);
            }
            return retval;
        }
    }

    public void isWellTypedClassDef(final ClassDef classDef) throws TypeErrorException {

        Map<Var, Type> typeEnvironment = baseTypeEnvironmentForClass(classDef.className);
        Map<Var, Type> constructorTypeEnvironment = typeEnvironment;
        final Set<Var> variablesInConstructor = new HashSet<Var>();
        for (final VarDec vardec : classDef.constructorArguments) {
            final Var variable = vardec.var;
            if (variablesInConstructor.contains(variable)) {
                throw new TypeErrorException("Duplicate variable in constructor param: " + variable);
            }
            variablesInConstructor.add(variable);
            constructorTypeEnvironment = addToMap(constructorTypeEnvironment, variable, vardec.type);
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