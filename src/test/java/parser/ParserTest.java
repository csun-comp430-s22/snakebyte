package parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.lang.Thread.State;
import java.sql.Array;
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
    public void testMultiplicativeOpTimes() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new TimesToken()));
        assertEquals(new ParseResult<Operator>(new TimesOp(), 1),
                parser.parseMultiplicativeOp(0));
    }

    @Test
    public void testMultiplicativeOpDivide() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new DivideToken()));
        assertEquals(new ParseResult<Operator>(new DivideOp(), 1),
                parser.parseMultiplicativeOp(0));
    }

    @Test
    public void testRelationalOpGreaterThanOp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new GreaterThanToken()));
        assertEquals(new ParseResult<Operator>(new GreaterThanOp(), 1),
                parser.parseRelationalOp(0));
    }

    @Test
    public void testRelationalOpLessThanEqualOp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new LessThanequaltoToken()));
        assertEquals(new ParseResult<Operator>(new LessThanEqualOp(), 1),
                parser.parseRelationalOp(0));
    }

    @Test
    public void testRelationalOpGreaterThanEqual() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new GreaterThanequaltoToken()));
        assertEquals(new ParseResult<Operator>(new GreaterThanEqualOp(), 1),
                parser.parseRelationalOp(0));
    }

    /*
    @Test
    public void testAdditiveEqualsEqualsExp() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new EqualsEqualsToken()));
        assertEquals(new ParseResult<Operator>(new EqualsEqualsOp(), 1),
                parser.parseEqualsExp(0));
    }

    @Test
    public void testAdditiveOpEquals() throws ParseException {
        final Parser parser = new Parser(Arrays.asList(new EqualsToken()));
        assertEquals(new ParseResult<Operator>(new EqualsOp(), 1),
                parser.parseAssignmentExp(0));
    }
    */

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
        final Statement expected = new IfStatement(new IntExp(1), new PrintStatement(new IntExp(1)),
                new PrintStatement(new IntExp(1)));
        assertEquals(new ParseResult<Statement>(expected, 15), (ParseResult<Statement>) parser.parserStatement(0));

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

    @Test
    public void testIfStatementEqualsEquals() throws ParseException {
        // should represent if (1 == 1) 1; else 1;
        final Parser parser = new Parser(
                Arrays.asList(new IfToken(), new LeftParenToken(), new IntegerValue(1), new EqualsEqualsToken(),
                        new IntegerValue(1), new RightParenToken(), new PrintToken(), new LeftParenToken(), 
                        new IntegerValue(1), new RightParenToken(), new SemiColonToken(), new ElseToken(),
                        new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(),
                        new SemiColonToken()));
        final Statement expected = new IfStatement(new OPExp(new IntExp(1), new EqualsEqualsOp(), new IntExp(1)), 
                        new PrintStatement(new IntExp(1)), new PrintStatement(new IntExp(1)));
        assertEquals(new ParseResult<Statement>(expected, 17), (ParseResult<Statement>)parser.parserStatement(0));
    }
    
    @Test
    public void testIntegerValue() throws ParseException {
        IntegerValue integerValue = new IntegerValue(1);
        assertTrue(integerValue.equals(new IntegerValue(1)));
        assertEquals(new IntegerValue(1).value, integerValue.hashCode());
        assertNotEquals(new IntegerValue(2), integerValue);
        assertEquals("IntegerValue(1)", integerValue.toString());
    }

    @Test
    public void testStringValue() throws ParseException {
        StringValue stringValue = new StringValue("test");
        assertTrue(stringValue.equals(new StringValue("test")));
        assertEquals(new StringValue("test").hashCode(), stringValue.hashCode());
        assertNotEquals(new StringExp("fail"), stringValue);
        assertEquals("StringValue(test)", stringValue.toString());
    }

    @Test
    public void testIfStatementBooleanExp() throws ParseException {
        // if (false) 1 * 3 = 
        final Parser parser = new Parser(
            Arrays.asList(new IfToken(), new LeftParenToken(), new BooleanValue(false), new RightParenToken(), 
                    new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(), new SemiColonToken(), new ElseToken(),
                    new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(),
                    new SemiColonToken())); 
        final Statement expected = new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
                        new PrintStatement(new IntExp(1)));
        assertEquals(new ParseResult<Statement>(expected, 15), (ParseResult<Statement>)parser.parserStatement(0));
    }
    @Test 
    public void testIfStatementToString() throws ParseException{
        final Statement test = new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
                        new PrintStatement(new IntExp(1)));
        assertEquals("IfStatement(BooleanExp(false), Print(IntExp(1)), Print(IntExp(1)))",test.toString());
    }
    @Test
    public void testProgramToString() throws ParseException{
        final Program test= new Program(new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1))));
        assertEquals("Program(IfStatement(BooleanExp(false), Print(IntExp(1)), Print(IntExp(1))))", test.toString()); 
    }
    @Test
    public void testProgramEquals() throws ParseException{
        
    final Program test= new Program(new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1))));
        final Program test2= new Program(new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1))));
        assertEquals(true, test.equals(test2));
    }
    @Test
    public void testProgramNotEquals() throws ParseException{
        final Program test= new Program(new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1))));
        final Statement test2 = new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1)));
        assertEquals(false, test.equals(test2));
    }
    @Test
    public void testBooleanValue() {
        BooleanValue booleanValue = new BooleanValue(true);
        assertTrue(booleanValue.equals(true));
        assertEquals(11, booleanValue.hashCode());
        assertEquals("BooleanValue: true", booleanValue.toString());
    }

    @Test
    public void testVar() {
        Var var = new Var("x");
        assertTrue(var.equals(new Var("x")));
        assertEquals(new Var("x").hashCode(), var.hashCode());
        assertEquals("Var(x)", var.toString());
    }

    @Test
    public void testBooleanExp() {
        BooleanExp booleanExp = new BooleanExp(true);
        BooleanExp booleanExp2 = new BooleanExp(false);
        assertTrue(booleanExp.equals(new BooleanExp(true)));
        assertEquals(1, booleanExp.hashCode());
        assertEquals(0, booleanExp2.hashCode());
        assertEquals("BooleanExp(true)", booleanExp.toString());
    }

    @Test
    public void testVarEqualsBoolean() throws ParseException {
        // x = true
        final Parser parser = new Parser(Arrays.asList(new VarToken("x"),
                new EqualsToken(),
                new BooleanValue(true)));
        assertEquals(new ParseResult<Expression>(new OPExp(new VarExp(new Var("x")),
                new EqualsOp(),
                new BooleanExp(true)),
                3),
                parser.parseAssignmentExp(0));
    }
    @Test
    public void testWhileStatement() throws ParseException{
        final Parser parser = new Parser(Arrays.asList(new WhileToken(), new LeftParenToken(), new BooleanValue(true), new RightParenToken(), 
                              new PrintToken(), new LeftParenToken(), new IntegerValue(55), new RightParenToken(), new SemiColonToken()));
        final Statement expected = new WhileStatement(new BooleanExp(true), new PrintStatement(new IntExp(55)));
        assertEquals((new ParseResult<Statement>(expected,9)),parser.parserStatement(0));
    }
    @Test
    public void TestWhileStatementToString() throws ParseException{
        final Statement expected = new WhileStatement(new BooleanExp(true), new PrintStatement(new IntExp(55)));
        assertEquals("WhileStatement(BooleanExp(true), Print(IntExp(55)))", expected.toString());
    }
    @Test
    public void TestWhileStatementEqualsFalse() throws ParseException{
        final Statement expected = new WhileStatement(new BooleanExp(true), new PrintStatement(new IntExp(55)));
        final Statement test=new IfStatement(new BooleanExp(false),  new PrintStatement(new IntExp(1)), 
        new PrintStatement(new IntExp(1)));
        assertEquals(false, expected.equals(test));
    }
    @Test
    public void TestWhileStatementEqualsTrue() throws ParseException{
        final Statement expected = new WhileStatement(new BooleanExp(true), new PrintStatement(new IntExp(55)));
        final Statement test=new WhileStatement(new BooleanExp(true), new PrintStatement(new IntExp(55)));
        assertEquals(true, expected.equals(test));
    }
    @Test
    public void testOPExpToString() throws ParseException{
        final Expression test = new OPExp(new IntExp(1), new PlusOP(), new IntExp(5));
        assertEquals("OPExp(IntExp(1), PlusOp, IntExp(5))", test.toString());
    }
    @Test
    public void testMultiplicativeOpTimesSecondtest() throws ParseException{
        final Parser parser= new Parser(
            Arrays.asList(new IfToken(), new LeftParenToken(), new IntegerValue(5), new TimesToken(),new IntegerValue(5), new RightParenToken(), 
                    new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(), new SemiColonToken(), new ElseToken(),
                    new PrintToken(), new LeftParenToken(), new IntegerValue(1), new RightParenToken(),
                    new SemiColonToken()));
        final Statement expected= new IfStatement(new OPExp(new IntExp(5), new TimesOp(), new IntExp(5)), new PrintStatement(new IntExp(1)), new PrintStatement(new IntExp(1)));
        assertEquals(new ParseResult<Statement>(expected, 17), parser.parserStatement(0));

    }
    @Test
    public void testIntVardec() throws ParseException{
        final Parser parser= new Parser(
            Arrays.asList(new IntegerToken(), new VarToken("Test")));
     
        assertEquals(new ParseResult<Statement>( new VarDecStatement<IntegerToken>("Test", new IntegerToken()),1), parser.parserStatement(0));
    }
    @Test
    public void testStringVardec() throws ParseException{
        final Parser parser= new Parser(
            Arrays.asList(new StringToken(), new VarToken("Test")));
     
        assertEquals(new ParseResult<Statement>( new VarDecStatement<StringToken>("Test", new StringToken()),1), parser.parserStatement(0));
    }
    @Test
    public void testBoolVardec() throws ParseException{
        final Parser parser= new Parser(
            Arrays.asList(new BooleanToken(), new VarToken("Test")));
     
        assertEquals(new ParseResult<Statement>( new VarDecStatement<BooleanToken>("Test", new BooleanToken()),1), parser.parserStatement(0));
    }
    @Test
    public void testVardecToString() throws ParseException{
        final Statement test = new VarDecStatement<IntegerToken>("Test", new IntegerToken());
        assertEquals("VarDecStatement(Int, Test)", test.toString());

    }
    @Test
    public void testBlockStatementToString() throws ParseException{
        final Statement test = new BlockStatement(Arrays.asList(new PrintStatement(new IntExp(5))));
        assertEquals("BlockStatement([Print(IntExp(5))])", test.toString());
    }   
    @Test
    public void testStringExptostring() throws ParseException{
        final Expression test = new StringExp("value");
        assertEquals("StringExp(value)", test.toString());
    }
    @Test
    public void testVarExptostring() throws ParseException{
        final Expression test = new VarExp(new Var("value"));
        assertEquals("VarExp(Var(value))", test.toString());
    }
    @Test
    public void testParseResulttostring() throws ParseException{
        final Parser parser= new Parser(
            Arrays.asList(new BooleanToken(), new VarToken("Test")));
        assertEquals("ParseResult(VarDecStatement(Bool, Test), 1)", parser.parserStatement(0).toString());
    }
    // -----------------------------------REUSING TOKEN TESTS ----------------------------------------------------

    @Test
    public void testDotTokenClass() {
        PeriodToken dotToken = new PeriodToken();
        assertTrue(dotToken.equals(new PeriodToken()));
        assertEquals(29, dotToken.hashCode());
        assertEquals("PeriodToken", dotToken.toString());
    }

    @Test
    public void testIntegerTokenClass() {
        IntegerToken integerToken = new IntegerToken();
        assertTrue(integerToken.equals(new IntegerToken()));
        assertEquals(9, integerToken.hashCode());
        assertEquals("Int", integerToken.toString());
    }

    @Test
    public void testNewTokenClass() {
        NewToken newToken = new NewToken();
        assertTrue(newToken.equals(new NewToken()));
        assertEquals(8, newToken.hashCode());
        assertEquals("NewToken", newToken.toString());
    }

    @Test
    public void testLeftSqrBracketTokenClass() {
        LeftSqrBrktToken leftSqrBracketToken = new LeftSqrBrktToken();
        assertTrue(leftSqrBracketToken.equals(new LeftSqrBrktToken()));
        assertEquals(12, leftSqrBracketToken.hashCode());
        assertEquals("LeftSqrBrktToken", leftSqrBracketToken.toString());
    }

    @Test
    public void testBoolTokenClass() {
        BooleanToken boolToken = new BooleanToken();
        assertTrue(boolToken.equals(new BooleanToken()));
        assertEquals(11, boolToken.hashCode());
        assertEquals("Bool", boolToken.toString());
    }

    @Test
    public void testWhileTokenClass() {
        WhileToken whileToken = new WhileToken();
        assertTrue(whileToken.equals(new WhileToken()));
        assertEquals(18, whileToken.hashCode());
        assertEquals("WhileToken", whileToken.toString());
    }

    @Test
    public void testThisTokenClass() {
        ThisToken thisToken = new ThisToken();
        assertTrue(thisToken.equals(new ThisToken()));
        assertEquals(30, thisToken.hashCode());
        assertEquals("ThisToken", thisToken.toString());
    }

    @Test
    public void testRightSqrBracketTokenClass() {
        RightSqrBrktToken rightSqrBracketToken = new RightSqrBrktToken();
        assertTrue(rightSqrBracketToken.equals(new RightSqrBrktToken()));
        assertEquals(13, rightSqrBracketToken.hashCode());
        assertEquals("RightSqrBrktToken", rightSqrBracketToken.toString());
    }

    @Test
    public void testExtendsTokenClass() {
        ExtendsToken extendsToken = new ExtendsToken();
        assertTrue(extendsToken.equals(new ExtendsToken()));
        assertEquals(23, extendsToken.hashCode());
        assertEquals("ExtendsToken", extendsToken.toString());
    }

    @Test
    public void testStringTokenClass() {
        StringToken stringToken = new StringToken();
        assertTrue(stringToken.equals(new StringToken()));
        assertEquals(10, stringToken.hashCode());
        assertEquals("Str", stringToken.toString());
    }

    @Test
    public void testSupertoken() {
        SuperToken superToken = new SuperToken();
        assertTrue(superToken.equals(new SuperToken()));
        assertEquals(32, superToken.hashCode());
        assertEquals("SuperToken", superToken.toString());
    }

    @Test
    public void testBreakTokenClass() {
        BreakToken breakToken = new BreakToken();
        assertTrue(breakToken.equals(new BreakToken()));
        assertEquals(20, breakToken.hashCode());
        assertEquals("BreakToken", breakToken.toString());
    }

    @Test
    public void testTrueTokenClass() {
        TrueToken trueToken = new TrueToken();
        assertTrue(trueToken.equals(new TrueToken()));
        assertEquals(24, trueToken.hashCode());
        assertEquals("TrueToken", trueToken.toString());
    }

    @Test
    public void testCommaTokenClass() {
        CommaToken commaToken = new CommaToken();
        assertTrue(commaToken.equals(new CommaToken()));
        assertEquals(28, commaToken.hashCode());
        assertEquals("CommaToken", commaToken.toString());
    }

    @Test
    public void testClassTokenClass() {
        ClassToken classToken = new ClassToken();
        assertTrue(classToken.equals(new ClassToken()));
        assertEquals(26, classToken.hashCode());
        assertEquals("ClassToken", classToken.toString());
    }

    @Test
    public void testFalseTokenClass() {
        FalseToken falseToken = new FalseToken();
        assertTrue(falseToken.equals(new FalseToken()));
        assertEquals(25, falseToken.hashCode());
        assertEquals("FalseToken", falseToken.toString());
    }

    @Test
    public void testFunctionTokenClass() {
        FunctionToken functionToken = new FunctionToken();
        assertTrue(functionToken.equals(new FunctionToken()));
        assertEquals(27, functionToken.hashCode());
        assertEquals("FunctionToken", functionToken.toString());
    }

    @Test
    public void testReturnTokenClass() {
        ReturnToken returnToken = new ReturnToken();
        assertTrue(returnToken.equals(new ReturnToken()));
        assertEquals(19, returnToken.hashCode());
        assertEquals("ReturnToken", returnToken.toString());
    }

    @Test
    public void testVariableToken() {
        VarToken var = new VarToken("var");
        assertTrue(var.equals(new VarToken("var")));
        assertEquals("VarToken", var.toString());
        assertEquals(31, var.hashCode());
    }

    @Test
    public void testDivideTokenClass() {
        DivideToken divideToken = new DivideToken();
        assertTrue(divideToken.equals(new DivideToken()));
        assertEquals(6, divideToken.hashCode());
        assertEquals("DivideToken", divideToken.toString());
    }

    @Test
    public void testElseTokenClass() {
        ElseToken elseToken = new ElseToken();
        assertTrue(elseToken.equals(new ElseToken()));
        assertEquals(2, elseToken.hashCode());
        assertEquals("ElseToken", elseToken.toString());
    }

    @Test
    public void testMinusTokenClass() {
        MinusToken minusToken = new MinusToken();
        assertTrue(minusToken.equals(new MinusToken()));
        assertEquals(4, minusToken.hashCode());
        assertEquals("MinusToken", minusToken.toString());
    }

    @Test
    public void testTimesTokenClass() {
        TimesToken timesToken = new TimesToken();
        assertTrue(timesToken.equals(new TimesToken()));
        assertEquals(5, timesToken.hashCode());
        assertEquals("TimesToken", timesToken.toString());
    }

    @Test
    public void testGreaterthan() {
        GreaterThanToken greaterThanToken = new GreaterThanToken();
        assertTrue(greaterThanToken.equals(new GreaterThanToken()));
        assertEquals(34, greaterThanToken.hashCode());
        assertEquals("GreaterThanToken", greaterThanToken.toString());
    }

    @Test
    public void GreaterThanEqualtoTokentest() {
        GreaterThanequaltoToken greaterThanequaltoToken = new GreaterThanequaltoToken();
        assertTrue(greaterThanequaltoToken.equals(new GreaterThanequaltoToken()));
        assertEquals(36, greaterThanequaltoToken.hashCode());
        assertEquals("GreaterThanequaltoToken", greaterThanequaltoToken.toString());
    }

    @Test
    public void LessThanEqualtoTokentest() {
        LessThanequaltoToken LessThanEqualtoToken = new LessThanequaltoToken();
        assertTrue(LessThanEqualtoToken.equals(new LessThanequaltoToken()));
        assertEquals(35, LessThanEqualtoToken.hashCode());
        assertEquals("LessThanequaltoToken", LessThanEqualtoToken.toString());
    }

    @Test
    public void testIfTokenClass() {
        IfToken ifToken = new IfToken();
        assertTrue(ifToken.equals(new IfToken()));
        assertEquals(1, ifToken.hashCode());
        assertEquals("IfToken", ifToken.toString());
    }

    @Test
    public void testLeftCurlyBracketTokenClass() {
        LeftCurlyToken leftCurlyBracketToken = new LeftCurlyToken();
        assertTrue(leftCurlyBracketToken.equals(new LeftCurlyToken()));
        assertEquals(14, leftCurlyBracketToken.hashCode());
        assertEquals("LeftCurlyToken", leftCurlyBracketToken.toString());
    }

    @Test
    public void testRightCurlyBracketTokenClass() {
        RightCurlyToken rightCurlyBracketToken = new RightCurlyToken();
        assertTrue(rightCurlyBracketToken.equals(new RightCurlyToken()));
        assertEquals(15, rightCurlyBracketToken.hashCode());
        assertEquals("RightCurlyToken", rightCurlyBracketToken.toString());
    }

    @Test
    public void testPlusTokenClass() {
        PlusToken plusToken = new PlusToken();
        assertTrue(plusToken.equals(new PlusToken()));
        assertEquals(3, plusToken.hashCode());
        assertEquals("PlusToken", plusToken.toString());
    }
    
    @Test
    public void testLeftParenTokenClass() {
        LeftParenToken leftParenToken = new LeftParenToken();
        assertTrue(leftParenToken.equals(new LeftParenToken()));
        assertEquals(16, leftParenToken.hashCode());
        assertEquals("LeftParenToken", leftParenToken.toString());
    }

    @Test
    public void testEqualsTokenClass() {
        EqualsToken equalsToken = new EqualsToken();
        assertTrue(equalsToken.equals(new EqualsToken()));
        assertEquals(7, equalsToken.hashCode());
        assertEquals("EqualsToken", equalsToken.toString());
    }

    @Test
    public void testPrintTokenClass() {
        PrintToken printToken = new PrintToken();
        assertTrue(printToken.equals(new PrintToken()));
        assertEquals(22, printToken.hashCode());
        assertEquals("PrintToken", printToken.toString());
    }

    @Test
    public void testLessthan() {
        LessThanToken lessThanToken = new LessThanToken();
        assertTrue(lessThanToken.equals(new LessThanToken()));
        assertEquals(33, lessThanToken.hashCode());
        assertEquals("LessThanToken", lessThanToken.toString());
    }
}