package parser;

import java.util.List;
/*HashCode Numbers
PlusOP = 100
MinusOP= 101
*/


import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    private class ParseResult<A> {
        public final A result;
        public final int nextPosition;
        public ParseResult(final A result, final int nextPosition) {
            this.result = result;
            this.nextPosition = nextPosition;
        }
    }

    public Token getToken(final int position) throws ParseException {
        if (position >= 0 && position < tokens.size()) {
            return tokens.get(position);
        } else {
            throw new ParseException("Invalid token position: " + position);
        }
    }
    public void assertTokenHereIs(final int position, final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("expected: " + expected + "; received: " + received);
        }
    }
    public ParseResult<Expression> parsePrimaryExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof VarToken) {
            final String name = ((VarToken)token).name;
            return new ParseResult<Expression>(new VarExp(name), position + 1);
        } else if (token instanceof IntegerToken) {
            final int value = ((IntegerToken)token).value;
            return new ParseResult<Expression>(new IntExp(value), position + 1);
        }
        else if (token instanceof LeftParenToken) {
            final ParseResult<Expression> inParens = parseExp(position + 1);
            //it was inParens.position
            assertTokenHereIs(inParens.nextPosition, new RightParenToken());
            return new ParseResult<Expression>(inParens.result,
                                        inParens.nextPosition + 1);
        }
    }
    public ParseResult<Operator> parseAdditiveOp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof PlusToken) {
            return new ParseResult<Operator>(new PlusOP(), position + 1);
        } else if (token instanceof MinusToken) {
            return new ParseResult<Operator>(new MinusOP(), position + 1);
        } else {
            throw new ParseException("expected + or -; received: " + token);
        }
    }
    public ParseResult<Expression> parseAdditiveExp(final int position) throws ParseException {
        ParseResult<Expression> current = parsePrimaryExp(position);
        boolean shouldRun = true;
        
        while (shouldRun) {
            try {
                final ParseResult<Operator> additiveOp = parseAdditiveOp(current.nextPosition);
                final ParseResult<Expression> anotherPrimary = parsePrimaryExp(additiveOp.nextPosition);
                current = new ParseResult<Expression>(new OPExp(current.result,
                                                         additiveOp.result,
                                                         anotherPrimary.result),
                                               anotherPrimary.nextPosition);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }
    public ParseResult<Expression> parseExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.nextPosition, new RightParenToken());
            final ParseResult<Expression> ifTrue = parseExp(guard.nextPosition + 1);
            assertTokenHereIs(ifTrue.nextPosition, new ElseToken());
            final ParseResult<Expression> ifFalse = parseExp(ifTrue.nextPosition + 1);
            return new ParseResult<Expression>(new IfExp(guard.result, ifTrue.result, ifFalse.result), 
                                                ifFalse.nextPosition);
        } else {
            return parseAdditiveExp(position);
        }
    }
    public ParseResult<Statement> parseStmt(final int position) throws ParseException {
        final Token token = getToken(position);
        // if
        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.nextPosition, new RightParenToken());
            final ParseResult<Statement> trueBranch = parseStmt(guard.nextPosition + 1);
            assertTokenHereIs(trueBranch.nextPosition, new ElseToken());
            final ParseResult<Statement> falseBranch = parseStmt(trueBranch.nextPosition + 1);
            return new ParseResult<Statement>(new IfExp(guard.result,
                                                   trueBranch.result,
                                                   falseBranch.result),
                                         falseBranch.nextPosition);
        } else if (token instanceof LeftCurlyToken) {
            final List<Statement> stmts = new ArrayList<Statement>();
            int curPosition = position + 1;
            boolean shouldRun = true;
            while (shouldRun) {
                try {
                    final ParseResult<Statement> stmt = parseStmt(curPosition);
                    stmts.add(stmt.result);
                    curPosition = stmt.nextPosition;
                } catch (final ParseException e) {
                    shouldRun = false;
                }
            }
            return new ParseResult<Statement>(new BlockStatement(stmts),
                                         curPosition);
        } else if (token instanceof PrintToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> exp = parseExp(position + 2);
            assertTokenHereIs(exp.nextPosition, new RightParenToken());
            assertTokenHereIs(exp.nextPosition + 1, new SemiColonToken());
            return new ParseResult<Statement>(new PrintStatement(exp.result),
                                         exp.nextPosition + 2);
        } else {
            throw new ParseException("expected statement; received: " + token);
        }
    }
}