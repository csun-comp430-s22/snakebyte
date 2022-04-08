package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;

import org.junit.Test;

//import lexer.IntegerToken;

public class ParserTest {
    @Test
    public void testEqualsOPExp() {
        // 1 + 1 == 1 + 1
        final OPExp first = new OPExp(new IntExp(1),
                new PlusOP(),
                new IntExp(1));
        final OPExp second = new OPExp(new IntExp(1),
                new PlusOP(),
                new IntExp(1));
        assertEquals(first, second);
    }

    @Test
    public void testPrimaryVariable() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new VarToken("x")));
        assertEquals(new ParseResult<Expression>(new VarExp(new Var("x")),
                1),
                parser.parserPrimaryExp(0));
    }

    @Test
    public void testPrimaryInteger() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                parser.parserPrimaryExp(0));
    }

    @Test
    public void testPrimaryString() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new StringValue("hello")));
        assertEquals(new ParseResult<Expression>(new StringExp("hello"), 1),
                parser.parserPrimaryExp(0));
    }

    @Test
    public void testPrimaryBool() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new BooleanValue(true)));
        assertEquals(new ParseResult<Expression>(new BooleanExp(true), 1),
                parser.parserPrimaryExp(0));
    }

    @Test
    public void testPrimaryParens() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LeftParenToken(),
                new IntegerValue(123),
                new RightParenToken()));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 3),
                parser.parserPrimaryExp(0));
    }
    @Test(expected = ParseException.class)
    public void testPrimaryException() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new RightParenToken()));
        assertEquals("Unexpected token: RightParenToken", parser.parserPrimaryExp(0));
    }

    @Test
    public void testAdditiveOpPlus() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new PlusToken()));
        assertEquals(new ParseResult<Operator>(new PlusOP(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpMinus() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new MinusToken()));
        assertEquals(new ParseResult<Operator>(new MinusOP(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpTimes() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new TimesToken()));
        assertEquals(new ParseResult<Operator>(new TimesOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpDivide() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new DivideToken()));
        assertEquals(new ParseResult<Operator>(new DivideOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpGreaterThanOp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new GreaterThanToken()));
        assertEquals(new ParseResult<Operator>(new GreaterThanOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpLessThanEqualOp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LessThanequaltoToken()));
        assertEquals(new ParseResult<Operator>(new LessThanEqualOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpGreaterThanEqual() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new GreaterThanequaltoToken()));
        assertEquals(new ParseResult<Operator>(new GreaterThanEqualOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveEqualsEqualsOp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new EqualsEqualsToken()));
        assertEquals(new ParseResult<Operator>(new EqualsEqualsOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveOpEquals() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new EqualsToken()));
        assertEquals(new ParseResult<Operator>(new EqualsOp(), 1),
                parser.parseAdditiveOp(0));
    }

    @Test
    public void testAdditiveExpOnlyPrimary() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                parser.parserAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpSingleOperator() throws ParseException {
        // 1 + 2
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(1),
                new PlusToken(),
                new IntegerValue(2)));
        assertEquals(new ParseResult<Expression>(new OPExp(new IntExp(1),
                new PlusOP(),
                new IntExp(2)),
                3),
                parser.parserAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpMultiOperator() throws ParseException {
        // 1 + 2 - 3 ==> (1 + 2) - 3
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(1),
                new PlusToken(),
                new IntegerValue(2),
                new MinusToken(),
                new IntegerValue(3)));
        final Expression expected = new OPExp(new OPExp(new IntExp(1),
                new PlusOP(),
                new IntExp(2)),
                new MinusOP(),
                new IntExp(3));
        assertEquals(new ParseResult<Expression>(expected, 5),
                parser.parserAdditiveExp(0));
    }

    @Test
    public void testLessThanExpOnlyAdditive() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                parser.parseRelationalExp(0));
    }

    @Test
    public void testLessThanSingleOperator() throws ParseException {
        // 1 < 2
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(1),
                new LessThanToken(),
                new IntegerValue(2)));
        final Expression expected = new OPExp(new IntExp(1),
                new LessThanOp(),
                new IntExp(2));
        assertEquals(new ParseResult<Expression>(expected, 3),
                parser.parseRelationalExp(0));
    }

    @Test
    public void testLessThanMultiOperator() throws ParseException {
        // 1 < 2 < 3 ==> (1 < 2) < 3
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(1),
                new LessThanToken(),
                new IntegerValue(2),
                new LessThanToken(),
                new IntegerValue(3)));
        final Expression expected = new OPExp(new OPExp(new IntExp(1),
                new LessThanOp(),
                new IntExp(2)),
                new LessThanOp(),
                new IntExp(3));
        assertEquals(new ParseResult<Expression>(expected, 5),
                parser.parseRelationalExp(0));
    }
    //test fail
    @Test
    public void testLessThanMixedOperator() throws ParseException {
        // 1 < 2 + 3 ==> 1 < (2 + 3)
        //can not pass this test
        //expected: OPExp(IntExp(1), LessThanOp, OPExp(IntExp(2), PlusOp, IntExp(3)))
        //but was: OPExp(OPExp(IntExp(1), LessThanOp, IntExp(2)), PlusOp, IntExp(3))
        final Parser parser = new Parser(Arrays.asList(new IntegerValue(1),
                                        new LessThanToken(),
                                        new IntegerValue(2),
                                        new PlusToken(),
                                        new IntegerValue(3)));
        final Expression expected = new OPExp(new IntExp(1),
                                        new LessThanOp(),
                                        new OPExp(new IntExp(2),
                                                new PlusOP(),
                                                new IntExp(3)));
        assertEquals(new ParseResult<Expression>(expected, 5),
        parser.parseRelationalExp(0));
    }
    @Test
    public void testOpExpression() throws ParseException {
        final Expression first = new OPExp(new IntExp(1),
                new LessThanOp(),
                new OPExp(new IntExp(2),
                        new PlusOP(),
                        new IntExp(3)));
        assertEquals(first.equals(new OPExp(new IntExp(1), new LessThanOp(),
                new OPExp(new IntExp(2), new PlusOP(), new IntExp(3)))),
                true);
    }
    //test fail
    @Test
    public void testIfStatementZeroParameters() throws ParseException {
        //expected:ParseResult(IfStatement(IfExp(), Print(IntExp(1)), Print(IntExp(1))), 15)
        //was: ParseResult(IfStatement(IntExp(1), Print(IntExp(1)), Print(IntExp(1))), 15)
        final Parser parser = new Parser(
                Arrays.asList(new IfToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(),
                        new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(),
                        new SemiColonToken(),
                        new ElseToken(), new PrintToken(), new LeftParenToken(), new IntegerValue(1),
                        new RightParenToken(), new SemiColonToken()));
        final Statement expected = new IfStatement(new IfExp(), new PrintStatement(new IntExp(1)),
                new PrintStatement(new IntExp(1)));
        assertEquals(new ParseResult<Statement>(expected, 15), parser.parserStatement(0));

    }
    //test fail
    @Test
    //test the statement: {statement *}
    public void testStatementLeftCurly() throws ParseException{
        final Parser parser = new Parser(Arrays.asList(new LeftCurlyToken(),
                new RightCurlyToken()));
        final Statement expected = new BlockStatement(Arrays.asList());
        assertEquals(new ParseResult<Statement>(expected, 1),parser.parserStatement(0));
    }

    //test parsePrgram method
    @Test(expected = ParseException.class)
    public void testParserProgram() throws ParseException{
        final Parser parser = new Parser(Arrays.asList(new LeftCurlyToken(),
                new RightCurlyToken()));
        final Program expected = new Program(new BlockStatement(Arrays.asList()));
        assertEquals(new ParseResult<Program>(expected, 1), parser.parseProgram());
    }


}