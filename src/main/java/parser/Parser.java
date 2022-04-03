package parser;

import java.util.List;
/*HashCode Numbers
PlusOP = 100
MinusOP= 101
TimesOp = 102
DivideOp = 103
LessThanOp = 104
GreaterThanOp = 105
LessThanEqualOp = 106
GreaterThanEqualOp = 107
*/


import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;
    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }
    public Token getToken(final int position) throws ParseException{
        if(position >= 0 && position < tokens.size()){
            return tokens.get(position);
        }else{
            throw new ParseException("Invalid token position"+position);
        }
    }
     public void assertTokenHereIs(final int position, final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("expected: " + expected + "; received: " + received);
        }
    }
    //parser for statement
    //sytax:
    //statement ::= type var ‘=’ exp ‘;’| variable assignment   
    // while (exp) statement | while loops   
    // { statement * } | block  
    // break; | break  
    //  if (exp) statement else statement | if/else conditional  
    //  		return exp; | return an expression  
    // 		print(exp) | Prints something to the terminal 
    // public ParseResult<Statement> parseStatement(final int position) throws ParseException{    
    // }
    //parser for operators
    //Op::= +| - | * | /|>|< |<=|>=

    //parser for expression
    //syntax:
    //exp ::= exp Op exp | exp.functionname(exp*) method call  
    // exp ::= i | s| b | var  
    public ParseResult<Expression> parserPrimaryExp(final int position) throws ParseException{
        final Token token = getToken(position);
        if(token instanceof VarToken){
            final String name = ((VarToken)token).name;
            return new ParseResult<Expression>(new VarExp(name), position+1);
        }else if(token instanceof IntegerToken){
            final int value = ((IntegerToken)token).value;
            return new ParseResult<Expression>(new IntExp(value), position+1);
        }else if(token instanceof LeftParenToken){
            final ParseResult<Expression> inParens = parseExp(position+1);
            assertTokenHereIs(inParens.position, new RightParenToken());
            return new ParseResult<Expression>(inParens.result, inParens.position+1);
        }
    }
    public ParseResult<Operator> parseAdditiveOp(final int position) throws ParseException{
        final Token token = getToken(position);
        if(token instanceof PlusToken){
            return new ParseResult<Operator>(new PlusOP(), position+1);
        }else if(token instanceof MinusToken){
            return new ParseResult<Operator>(new MinusOP(), position+1);
        }else if(token instanceof TimesToken){
            return new ParseResult<Operator>(new TimesOp(), position+1);
        }else if(token instanceof DivideToken){
            return new ParseResult<Operator>(new DivideOp(), position+1);
        }else if(token instanceof LessThanToken){
            return new ParseResult<Operator>(new LessThanOp(), position+1);
        }else if(token instanceof GreaterThanToken){
            return new ParseResult<Operator>(new GreaterThanOp(), position+1);
        }else if(token instanceof LessThanequaltoToken){
            return new ParseResult<Operator>(new LessThanEqualOp(), position+1);
        }else if(token instanceof GreaterThanequaltoToken){
            return new ParseResult<Operator>(new GreaterThanEqualOp(), position+1);
        }else{
            throw new ParseException("Expected +,-,/,*,<,>,<=,>=, it was: "+token);
        }
    }
    public ParseResult<Expression> parserAdditiveExp(final int position) throws ParseException{
        ParseResult<Expression> current = parserPrimaryExp(position);
        boolean shouldRun = true;
        
        while (shouldRun) {
            try {
                final ParseResult<Operator> additiveOp = parseAdditiveOp(current.position);
                final ParseResult<Expression> anotherPrimary = parserPrimaryExp(additiveOp.position);
                current = new ParseResult<Expression>(new OPExp(current.result,
                                                         additiveOp.result,
                                                         anotherPrimary.result),
                                               anotherPrimary.position);
            } catch (final ParseException e) {
                shouldRun = false;
            }
        }

        return current;
    }
    

    public ParseResult<Statement> parserStatement(final int position) throws ParseException{
        final Token token = getToken(position);
        // if
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
    //the following code was the original code:

    
    public ParseResult<Expression> parseExp(final int position) throws ParseException {
        final Token token = getToken(position);
        if (token instanceof IfToken) {
            assertTokenHereIs(position + 1, new LeftParenToken());
            final ParseResult<Expression> guard = parseExp(position + 2);
            assertTokenHereIs(guard.position, new RightParenToken());
            final ParseResult<Expression> ifTrue = parseExp(guard.position + 1);
            assertTokenHereIs(ifTrue.position, new ElseToken());
            final ParseResult<Expression> ifFalse = parseExp(ifTrue.position + 1);
            return new ParseResult<Expression>(new IfStatement(guard.result, ifTrue.result, ifFalse.result), 
                                                ifFalse.position);
        } else {
            return parserAdditiveExp(position);
        }
    }
}