package typechecker;

import typechecker.parser.*;
import parser.*;
import parser.Statement;

import java.util.List;
import java.util.Map;

import lexer.BooleanToken;

import java.util.HashMap;

public TypeChecker {
    public final Program program;
    // if the functions were overloaded:
    // public final Map<Signature, Fdef> functions;
    // public class Signature {
    //   public final FunctionName name;
    //   public final List<Type> params;
    // }
    //
    // if you had classes:
    // public final Map<ClassName, ClassInformation> classes;
    // public class ClassInformation {
    //   public final ClassDef cdef;
    //   public final Map<Signature, MethodDefinition> methods; // may include inherited methods
    // }
    public final Map<FunctionName, Fdef> functions;

    // Signature (usually): name, parameter types
    //
    // // same name, different signatures:
    // int foo(int x, bool y) { ... }
    // int foo(int x, int y) { ... }
    //
    // foo(5, true)
    // foo(int, bool)
    //
    public Typechecker(final Program program) throws TypeErrorException {
        this.program = program;
        functions = new HashMap<FunctionName, FDef>();
        for (final Fdef fdef : program.functions) {
            if (!functions.containsKey(fdef.fname)) {
                functions.put(fdef.fname, fdef);
            } else {
                throw new TypeErrorException("Function with duplicate name: " + fdef.fname);
            }
        }
    }

    public Fdef getFunctionByName(final FunctionName fname) throws TypeErrorException {
        final FDef fdef = functions.get(fname);
        if (fdef == null) {
            throw new TypeErrorException("No such function with name: " + fname);
        } else {
            return fdef;
        }
    }
    
    // int foo(int x, bool y) { ... }
    //
    // int x = foo(1, true);
    //
    // 1. Is foo a function?
    // 2. Does foo take an integer and a boolean? - (int, bool)
    // 3. Does foo return an integer?
    public static Type typeofFunctionCall(final FunctionCallExp exp,
                                          final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
        // what are functions?  Are they data?  Are they somehow special?
        final Fdef fdef = getFunctionByName(exp.fname);
        if (exp.params.size() != fdef.arguments.size()) {
            throw new TypeErrorException("Wrong number of arguments for function: " + fdef.fname);
        }
        for (int index = 0; index < exp.params.size(); index++) {
            final Type receivedArgumentType = typeof(exp.params.get(index), typeEnvironment);
            final Type expectedArgumentType = fdef.arguments.get(index).type;
            // doesn't handle subtyping right now
            //
            // void foo(Animal a) { ... }
            //
            // foo(new Dog())
            if (!receivedArgumentType.equals(expectedArgumentType)) {
                throw new TypeErrorException("Type mismatch on function call argument");
            }
        }
        return fdef.returnType;
    }
    
    // op ::= + | < | &&
    public Type typeofOp(final OpExp exp,
                         final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
        final Type leftType = typeof(exp.left, typeEnvironment);
        final Type rightType = typeof(exp.right, typeEnvironment);
        if (exp.op instanceof PlusOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new IntType();
            } else {
                throw new TypeErrorException("Incorrect types for +");
            }
        } else if (exp.op instanceof LessThanOp) {
            if (leftType instanceof IntType && rightType instanceof IntType) {
                return new BoolType();
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
    public Type typeof(final Exp exp,
                       final Map<Variable, Type> typeEnvironment) throws TypeErrorException {
        if (exp instanceof BooleanLiteralExp) {
            return new BoolType();
        } else if (exp instanceof IntegerLiteralExp) {
            return new IntType();
        } else if (exp instanceof VariableExp) {
            // needed: some way to track variables in scope,
            //         including the types they were declared as
            // int x = ...; // need to remeber that x is an int
            final Variable variable = ((VariableExp)exp).variable;
            final Type variableType = typeEnvironment.get(variable);
            // get returns null if the key isn't in the map
            if (variableType == null) {
                throw new TypeErrorException("variable not in scope: " + variable);
            } else {
                return variableType;
            }
        } else if (exp instanceof OpExp) {
            return typeofOpExp((OpExp)exp, typeEnvironment);
        } else if (exp instanceof FunctionCallExp) {
            return typeofFunctionCall((FunctionCallExp)exp, typeEnvironment);
        } else {
            throw new TypeErrorException("Unsupported expresssion: " + exp);
        }
    }

    // addToMap: O(n) - to add one key/value pair
    // with immutable data structures: O(log(n))
    public static Map<Variable, Type> addToMap(final Map<Variable, Type> typeEnvironment,
                                               final Variable key,
                                               final Type value) {
        final Map<Variable, Type> retval = new HashMap<Variable, Type>();
        retval.putAll(typeEnvironment);
        retval.put(key, value);
        return retval;
    }

    public Map<Variable, Type> typecheckVardec(final VardecStmt asDec,
                                               final Map<Variable, Type> typeEnvironment,
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

    public Map<Variable, Type> typecheckIf(final IfStmt asIf,
                                           final Map<Variable, Type> typeEnvironment,
                                           final Type returnType) throws TypeErrorException {
        final IfStmt asIf = (IfStmt)stmt;
        final Type receivedType = typeof(asIf.guard, typeEnvironment);
        if (receivedType.equals(new BoolType())) {
            // if (...) {
            //   int x = 17;
            // } else {
            //   int y = true;
            // }
            typecheckStmt(asIf.trueBranch, typeEnvironment, returnType);
            typecheckStmt(asIf.falseBranch, typeEnvironment, returnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard should be bool; received: " + receivedType);
        }
    }

    public Map<Variable, Type> typecheckWhile(final WhileStmt asWhile,
                                              final Map<Variable, Type> typeEnvironment,
                                              final Type returnType) throws TypeErrorException {
        // while (...) { ... }
        final Type receivedType = typeof(asWhile.guard, typeEnvironment);
        if (receivedType.equals(new BoolType())) {
            typecheckStmt(asWhile.body, typeEnvironment, returnType);
            return typeEnvironment;
        } else {
            throw new TypeErrorException("guard should be bool; received: " + receivedType);
        }
    }

    public Map<Variable, Type> typecheckReturn(final ReturnStmt asReturn,
                                               final Map<Variable, Type> typeEnvironment,
                                               final Type returnType) throws TypeErrorException {
        final ReturnStmt asReturn = (ReturnStmt)stmt;
        final Type receivedType = typeof(asReturn.exp, typeEnvironment);
        if (returnType.equals(receivedType)) {
            return typeEnvironment;
        } else {
            throw new TypeErrorException("expected return type: " + returnType + ", received: " + receivedType);
        }
    }
    
    public Map<Variable, Type> typecheckBlock(final BlockStmt asBlock,
                                              final Map<Variable, Type> originalTypeEnvironment,
                                              final Type returnType) throws TypeErrorException {
        Map<Variable, Type> typeEnvironment = originalTypeEnvironment;
        // {
        //   int x = 17;
        //   int y = x + x;
        //   if (...) { return y; } else { ... } // maybe returns
        // }
        for (final Stmt stmt : asBlock.body) {
            typeEnvironment = typecheckStmt(stmt, typeEnvironment, returnType);
        }

        return originalTypeEnvironment;
    }
    
    // int x = 7;
    // while (...) {
    //   bool x = true; // remember: x is an integer
    //   // only the boolean available here
    //   // remember: reinstate x as an integer
    // }
    // // only the integer available here
    public Map<Variable, Type> typecheckStmt(final Stmt stmt,
                                             final Map<Variable, Type> typeEnvironment,
                                             final Type returnType) throws TypeErrorException {
        if (stmt instanceof VardecStmt) {
            // vardec = exp;
            // int x = 17; // initialized type should be compatible with provided type
            return typecheckVardec((VardecStmt)stmt, typeEnvironment, returnType);
        } else if (stmt instanceof IfStmt) {
            // if (exp) stmt else stmt
            // exp: bool
            //   possible other check: returning
            return typecheckIf((IfStmt)stmt, typeEnvironment, returnType);
        } else if (stmt instanceof WhileStmt) {
            return typecheckWhile((WhileStmt)stmt, typeEnvironment, returnType);
        } else if (stmt instanceof ReturnStmt) {
            // return exp;
            return typecheckReturn((ReturnStmt)stmt, typeEnvironment, returnType);
        } else if (stmt instanceof BlockStmt) {
            return typecheckBlock((BlockStmt)stmt, typeEnvironment, returnType);
        } else {
            throw new TypeErrorException("Unsupported statement: " + stmt);
        }
    }

    // fdef ::= type fname(vardec*) stmt
    public void typecheckFunction(final Fdef fdef) throws TypeErrorException {
        final Map<Variable, Type> typeEnvironment = new HashMap<Variable, Type>();
        for (final Vardec vardec : fdef.arguments) {
            // int foo(int x, bool x)
            if (!typeEnvironment.containsKey(vardec.variable)) {
                throw new TypeErrorException("Duplicate variable name: " + vardec.variable);
            } else {
                typeEnvironment.put(vardec.variable, vardec.type);
            }
        }
        
        typecheckStmt(fdef.body, typeEnvironment, fdef.returnType);
    }

    public void typecheckWholeProgram() throws TypeErrorException {
        for (final Fdef fdef : program.functions) {
            typecheckFunction(fdef);
        }
    }

}
