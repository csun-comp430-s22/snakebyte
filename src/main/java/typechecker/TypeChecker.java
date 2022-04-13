package typechecker;

import typechecker.parser.*;
import parser.*;
import parser.Statement;

import java.util.List;
import java.util.Map;

import lexer.BooleanToken;

import java.util.HashMap;

public class TypeChecker {

    public final Program program;
    // if the functions were overloaded:
    // public final Map<Signature, Fdef> functions;
    // public class Signature {
    // public final FunctionName name;
    // public final List<Type> params;
    // }
    //
    // if you had classes:
    // public final Map<ClassName, ClassInformation> classes;
    // public class ClassInformation {
    // public final ClassDef cdef;
    // public final Map<Signature, MethodDefinition> methods; // may include
    // inherited methods
    // }
    // public final Map<FunctionName, Fdef> functions;

    // Signature (usually): name, parameter types
    //
    // // same name, different signatures:
    // int foo(int x, bool y) { ... }
    // int foo(int x, int y) { ... }
    //
    // foo(5, true)
    // foo(int, bool)
    //

    // *************************************************************************************************************code
    // below for function
    // defs************************************************************* */

    /*
     * public TypeChecker(final Program program) throws TypeErrorException {
     * this.program = program;
     * functions = new HashMap<FunctionName, FDef>();
     * for (final Fdef fdef : program.functions) {
     * if (!functions.containsKey(fdef.fname)) {
     * functions.put(fdef.fname, fdef);
     * } else {
     * throw new TypeErrorException("Function with duplicate name: " + fdef.fname);
     * }
     * }
     * }
     * 
     * public Fdef getFunctionByName(final FunctionName fname) throws
     * TypeErrorException {
     * final FDef fdef = functions.get(fname);
     * if (fdef == null) {
     * throw new TypeErrorException("No such function with name: " + fname);
     * } else {
     * return fdef;
     * }
     * }
     */

    // int foo(int x, bool y) { ... }
    //
    // int x = foo(1, true);
    //
    // 1. Is foo a function?
    // 2. Does foo take an integer and a boolean? - (int, bool)
    // 3. Does foo return an integer?

    /***************************************************************************************************************************
     * functioncallexpressions
     * below**********************************************************
     */
    /*
     * public static Type typeofFunctionCall(final FunctionCallExp exp,
     * final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
     * // what are functions? Are they data? Are they somehow special?
     * final Fdef fdef = getFunctionByName(exp.fname);
     * if (exp.params.size() != fdef.arguments.size()) {
     * throw new TypeErrorException("Wrong number of arguments for function: " +
     * fdef.fname);
     * }
     * for (int index = 0; index < exp.params.size(); index++) {
     * final Type receivedArgumentType = typeof(exp.params.get(index),
     * typeEnvironment);
     * final Type expectedArgumentType = fdef.arguments.get(index).type;
     * // doesn't handle subtyping right now
     * //
     * // void foo(Animal a) { ... }
     * //
     * // foo(new Dog())
     * if (!receivedArgumentType.equals(expectedArgumentType)) {
     * throw new TypeErrorException("Type mismatch on function call argument");
     * }
     * }
     * return fdef.returnType;
     * }
     */
    // op ::= + | < | &&
    public Type typeofOp(final OpExp exp,
            final Map<VarToken, Type> typeEnvironment) throws TypeErrorException {
        final Type leftType = typeof(exp.left, typeEnvironment);
        final Type rightType = typeof(exp.right, typeEnvironment);
        if (exp.op instanceof PlusOP) {
            if (leftType instanceof IntegerToken && rightType instanceof IntegerToken) {
                return new IntegerToken();
            } else {
                throw new TypeErrorException("Incorrect types for +");
            }
        } else if (exp.op instanceof LessThanOp) {
            if (leftType instanceof IntegerToken && rightType instanceof IntegerToken) {
                return new parser.BooleanToken();
            } else {
                throw new TypeErrorException("Incorrect types for <");
            }
        } else if (exp.op instanceof AndOp) {
            if (leftType instanceof BoolType && rightType instanceof BoolType) {
                return new BoolType();
            } else {
                throw new TypeErrorException("Incorrect types for &&");
            }
        } else {
            throw new TypeErrorException("Unsupported operation: " + exp.op);
        }
    }

    // type environment: Variable -> Type
    public Type typeof(final Expression exp,
            final Map<VarToken, Type> typeEnvironment) throws TypeErrorException {
        if (exp instanceof BooleanValue) {
            return new BooleanToken();
        } else if (exp instanceof IntegerValue) {
            return new IntegerToken();
        } else if (exp instanceof VarExp) {
            // needed: some way to track variables in scope,
            // including the types they were declared as
            // int x = ...; // need to remeber that x is an int
            final VarToken variable = ((VarExp) exp).variable;
            final Type variableType = typeEnvironment.get(variable);
            // get returns null if the key isn't in the map
            if (variableType == null) {
                throw new TypeErrorException("variable not in scope: " + variable);
            } else {
                return variableType;
            }
        } else if (exp instanceof OPExp) {
            return typeofOpExp((OPExp) exp, typeEnvironment);
        } /*
           * else if (exp instanceof FunctionCallExp) {
           * return typeofFunctionCall((FunctionCallExp)exp, typeEnvironment);
           * }
           */ else {
            throw new TypeErrorException("Unsupported expresssion: " + exp);
        }
    }

    // addToMap: O(n) - to add one key/value pair
    // with immutable data structures: O(log(n))
    public static Map<VarToken, Type> addToMap(final Map<VarToken, Type> typeEnvironment,
            final VarToken key,
            final Type value) {
        final Map<VarToken, Type> retval = new HashMap<VarToken, Type>();
        retval.putAll(typeEnvironment);
        retval.put(key, value);
        return retval;
    }

    public Map<VarToken, Type> typecheckVardec(final VarDecStatement asDec,
            final Map<VarToken, Type> typeEnvironment,
            final Type returnType) throws TypeErrorException {
        final Type expectedType = asDec.vardec.type;
        final Type receivedType = typeof(asDec.exp, typeEnvironment);
        // Animal a = new Dog();
        if (receivedType.equals(expectedType)) {
            // if it were mutable
            // typeEnvironment.put(asDec.vardec.variable, asDec.vardec.type);
            return addToMap(typeEnvironment, asDec.vardec.variable, expectedType);
        } else {
            throw new TypeErrorException("expected: " + expectedType + ", received: " + receivedType);
        }
    }

    public Map<VarToken, Type> typecheckIf(final IfStatement asIf,
            final Map<VarToken, Type> typeEnvironment,
            final Type returnType) throws TypeErrorException {
        asIf = (IfStatement) asIf;
        final Type receivedType = typeof(asIf.guard, typeEnvironment);
        if (receivedType.equals(new BooleanToken())) {
            // if (...) {
            // int x = 17;
            // } else {
            // int y = true;
            // }
            typecheckStmt(asIf.trueBranch, typeEnvironment, returnType);
            typecheckStmt(asIf.falseBranch, typeEnvironment, returnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard should be bool; received: " + receivedType);
        }
    }

    public Map<VarToken, Type> typecheckWhile(final WhileStatement asWhile,
            final Map<VarToken, Type> typeEnvironment,
            final Type returnType) throws TypeErrorException {
        // while (...) { ... }
        final Type receivedType = typeof(asWhile.guard, typeEnvironment);
        if (receivedType.equals(new BooleanToken())) {
            typecheckStmt(asWhile.trueBranch, typeEnvironment, returnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard should be bool; received: " + receivedType);
        }
    }

    /****************************************************************************************************************
     * return statement
     * below*********************************************************************************
     */
    /*
     * public Map<VarToken, Type> typecheckReturn(final ReturnStmt asReturn,
     * final Map<VarToken, Type> typeEnvironment,
     * final Type returnType) throws TypeErrorException {
     * final ReturnStmt asReturn = (ReturnStmt)stmt;
     * final Type receivedType = typeof(asReturn.exp, typeEnvironment);
     * if (returnType.equals(receivedType)) {
     * return typeEnvironment;
     * } else {
     * throw new TypeErrorException("expected return type: " + returnType +
     * ", received: " + receivedType);
     * }
     * }
     */
    public Map<VarToken, Type> typecheckBlock(final BlockStatement asBlock,
            final Map<VarToken, Type> originalTypeEnvironment,
            final Type returnType) throws TypeErrorException {
        Map<VarToken, Type> typeEnvironment = originalTypeEnvironment;
        // {
        // int x = 17;
        // int y = x + x;
        // if (...) { return y; } else { ... } // maybe returns
        // }
        for (final Statement stmt : asBlock.stmts) {
            typeEnvironment = typecheckStmt(stmt, typeEnvironment, returnType);
        }

        return originalTypeEnvironment;
    }

    // int x = 7;
    // while (...) {
    // bool x = true; // remember: x is an integer
    // // only the boolean available here
    // // remember: reinstate x as an integer
    // }
    // // only the integer available here
    public Map<VarToken, Type> typecheckStmt(final Statement stmt,
            final Map<VarToken, Type> typeEnvironment,
            final Type returnType) throws TypeErrorException {
        if (stmt instanceof VarDecStatement) {
            // vardec = exp;
            // int x = 17; // initialized type should be compatible with provided type
            return typecheckVardec((VarDecStatement) stmt, typeEnvironment, returnType);
        } else if (stmt instanceof IfStatement) {
            // if (exp) stmt else stmt
            // exp: bool
            // possible other check: returning
            return typecheckIf((IfStatement) stmt, typeEnvironment, returnType);
        } else if (stmt instanceof WhileStatement) {
            return typecheckWhile((WhileStatement) stmt, typeEnvironment, returnType);
        } /*
           * else if (stmt instanceof ReturnStmt) {
           * // return exp;
           * return typecheckReturn((ReturnStmt)stmt, typeEnvironment, returnType);
           * }
           */ else if (stmt instanceof BlockStatement) {
            return typecheckBlock((BlockStatement) stmt, typeEnvironment, returnType);
        } else {
            throw new TypeErrorException("Unsupported statement: " + stmt);
        }
    }

    /***********************************************************************************************
     * typecheck function
     * below******************************************************
     */
    // fdef ::= type fname(vardec*) stmt
    /*
     * public void typecheckFunction(final Fdef fdef) throws TypeErrorException {
     * final Map<VarToken, Type> typeEnvironment = new HashMap<VarToken, Type>();
     * for (final Vardec vardec : fdef.arguments) {
     * // int foo(int x, bool x)
     * if (!typeEnvironment.containsKey(vardec.variable)) {
     * throw new TypeErrorException("Duplicate variable name: " + vardec.variable);
     * } else {
     * typeEnvironment.put(vardec.variable, vardec.type);
     * }
     * }
     * 
     * typecheckStmt(fdef.body, typeEnvironment, fdef.returnType);
     * }
     * 
     * public void typecheckWholeProgram() throws TypeErrorException {
     * for (final Fdef fdef : program.functions) {
     * typecheckFunction(fdef);
     * }
     * }
     * 
     * 
     * 
     */

}
