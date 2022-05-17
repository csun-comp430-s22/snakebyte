package codegenerator.parser;

import java.util.ArrayList;
import java.util.List;

import codegenerator.lexer.AssignToken;
import codegenerator.lexer.ConstructorToken;
import codegenerator.lexer.DotToken;
import codegenerator.lexer.IntegerTokenCodeGen;
import codegenerator.lexer.PrintlnToken;
import codegenerator.lexer.SemicolonToken;
import lexer.*;
import parser.ParseException;
import parser.ParseResult;
import typechecker.parser.BoolType;
import typechecker.parser.ClassDef;
import typechecker.parser.ClassName;
import typechecker.parser.ClassNameType;
import typechecker.parser.EqualsOp;
import typechecker.parser.ExpStmt;
import typechecker.parser.Expression;
import typechecker.parser.IfStatement;
import typechecker.parser.IntType;
import typechecker.parser.LessThanOp;
import typechecker.parser.MethodCallExp;
import typechecker.parser.MethodDef;
import typechecker.parser.MethodName;
import typechecker.parser.NewExp;
import typechecker.parser.OpExp;
import typechecker.parser.PlusOp;
import typechecker.parser.PrintlnStmt;
import typechecker.parser.Program;
import typechecker.parser.ReturnNonVoidStmt;
import typechecker.parser.ReturnVoidStmt;
import typechecker.parser.Statement;
import typechecker.parser.ThisExp;
import typechecker.parser.Type;
import typechecker.parser.Var;
import typechecker.parser.VarDec;
import typechecker.parser.VoidType;
import typechecker.parser.WhileStmt;
import typechecker.parser.IntLiteralExp;
import typechecker.parser.VarExp;
import typechecker.parser.VariableInitializationStmt;
import typechecker.parser.BlockStmt;
import typechecker.parser.BoolLiteralExp;
public class PaserCodeGen {
    public final Token[] tokens;
    //constructor
    public PaserCodeGen(Token[] tokens) {
        this.tokens = tokens;
    }
    //getter
    public Token getToken(final int position) throws ParseException{
        if(position < 0 || position >= tokens.length) {
            throw new ParseException("Invalid position: " + position);
        }else {
            return tokens[position];
        }
    }
    public void assertTokenHereIs(final int position, final Token expectedToken) throws ParseException {
        final Token recievedToken = getToken(position);
        if( !recievedToken.equals(expectedToken) ) {
            throw new ParseException("Expected token: " + expectedToken + " but was: " + recievedToken);
        }
    }
    public ParseResult<ClassName> parseClassName(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IdentifierToken) {
            final String name = ((IdentifierToken)token).identifier;
            return new ParseResult<ClassName>(new ClassName(name), position + 1);
        } else {
            throw new ParseException("Expected class name; received: " + token);
        }
    }
    public ParseResult<MethodName> parseMethodName(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IdentifierToken) {
            final String name = ((IdentifierToken)token).identifier;
            return new ParseResult<MethodName>(new MethodName(name), position + 1);
        } else {
            throw new ParseException("Expected method name; received: " + token);
        }
    }
    public ParseResult<Var> parseVariable(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IdentifierToken) {
            final String name = ((IdentifierToken)token).identifier;
            return new ParseResult<Var>(new Var(name), position + 1);
        } else {
            throw new ParseException("Expected variable name; received: " + token);
        }
    }
    //type ::= int | bool | void | classname
    //probably need to add more type
    public ParseResult<Type> parseType(int position) throws ParseException {
        final Token token = getToken(position);
        Type type = null;
        if (token instanceof IntType) {
            type = new IntType();
            position++;
        } else if (token instanceof BoolType) {
            type = new BoolType();
            position++;
        } else if (token instanceof VoidType) {
            type = new VoidType();
            position++;
        } else {
            final ParseResult<ClassName> className = parseClassName(position);
            position = className.position;
            type = new ClassNameType(className.result);
        }

        return new ParseResult<Type>(type, position);
    }
    //need modifier this part
    //primary_exp ::= i | x | true | false | this | `(` exp `)` | new classname `(` comma_exp `)`
    public ParseResult<Expression> parsePrimaryExp(int position) throws ParseException {
        final Token token = getToken(position++);
        Expression exp = null;
        if (token instanceof IntegerTokenCodeGen) {
            exp = new IntLiteralExp(((IntegerTokenCodeGen)token).value);
        } else if (token instanceof IdentifierToken) {
            exp = new VarExp(new Var(((IdentifierToken)token).identifier));
        } else if (token instanceof TrueToken) {
            exp = new BoolLiteralExp(true);
        } else if (token instanceof FalseToken) {
            exp = new BoolLiteralExp(false);
        } else if (token instanceof ThisToken) {
            exp = new ThisExp();
        } else if (token instanceof LeftParenToken) {
            final ParseResult<Expression> nested = parseExp(position);
            assertTokenHereIs(nested.position, new RightParenToken());
            exp = nested.result;
            position = nested.position + 1;
        } else if (token instanceof NewToken) {
            final ParseResult<ClassName> className = parseClassName(position);
            assertTokenHereIs(className.position, new LeftParenToken());
            final ParseResult<List<Expression>> params = parseCommaExp(className.position + 1);
            position = params.position;
            assertTokenHereIs(position++, new RightParenToken());
            exp = new NewExp(className.result, params.result);
        } else {
            throw new ParseException("Expected primary expression; received: " + token +
                                          " at position: " + position);
        }

        return new ParseResult<Expression>(exp, position);
    }
    // dot_exp ::= primary_exp (`.` methodname `(` comma_exp `)`)*
    public ParseResult<Expression> parseDotExp(final int position) throws ParseException {
        ParseResult<Expression> retval = parsePrimaryExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(retval.position, new DotToken());
                final ParseResult<MethodName> methodName = parseMethodName(retval.position + 1);
                assertTokenHereIs(methodName.position, new LeftParenToken());
                final ParseResult<List<Expression>> params = parseCommaExp(methodName.position + 1);
                assertTokenHereIs(params.position, new RightParenToken());
                retval = new ParseResult<Expression>(new MethodCallExp(retval.result,
                                                                methodName.result,
                                                                params.result),
                                              params.position + 1);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return retval;
    }
     // additive_exp ::= dot_exp (`+` dot_exp)*
     public ParseResult<Expression> parseAdditiveExp(final int position) throws ParseException {
        ParseResult<Expression> retval = parseDotExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(retval.position, new PlusToken());
                final ParseResult<Expression> right = parseDotExp(retval.position + 1);
                retval = new ParseResult<Expression>(new OpExp(retval.result,
                                                        new PlusOp(),
                                                        right.result),
                                              right.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return retval;
    }//end of parseAdditiveExp method
     // less_than_exp ::= additive_exp (`<` additive_exp)*
     public ParseResult<Expression> parseLessThanExp(final int position) throws ParseException {
        ParseResult<Expression> retval = parseAdditiveExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(retval.position, new LessThanToken());
                final ParseResult<Expression> right = parseAdditiveExp(retval.position + 1);
                retval = new ParseResult<Expression>(new OpExp(retval.result,
                                                        new LessThanOp(),
                                                        right.result),
                                              right.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return retval;
    }
    // equals_exp ::= less_than_exp (`==` less_than_exp)*
    public ParseResult<Expression> parseEqualsExp(final int position) throws ParseException {
        ParseResult<Expression> retval = parseLessThanExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(retval.position, new EqualsToken());
                final ParseResult<Expression> right = parseLessThanExp(retval.position + 1);
                retval = new ParseResult<Expression>(new OpExp(retval.result,
                                                        new EqualsOp(),
                                                        right.result),
                                              right.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return retval;
    }
    // comma_exp ::= [equals_exp (`,` equals_exp)*]
    public ParseResult<List<Expression>> parseCommaExp(int position) throws ParseException {
        final List<Expression> exps = new ArrayList<Expression>();

        try {
            ParseResult<Expression> currentExp = parseExp(position);
            exps.add(currentExp.result);
            position = currentExp.position;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    assertTokenHereIs(position, new CommaToken());
                    currentExp = parseExp(currentExp.position + 1);
                    exps.add(currentExp.result);
                    position = currentExp.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }
        } catch (final ParseException e) {}

        return new ParseResult<List<Expression>>(exps, position);
    }
    // exp ::= comma_exp
    public ParseResult<Expression> parseExp(final int position) throws ParseException {
        return parseEqualsExp(position);
    }

    // vardec ::= type x
    public ParseResult<VarDec> parseVardec(final int position) throws ParseException {
        final ParseResult<Type> type = parseType(position);
        final ParseResult<Var> variable = parseVariable(type.position);
        return new ParseResult<VarDec>(new VarDec(type.result, variable.result),
                                       variable.position);
    }
    public ParseResult<List<Statement>> parseStmts(int position) throws ParseException {
        final List<Statement> stmts = new ArrayList<Statement>();
        boolean shouldRun = true;
        while (shouldRun) {
            try {
                final ParseResult<Statement> stmt = parseStmt(position);
                stmts.add(stmt.result);
                position = stmt.position;
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return new ParseResult<List<Statement>>(stmts, position);
    }
    
    // stmt ::=
    //      if (exp) stmt else stmt |
    //      while (exp) stmt |
    //      return exp; |
    //      return; |
    //      println(exp); |
    //      { stmt* } |
    //      vardec = exp; |
    //      exp;
    // try expression statements last, as we don't know we (should) have one until we
    // finish parsing it.  Similar situation for vardecs
    public ParseResult<Statement> parseStmt(final int position) throws ParseException {
        
        final Token token = getToken(position);
        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Statement> ifTrue = parseStmt(guard.position + 1);
            assertTokenHereIs(ifTrue.position, new ElseToken());
            final ParseResult<Statement> ifFalse = parseStmt(ifTrue.position + 1);
            return new ParseResult<Statement>(new IfStatement(guard.result, ifTrue.result, ifFalse.result),
                                         ifFalse.position);
        } else if (token instanceof WhileToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Statement> body = parseStmt(guard.position + 1);
            return new ParseResult<Statement>(new WhileStmt(guard.result, body.result),
                                         body.position);
        } else if (token instanceof ReturnToken) {
            if (getToken(position + 1) instanceof SemicolonToken) {
                return new ParseResult<Statement>(new ReturnVoidStmt(), position + 2);
            } else {
                final ParseResult<Expression> exp = parseExp(position + 1);
                assertTokenHereIs(exp.position, new SemicolonToken());
                return new ParseResult<Statement>(new ReturnNonVoidStmt(exp.result),
                                             exp.position + 1);
            }
        } else if (token instanceof PrintlnToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> exp = parseExp(position + 2);
            assertTokenHereIs(exp.position, new RightParenToken());
            assertTokenHereIs(exp.position + 1, new SemicolonToken());
            return new ParseResult<Statement>(new PrintlnStmt(exp.result),
                                         exp.position + 2);
        } else if (token instanceof LeftCurlyToken) {
            final ParseResult<List<Statement>> stmts = parseStmts(position + 1);
            assertTokenHereIs(stmts.position, new RightCurlyToken());
            return new ParseResult<Statement>(new BlockStmt(stmts.result),
                                         stmts.position + 1);
        } else {
            // try variable declaration...
            try {
                final ParseResult<VarDec> vardec = parseVardec(position);
                assertTokenHereIs(vardec.position, new AssignToken());
                final ParseResult<Expression> exp = parseExp(vardec.position + 1);
                assertTokenHereIs(exp.position, new SemicolonToken());
                return new ParseResult<Statement>(new VariableInitializationStmt(vardec.result,
                                                                            exp.result),
                                             exp.position + 1);
            } catch (final ParseException e1) {
                // ...then expression statements if that didn't work...
                try {
                    final ParseResult<Expression> exp = parseExp(position);
                    assertTokenHereIs(exp.position, new SemicolonToken());
                    return new ParseResult<Statement>(new ExpStmt(exp.result),
                                                 exp.position + 1);
                } catch (final ParseException e2) {
                    // ...and finally assignment if that didn't work
                    final ParseResult<Var> variable = parseVariable(position);
                    assertTokenHereIs(variable.position, new AssignToken());
                    final ParseResult<Expression> exp = parseExp(variable.position + 1);
                    assertTokenHereIs(exp.position, new SemicolonToken());
                    return new ParseResult<Statement>(new AssignStmt(variable.result,
                                                                exp.result),
                                                 exp.position + 1);
                }
            }
        }
         
    }

    // vardecs_comma ::= [vardec (`,` vardec)*]
    public ParseResult<List<VarDec>> parseVardecsComma(int position) throws ParseException {
        final List<VarDec> vardecs = new ArrayList<VarDec>();

        try {
            ParseResult<VarDec> vardec = parseVardec(position);
            vardecs.add(vardec.result);
            position = vardec.position;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    assertTokenHereIs(position, new CommaToken());
                    vardec = parseVardec(position + 1);
                    vardecs.add(vardec.result);
                    position = vardec.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }
        } catch (final ParseException e) {}

        return new ParseResult<List<VarDec>>(vardecs, position);
    }

    // vardecs_semicolon ::= (vardec `;`)*
    public ParseResult<List<VarDec>> parseVardecsSemicolon(int position) throws ParseException {
        final List<VarDec> vardecs = new ArrayList<VarDec>();
        boolean shouldRun = true;
        while (shouldRun) {
            try {
                final ParseResult<VarDec> vardec = parseVardec(position);
                assertTokenHereIs(vardec.position, new SemicolonToken());
                vardecs.add(vardec.result);
                position = vardec.position + 1;
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return new ParseResult<List<VarDec>>(vardecs, position);
    }

    // methoddef ::= type methodname(vardecs_comma) stmt
    public ParseResult<MethodDef> parseMethodDef(final int position) throws ParseException {
        final ParseResult<Type> type = parseType(position);
        final ParseResult<MethodName> methodName = parseMethodName(type.position);
        assertTokenHereIs(methodName.position, new LeftParenToken());
        final ParseResult<List<VarDec>> arguments = parseVardecsComma(methodName.position + 1);
        assertTokenHereIs(arguments.position, new RightParenToken());
        final ParseResult<Statement> body = parseStmt(arguments.position + 1);
        return new ParseResult<MethodDef>(new MethodDef(type.result,
                                                        methodName.result,
                                                        arguments.result,
                                                        body.result),
                                          body.position);
    }

    public ParseResult<List<MethodDef>> parseMethodDefs(int position) throws ParseException {
        final List<MethodDef> methodDefs = new ArrayList<MethodDef>();
        boolean shouldRun = true;
        while (shouldRun) {
            try {
                final ParseResult<MethodDef> methodDef = parseMethodDef(position);
                methodDefs.add(methodDef.result);
                position = methodDef.position;
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return new ParseResult<List<MethodDef>>(methodDefs, position);
    }
    
    // classdef ::= class classname extends classname {
    //            vardecs_semicolon
    //            constructor(vardecs_comma) {
    //              super(comma_exp);
    //              stmt*
    //            }
    //            methoddef*
    //          }
    public ParseResult<ClassDef> parseClassDef(final int position) throws ParseException {
        // header
        assertTokenHereIs(position, new ClassToken());
        final ParseResult<ClassName> className = parseClassName(position + 1);
        assertTokenHereIs(className.position, new ExtendsToken());
        final ParseResult<ClassName> extendsClassName = parseClassName(className.position + 1);
        assertTokenHereIs(extendsClassName.position, new LeftCurlyToken());

        // instance variables
        final ParseResult<List<VarDec>> instanceVariables =
            parseVardecsSemicolon(extendsClassName.position + 1);

        // constructor header
        assertTokenHereIs(instanceVariables.position, new ConstructorToken());
        assertTokenHereIs(instanceVariables.position + 1, new LeftParenToken());
        final ParseResult<List<VarDec>> constructorArguments =
            parseVardecsComma(instanceVariables.position + 2);
        assertTokenHereIs(constructorArguments.position, new RightParenToken());
        assertTokenHereIs(constructorArguments.position + 1, new LeftCurlyToken());
        
        // constructor body
        assertTokenHereIs(constructorArguments.position + 2, new SuperToken());
        assertTokenHereIs(constructorArguments.position + 3, new LeftParenToken());
        final ParseResult<List<Expression>> superParams =
            parseCommaExp(constructorArguments.position + 4);
        assertTokenHereIs(superParams.position, new RightParenToken());
        assertTokenHereIs(superParams.position + 1, new SemicolonToken());
        final ParseResult<List<Statement>> constructorBody =
            parseStmts(superParams.position + 2);
        assertTokenHereIs(constructorBody.position, new RightCurlyToken());

        // methods
        final ParseResult<List<MethodDef>> methodDefs =
            parseMethodDefs(constructorBody.position + 1);
        assertTokenHereIs(methodDefs.position, new RightCurlyToken());

        return new ParseResult<ClassDef>(new ClassDef(className.result,
                                                      extendsClassName.result,
                                                      instanceVariables.result,
                                                      constructorArguments.result,
                                                      superParams.result,
                                                      constructorBody.result,
                                                      methodDefs.result),
                                         methodDefs.position + 1);
    }

    public ParseResult<List<ClassDef>> parseClassDefs(int position) throws ParseException {
        final List<ClassDef> classDefs = new ArrayList<ClassDef>();
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<ClassDef> classDef = parseClassDef(position);
                classDefs.add(classDef.result);
                position = classDef.position;
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return new ParseResult<List<ClassDef>>(classDefs, position);
    }

    // program ::= classdef* stmt // stmt is the entry point
    public ParseResult<Program> parseProgram(final int position) throws ParseException {
        final ParseResult<List<ClassDef>> classDefs = parseClassDefs(position);
        final ParseResult<Statement> entryPoint = parseStmt(classDefs.position);
        return new ParseResult<Program>(new Program(classDefs.result, entryPoint.result),
                                        entryPoint.position);
    }

    public Program parseProgram() throws ParseException {
        final ParseResult<Program> program = parseProgram(0);
        if (program.position == tokens.length) {
            return program.result;
        } else {
            throw new ParseException("remaining tokens at end");
        }
    }

    public static Program parse(final Token[] tokens) throws ParseException {
        return new PaserCodeGen(tokens).parseProgram();
    }

}//end of class
