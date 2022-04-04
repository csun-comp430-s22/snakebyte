package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

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
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                     parser.parserPrimaryExp(0));
    }

    @Test
    public void testPrimaryParens() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LeftParenToken(),
                                                       new IntegerToken(123),
                                                       new RightParenToken()));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 3),
                     parser.parserPrimaryExp(0));
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
    public void testAdditiveExpOnlyPrimary() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                     parser.parserAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpSingleOperator() throws ParseException {
        // 1 + 2
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(1),
                                                       new PlusToken(),
                                                       new IntegerToken(2)));
        assertEquals(new ParseResult<Expression>(new OPExp(new IntExp(1),
                                                    new PlusOP(),
                                                    new IntExp(2)),
                                          3),
                     parser.parserAdditiveExp(0));
    }

    @Test
    public void testAdditiveExpMultiOperator() throws ParseException {
        // 1 + 2 - 3 ==> (1 + 2) - 3
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(1),
                                                       new PlusToken(),
                                                       new IntegerToken(2),
                                                       new MinusToken(),
                                                       new IntegerToken(3)));
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
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(123)));
        assertEquals(new ParseResult<Expression>(new IntExp(123), 1),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanSingleOperator() throws ParseException {
        // 1 < 2
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(1),
                                                       new LessThanToken(),
                                                       new IntegerToken(2)));
        final Expression expected = new OPExp(new IntExp(1),
                                       new LessThanOp(),
                                       new IntExp(2));
        assertEquals(new ParseResult<Expression>(expected, 3),
                     parser.parseLessThanExp(0));
    }

    @Test
    public void testLessThanMultiOperator() throws ParseException {
        // 1 < 2 < 3 ==> (1 < 2) < 3
        final Parser parser = new Parser(Arrays.asList(new IntegerToken(1),
                                                       new LessThanToken(),
                                                       new IntegerToken(2),
                                                       new LessThanToken(),
                                                       new IntegerToken(3)));
        final Expression expected = new OPExp(new OPExp(new IntExp(1),
                                                 new LessThanOp(),
                                                 new IntExp(2)),
                                       new LessThanOp(),
                                       new IntExp(3));
        assertEquals(new ParseResult<Expression>(expected, 5),
                     parser.parseLessThanExp(0));
    }

    // @Test
    // public void testLessThanMixedOperator() throws ParseException {
    //     // 1 < 2 + 3 ==> 1 < (2 + 3)
    //     final Parser parser = new Parser(Arrays.asList(new IntegerToken(1),
    //                                                    new LessThanToken(),
    //                                                    new IntegerToken(2),
    //                                                    new PlusToken(),
    //                                                    new IntegerToken(3)));
    //     final Expression expected = new OPExp(new IntExp(1),
    //                                    new LessThanOp(),
    //                                    new OPExp(new IntExp(2),
    //                                              new PlusOP(),
    //                                              new IntExp(3)));
    //     assertEquals(new ParseResult<Expression>(expected, 5),
    //                  parser.parseLessThanExp(0));
    // }
}