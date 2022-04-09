package parser;

import java.util.List;
import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }
    // **************************************************************************Helpers*****************************************************************************
    // */

    public Token getToken(final int position) throws ParseException {
        if (position >= 0 && position < tokens.size()) {
            return tokens.get(position);
        } else {
            throw new ParseException("Invalid token position" + position);
        }
    }

    public void assertTokenHereIs(final int position, final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("expected: " + expected + "; received: " + received);
        }
    }
    // ***********************************************************************Expressions***************************************************************************
    // */

    public ParseResult<Expression> parserPrimaryExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof VarToken) {
            final String name = ((VarToken) token).name;
            return new ParseResult<Expression>(new VarExp(new Var(name)),
                    position + 1);

        } else if (token instanceof IntegerValue) {
            final int value = ((IntegerValue) token).value;
            return new ParseResult<Expression>(new IntExp(value), position + 1);
        } else if (token instanceof StringValue) {
            final String value = ((StringValue) token).value;
            return new ParseResult<Expression>(new StringExp(value), position + 1);
        } else if (token instanceof BooleanValue) {
            final Boolean value = ((BooleanValue) token).value;
            return new ParseResult<Expression>(new BooleanExp(value), position + 1);
        } else if (token instanceof LeftParenToken) {
            final ParseResult<Expression> inParens = parseExp(position + 1);
            assertTokenHereIs(inParens.position, new RightParenToken());
            return new ParseResult<Expression>(inParens.result, inParens.position + 1);
        } else {
            // added default if branch
            throw new ParseException("Unexpected token: " + token);
        }
    }

    public ParseResult<Operator> parseMultiplicativeOp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof TimesToken) {
            return new ParseResult<Operator>(new TimesOp(), position + 1);
        } else if (token instanceof DivideToken) {
            return new ParseResult<Operator>(new DivideOp(), position + 1);
        } else {
            throw new ParseException("Expected /,*, it was: " + token);
        }
    }

    public ParseResult<Operator> parseAdditiveOp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof PlusToken) {
            return new ParseResult<Operator>(new PlusOP(), position + 1);
        } else if (token instanceof MinusToken) {
            return new ParseResult<Operator>(new MinusOP(), position + 1);
        } else {
            throw new ParseException("Expected +,-, it was: " + token);
        }
    }

    public ParseResult<Operator> parseRelationalOp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof LessThanToken) {
            return new ParseResult<Operator>(new LessThanOp(), position + 1);
        } else if (token instanceof GreaterThanToken) {
            return new ParseResult<Operator>(new GreaterThanOp(), position + 1);
        } else if (token instanceof LessThanequaltoToken) {
            return new ParseResult<Operator>(new LessThanEqualOp(), position + 1);
        } else if (token instanceof GreaterThanequaltoToken) {
            return new ParseResult<Operator>(new GreaterThanEqualOp(), position + 1);
        } else {
            throw new ParseException("Expected >,<,>=,<=, it was: " + token);
        }
    }

    // parse multiplicative * /
    public ParseResult<Expression> parserMultiplicativeExp(final int position) throws ParseException {
        ParseResult<Expression> current = parserPrimaryExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<Operator> multiplicativeOp = parseMultiplicativeOp(current.position);
                final ParseResult<Expression> anotherPrimary = parserPrimaryExp(multiplicativeOp.position);
                current = new ParseResult<Expression>(new OPExp(current.result,
                        multiplicativeOp.result,
                        anotherPrimary.result),
                        anotherPrimary.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }

    // parse additives + -
    public ParseResult<Expression> parserAdditiveExp(final int position) throws ParseException {
        ParseResult<Expression> current = parserMultiplicativeExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<Operator> additiveOp = parseAdditiveOp(current.position);
                final ParseResult<Expression> anotherMultiplicative = parserMultiplicativeExp(additiveOp.position);
                current = new ParseResult<Expression>(new OPExp(current.result,
                        additiveOp.result,
                        anotherMultiplicative.result),
                        anotherMultiplicative.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }

    // parse relationals < > <= >=
    public ParseResult<Expression> parseRelationalExp(final int position) throws ParseException {
        ParseResult<Expression> current = parserAdditiveExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                final ParseResult<Operator> relationalOp = parseRelationalOp(current.position);
                final ParseResult<Expression> anotherAdditve = parserAdditiveExp(relationalOp.position);
                current = new ParseResult<Expression>(new OPExp(current.result,
                        relationalOp.result,
                        anotherAdditve.result),
                        anotherAdditve.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }
        return current;
    }

    // parseEqualsExp ==
    public ParseResult<Expression> parseEqualsExp(final int position) throws ParseException {
        ParseResult<Expression> current = parseRelationalExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(current.position, new EqualsEqualsToken());
                final ParseResult<Expression> other = parseRelationalExp(current.position + 1);
                current = new ParseResult<Expression>(new OPExp(current.result,
                        new EqualsEqualsOp(),
                        other.result),
                        other.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }

    // parse assignment =
    public ParseResult<Expression> parseAssignmentExp(final int position) throws ParseException {
        ParseResult<Expression> current = parseEqualsExp(position);
        boolean shouldRun = true;

        while (shouldRun) {
            try {
                assertTokenHereIs(current.position, new EqualsToken());
                final ParseResult<Expression> other = parseEqualsExp(current.position + 1);
                current = new ParseResult<Expression>(new OPExp(current.result,
                        new EqualsOp(),
                        other.result),
                        other.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }

    public ParseResult<Expression> parseExp(final int position) throws ParseException {
        return parseAssignmentExp(position);
    }

    // ***********************************************************************************************Statements****************************************************************************************
    // */
    // add while and var dec

    public ParseResult<Statement> parserStatement(final int position) throws ParseException {
        final Token token = getToken(position);

        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Statement> trueBranch = parserStatement(guard.position + 1);
            assertTokenHereIs(trueBranch.position, new ElseToken());
            final ParseResult<Statement> falseBranch = parserStatement(trueBranch.position + 1);
            return new ParseResult<Statement>(new IfStatement(guard.result,
                    trueBranch.result,
                    falseBranch.result),
                    falseBranch.position);
        } else if (token instanceof WhileToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Statement> trueBranch = parserStatement(guard.position + 1);
            return new ParseResult<Statement>(new WhileStatement(guard.result, trueBranch.result), trueBranch.position);
        } else if (token instanceof LeftCurlyToken) {
            final List<Statement> stmts = new ArrayList<Statement>();
            int curPosition = position + 1;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Statement> stmt = parserStatement(curPosition);
                    stmts.add(stmt.result);
                    curPosition = stmt.position;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }
            return new ParseResult<Statement>(new BlockStatement(stmts),
                    curPosition);
        } else if (token instanceof PrintToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> exp = parseExp(position + 2);
            assertTokenHereIs(exp.position, new RightParenToken());
            assertTokenHereIs(exp.position + 1, new SemiColonToken());
            return new ParseResult<Statement>(new PrintStatement(exp.result),
                    exp.position + 2);
        } else {
            throw new ParseException("expected statement; received: " + token);
        }
    }

    // ****************************************************************************************************************Program**********************************************************************************************
    // */

    public ParseResult<Program> parseProgram(final int position) throws ParseException {
        final ParseResult<Statement> stmt = parserStatement(position);
        return new ParseResult<Program>(new Program(stmt.result),
                stmt.position);
    } // parseProgram

    // intended to be called on the top-level
    public Program parseProgram() throws ParseException {
        final ParseResult<Program> program = parseProgram(0);
        // make sure all tokens were read in
        // if any tokens remain, then there is something extra at the end
        // of the program, which should be a syntax error
        if (program.position == tokens.size()) {
            return program.result;
        } else {
            throw new ParseException("Remaining tokens at end");
        }
    } // parseProgram
      // parse operator: <,==
      // parseLessThanExp

}

/*
 * precedence of parsing
 * 
 * this is deweys basic structure add extra ops and expressions from our own
 * language
 * 
 * primary_expression ::= x | i | s | b | '(' exp ')'
 * additive_operation ::= + | -
 * additive_expression ::= primary_expression(additive_operation
 * primary_expression)*
 * less_than_expression ::= additive_expression('<' additive_exp)*
 * equals_expression ::= less_than_expression('==' less_than_expression)*
 * exp :: equals_expression
 * statement ::= if(expression) statement else statement | {statement*} |
 * while(expression) statement | println(exp) statement seperated by ;
 * program ::= statement
 * 
 */

// missing statement
// while
// method def
// class def
// vardec
// add return and return value token
/*
 * HashCode Numbers
 * PlusOP = 100
 * MinusOP= 101
 * TimesOp = 102
 * DivideOp = 103
 * LessThanOp = 104
 * GreaterThanOp = 105
 * LessThanEqualOp = 106
 * GreaterThanEqualOp = 107
 * IfExp = 108
 */

// parser for statement
// sytax:
// statement ::= type var ‘=’ exp ‘;’| variable assignment
// while (exp) statement | while loops
// { statement * } | block
// break; | break
// if (exp) statement else statement | if/else conditional
// return exp; | return an expression
// print(exp) | Prints something to the terminal
// public ParseResult<Statement> parseStatement(final int position) throws
// ParseException{
// }
// parser for operators
// Op::= +| - | * | /|>|< |<=|>=

// parser for expression
// syntax:
// exp ::= exp Op exp | exp.functionname(exp*) method call
// exp ::= i | s| b | var