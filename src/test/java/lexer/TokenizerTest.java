package lexer;

import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThrows;
import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest {
    // test token classes
    // test TrueToken.java class for code coverage
    @Test
    public void testVariableToken() {
        VarToken var = new VarToken("var");
        assertEquals("Variable(var)", var.toString());
        assertEquals(var.hashCode(), "var".hashCode());
    }

    @Test
    public void testIfTokenClass() {
        IfToken ifToken = new IfToken();
        assertTrue(ifToken.equals(new IfToken()));
        assertEquals(1, ifToken.hashCode());
        assertEquals("if", ifToken.toString());
    }

    @Test
    public void testStringValue() throws TokenizerException {
        asserTokenizes("\"hello\"", new Token[] { new StringValue("\"hello\"") });
    }

    @Test
    public void testStringValueWithEscape() throws TokenizerException {
        asserTokenizes("\"he\\\"llo\"", new Token[] { new StringValue("\"he\\\"llo\"")
        });
    }

    @Test
    public void testElseTokenClass() {
        ElseToken elseToken = new ElseToken();
        assertTrue(elseToken.equals(new ElseToken()));
        assertEquals(2, elseToken.hashCode());
        assertEquals("else", elseToken.toString());
    }

    @Test
    public void testPlusTokenClass() {
        PlusToken plusToken = new PlusToken();
        assertTrue(plusToken.equals(new PlusToken()));
        assertEquals(3, plusToken.hashCode());
        assertEquals("+", plusToken.toString());
    }

    @Test
    public void testMinusTokenClass() {
        MinusToken minusToken = new MinusToken();
        assertTrue(minusToken.equals(new MinusToken()));
        assertEquals(4, minusToken.hashCode());
        assertEquals("-", minusToken.toString());
    }

    @Test
    public void testTimesTokenClass() {
        TimesToken timesToken = new TimesToken();
        assertTrue(timesToken.equals(new TimesToken()));
        assertEquals(5, timesToken.hashCode());
        assertEquals("*", timesToken.toString());
    }

    @Test
    public void testDivideTokenClass() {
        DivideToken divideToken = new DivideToken();
        assertTrue(divideToken.equals(new DivideToken()));
        assertEquals(6, divideToken.hashCode());
        assertEquals("/", divideToken.toString());
    }

    @Test
    public void testEqualsTokenClass() {
        EqualsToken equalsToken = new EqualsToken();
        assertTrue(equalsToken.equals(new EqualsToken()));
        assertEquals(7, equalsToken.hashCode());
        assertEquals("=", equalsToken.toString());
    }

    @Test
    public void testNewTokenClass() {
        NewToken newToken = new NewToken();
        assertTrue(newToken.equals(new NewToken()));
        assertEquals(8, newToken.hashCode());
        assertEquals("new", newToken.toString());
    }

    @Test
    public void testIntegerTokenClass() {
        IntegerToken integerToken = new IntegerToken();
        assertTrue(integerToken.equals(new IntegerToken()));
        assertEquals(9, integerToken.hashCode());
        assertEquals("Int", integerToken.toString());
    }

    @Test
    public void testStringTokenClass() {
        StringToken stringToken = new StringToken();
        assertTrue(stringToken.equals(new StringToken()));
        assertEquals(10, stringToken.hashCode());
        assertEquals("Str", stringToken.toString());
    }

    @Test
    public void testBoolTokenClass() {
        BooleanToken boolToken = new BooleanToken();
        assertTrue(boolToken.equals(new BooleanToken()));
        assertEquals(11, boolToken.hashCode());
        assertEquals("Bool", boolToken.toString());
    }

    @Test
    public void testLeftSqrBracketTokenClass() {
        LeftSqrBrktToken leftSqrBracketToken = new LeftSqrBrktToken();
        assertTrue(leftSqrBracketToken.equals(new LeftSqrBrktToken()));
        assertEquals(12, leftSqrBracketToken.hashCode());
        assertEquals("[", leftSqrBracketToken.toString());
    }

    @Test
    public void testRightSqrBracketTokenClass() {
        RightSqrBrktToken rightSqrBracketToken = new RightSqrBrktToken();
        assertTrue(rightSqrBracketToken.equals(new RightSqrBrktToken()));
        assertEquals(13, rightSqrBracketToken.hashCode());
        assertEquals("]", rightSqrBracketToken.toString());
    }

    @Test
    public void testLeftCurlyBracketTokenClass() {
        LeftCurlyToken leftCurlyBracketToken = new LeftCurlyToken();
        assertTrue(leftCurlyBracketToken.equals(new LeftCurlyToken()));
        assertEquals(14, leftCurlyBracketToken.hashCode());
        assertEquals("{", leftCurlyBracketToken.toString());
    }

    @Test
    public void testRightCurlyBracketTokenClass() {
        RightCurlyToken rightCurlyBracketToken = new RightCurlyToken();
        assertTrue(rightCurlyBracketToken.equals(new RightCurlyToken()));
        assertEquals(15, rightCurlyBracketToken.hashCode());
        assertEquals("}", rightCurlyBracketToken.toString());
    }

    @Test
    public void testLeftParenTokenClass() {
        LeftParenToken leftParenToken = new LeftParenToken();
        assertTrue(leftParenToken.equals(new LeftParenToken()));
        assertEquals(16, leftParenToken.hashCode());
        assertEquals("(", leftParenToken.toString());
    }

    @Test
    public void testRightParenTokenClass() {
        RightParenToken rightParenToken = new RightParenToken();
        assertTrue(rightParenToken.equals(new RightParenToken()));
        assertEquals(17, rightParenToken.hashCode());
        assertEquals(")", rightParenToken.toString());
    }

    @Test
    public void testWhileTokenClass() {
        WhileToken whileToken = new WhileToken();
        assertTrue(whileToken.equals(new WhileToken()));
        assertEquals(18, whileToken.hashCode());
        assertEquals("while", whileToken.toString());
    }

    @Test
    public void testReturnTokenClass() {
        ReturnToken returnToken = new ReturnToken();
        assertTrue(returnToken.equals(new ReturnToken()));
        assertEquals(19, returnToken.hashCode());
        assertEquals("return", returnToken.toString());
    }

    @Test
    public void testBreakTokenClass() {
        BreakToken breakToken = new BreakToken();
        assertTrue(breakToken.equals(new BreakToken()));
        assertEquals(20, breakToken.hashCode());
        assertEquals("break", breakToken.toString());
    }

    @Test
    public void testSemiColonTokenClass() {
        SemiColonToken semiColonToken = new SemiColonToken();
        assertTrue(semiColonToken.equals(new SemiColonToken()));
        assertEquals(21, semiColonToken.hashCode());
        assertEquals(";", semiColonToken.toString());
    }

    @Test
    public void testPrintTokenClass() {
        PrintToken printToken = new PrintToken();
        assertTrue(printToken.equals(new PrintToken()));
        assertEquals(22, printToken.hashCode());
        assertEquals("print", printToken.toString());
    }

    @Test
    public void testExtendsTokenClass() {
        ExtendsToken extendsToken = new ExtendsToken();
        assertTrue(extendsToken.equals(new ExtendsToken()));
        assertEquals(23, extendsToken.hashCode());
        assertEquals("extends", extendsToken.toString());
    }

    @Test
    public void testTrueTokenClass() {
        TrueToken trueToken = new TrueToken();
        assertTrue(trueToken.equals(new TrueToken()));
        assertEquals(24, trueToken.hashCode());
        assertEquals("true", trueToken.toString());
    }

    @Test
    public void testFalseTokenClass() {
        FalseToken falseToken = new FalseToken();
        assertTrue(falseToken.equals(new FalseToken()));
        assertEquals(25, falseToken.hashCode());
        assertEquals("false", falseToken.toString());
    }

    @Test
    public void testClassTokenClass() {
        ClassToken classToken = new ClassToken();
        assertTrue(classToken.equals(new ClassToken()));
        assertEquals(26, classToken.hashCode());
        assertEquals("class", classToken.toString());
    }

    @Test
    public void testFunctionTokenClass() {
        FunctionToken functionToken = new FunctionToken();
        assertTrue(functionToken.equals(new FunctionToken()));
        assertEquals(27, functionToken.hashCode());
        assertEquals("function", functionToken.toString());
    }

    @Test
    public void testCommaTokenClass() {
        CommaToken commaToken = new CommaToken();
        assertTrue(commaToken.equals(new CommaToken()));
        assertEquals(28, commaToken.hashCode());
        assertEquals(",", commaToken.toString());
    }

    @Test
    public void testDotTokenClass() {
        PeriodToken dotToken = new PeriodToken();
        assertTrue(dotToken.equals(new PeriodToken()));
        assertEquals(29, dotToken.hashCode());
        assertEquals(".", dotToken.toString());
    }

    @Test
    public void testThisTokenClass() {
        ThisToken thisToken = new ThisToken();
        assertTrue(thisToken.equals(new ThisToken()));
        assertEquals(30, thisToken.hashCode());
        assertEquals("this", thisToken.toString());
    }

    @Test
    public void testIntegerValueTokenClass() {
        IntegerValue integerValue = new IntegerValue(1);
        assertTrue(integerValue.equals(new IntegerValue(1)));
        assertFalse(integerValue.equals(new IntegerToken()));
        assertEquals(1, integerValue.hashCode());
        assertEquals("IntegerValue(1)", integerValue.toString());
    }

    @Test
    public void testStringValueTokenClass() {
        StringValue stringValue = new StringValue("hello");
        assertTrue(stringValue.equals(new StringValue("hello")));
        assertFalse(stringValue.equals(new StringToken()));
        assertEquals("hello".hashCode(), stringValue.hashCode());
        assertEquals("StringValue(hello)", stringValue.toString());
    }

    // original code
    // public void asserTokenizes(final String input, final Token[] expected) throws
    // TokenizerException{
    // try {
    // final Tokenizer tokenizer = new Tokenizer(input);
    // final List<Token> received = tokenizer.tokenize();
    // assertArrayEquals(expected, received.toArray(new Token[received.size()]));
    // } catch (final TokenizerException e) {
    // fail("Tokenizer threw exception");
    // }
    // }
    public void asserTokenizes(final String input, final Token[] expected) throws TokenizerException {
        final Tokenizer tokenizer = new Tokenizer(input);
        final List<Token> received = tokenizer.tokenize();
        assertArrayEquals(expected, received.toArray(new Token[received.size()]));
    }

    //// test token with different cases
    /*
     * test true tokens
     * "true" -- pass
     * " true" -- pass
     * "true " -- pass
     * " true " -- pass
     * "truefalse" -- fail
     * " truefalse" -- fail
     * "truefalse " -- fail
     * " truefalse " -- fail
     * "true false" -- pass
     * "(true)" -- pass
     * "(true" -- pass
     * "true)" -- pass
     */
    @Test
    public void testEmptyString() throws TokenizerException {
        asserTokenizes("", new Token[0]);
    }

    @Test
    public void testTrueItSelf() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("true");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(1, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes("true", new Token[] { new TrueToken() });
    }

    @Test
    public void testTrueEmptyBefore() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer(" true");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(1, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes(" true", new Token[] { new TrueToken() });
    }

    @Test
    public void testTrueEmptyAfter() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("true ");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(1, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes("true ", new Token[] { new TrueToken() });
    }

    @Test
    public void testTrueEmptyBeforeAndAfter() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer(" true ");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(1, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes(" true ", new Token[] { new TrueToken() });
    }

    // The following code is not working after refactored code
    // @Test(expected = TokenizerException.class)
    // public void testTokenizeSingleExceptionThrows() throws TokenizerException {
    // new Tokenizer("|||").tokenizeSingle();
    // }

    @Test
    public void testSingleIntegerValue() throws TokenizerException {
        asserTokenizes("1", new Token[] { new IntegerValue(1) });
    }

    @Test
    public void testMultiIntegerValue() throws TokenizerException {
        asserTokenizes("12345", new Token[] { new IntegerValue(12345) });
    }

    @Test(expected = TokenizerException.class)
    public void testExeception() throws TokenizerException {
        asserTokenizes("$", null);
    }

    @Test
    public void testTrueAndFalseEmptyBetween() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("true false");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(2, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        // Token falseToken = tokens.get(1);
        // assertTrue(falseToken instanceof FalseToken);
        asserTokenizes("true false", new Token[] { new TrueToken(), new FalseToken() });
    }

    @Test
    public void testTrueLeftParentheses() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("(true");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(2, tokens.size());
        // Token trueToken = tokens.get(1);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes("(true", new Token[] { new LeftParenToken(), new TrueToken() });
    }

    @Test
    public void testTrueRightParentheses() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("true)");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(2, tokens.size());
        // Token trueToken = tokens.get(0);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes("true)", new Token[] { new TrueToken(), new RightParenToken() });
    }

    @Test
    public void testTrueLeftParenthesesAndRightParentheses() throws TokenizerException {
        // Tokenizer tokenizer = new Tokenizer("(true)");
        // List<Token> tokens = tokenizer.tokenize();
        // assertEquals(3, tokens.size());
        // Token trueToken = tokens.get(1);
        // assertTrue(trueToken instanceof TrueToken);
        asserTokenizes("(true)", new Token[] { new LeftParenToken(), new TrueToken(), new RightParenToken() });
    }

    @Test
    public void testFalseEqualsAndTrue() throws TokenizerException {
        asserTokenizes("false=true", new Token[] { new FalseToken(), new EqualsToken(), new TrueToken() });
    }

    @Test
    public void testPeriodFalse() throws TokenizerException {
        asserTokenizes(".false", new Token[] { new PeriodToken(), new FalseToken() });
    }

    @Test
    public void testThisPeriodLeftAndRightParentheses() throws TokenizerException {
        asserTokenizes("this.()",
                new Token[] { new ThisToken(), new PeriodToken(), new LeftParenToken(), new RightParenToken() });
    }

    @Test
    public void testIfLeftAndRightParentheses() throws TokenizerException {
        asserTokenizes("if(  )", new Token[] { new IfToken(), new LeftParenToken(), new RightParenToken() });
    }

    @Test
    public void testPrintLeftparenthesesStringRightparentheses() throws TokenizerException {
        asserTokenizes("print(Str)",
                new Token[] { new PrintToken(), new LeftParenToken(), new StringToken(), new RightParenToken() });
    }

    @Test
    public void testIntPlusInt() throws TokenizerException {
        asserTokenizes("Int + Int", new Token[] { new IntegerToken(), new PlusToken(), new IntegerToken() });
    }

    @Test
    public void testAllTokens() throws TokenizerException {
        asserTokenizes(
                "if else + - * / = new Int Str Bool [ ] { } ( ) while return break ; print extends true false class function , . this thisIsAVariable",
                new Token[] { new IfToken(), new ElseToken(), new PlusToken(), new MinusToken(), new TimesToken(),
                        new DivideToken(), new EqualsToken(),
                        new NewToken(), new IntegerToken(), new StringToken(), new BooleanToken(),
                        new LeftSqrBrktToken(), new RightSqrBrktToken(),
                        new LeftCurlyToken(), new RightCurlyToken(), new LeftParenToken(), new RightParenToken(),
                        new WhileToken(), new ReturnToken(),
                        new BreakToken(), new SemiColonToken(), new PrintToken(), new ExtendsToken(), new TrueToken(),
                        new FalseToken(), new ClassToken(),
                        new FunctionToken(), new CommaToken(), new PeriodToken(), new ThisToken(),
                        new VarToken("thisIsAVariable") });
    }

    @Test
    public void testThis() throws TokenizerException {
        asserTokenizes("this", new Token[] { new ThisToken() });
    }

    @Test
    public void testSupertoken() throws TokenizerException {
        asserTokenizes("Super", new Token[] { new SuperToken() });
    }

    @Test
    public void testSuperToStringtoken() throws TokenizerException {
        assertEquals("Super", new SuperToken().toString());
    }

    @Test
    public void testSuperHashCodetoken() throws TokenizerException {
        assertEquals(32, new SuperToken().hashCode());
    }

    @Test
    public void testIf() throws TokenizerException {
        asserTokenizes("if", new Token[] { new IfToken() });
    }

    @Test
    public void testElse() throws TokenizerException {
        asserTokenizes("else", new Token[] { new ElseToken() });
    }

    @Test
    public void testWhile() throws TokenizerException {
        asserTokenizes("while", new Token[] { new WhileToken() });
    }

    @Test
    public void testBreak() throws TokenizerException {
        asserTokenizes("break", new Token[] { new BreakToken() });
    }

    @Test
    public void testReturn() throws TokenizerException {
        asserTokenizes("return", new Token[] { new ReturnToken() });
    }

    @Test
    public void testPrint() throws TokenizerException {
        asserTokenizes("print", new Token[] { new PrintToken() });
    }

    @Test
    public void testExtends() throws TokenizerException {
        asserTokenizes("extends", new Token[] { new ExtendsToken() });
    }

    @Test
    public void testClass() throws TokenizerException {
        asserTokenizes("class", new Token[] { new ClassToken() });
    }

    @Test
    public void testFunction() throws TokenizerException {
        asserTokenizes("function", new Token[] { new FunctionToken() });
    }

    @Test
    public void testNew() throws TokenizerException {
        asserTokenizes("new", new Token[] { new NewToken() });
    }

    @Test
    public void testBool() throws TokenizerException {
        asserTokenizes("Bool", new Token[] { new BooleanToken() });
    }

    @Test
    public void testInt() throws TokenizerException {
        asserTokenizes("Int", new Token[] { new IntegerToken() });
    }

    @Test
    public void testString() throws TokenizerException {
        asserTokenizes("Str", new Token[] { new StringToken() });
    }

    @Test
    public void testTrueTrue() throws TokenizerException {
        asserTokenizes("truetrue", new Token[] { new VarToken("truetrue") });
    }

    @Test
    public void testVarReturnFalse() throws TokenizerException {
        Token token = new VarToken("true");
        Token tokenfalse = new TrueToken();

        assertEquals(false, token.equals(tokenfalse));
    }

    @Test
    public void testWhiteSpace() throws TokenizerException {
        asserTokenizes("    ", new Token[0]);
    }

    @Test
    public void testPlus() throws TokenizerException {
        asserTokenizes("+", new Token[] { new PlusToken() });
    }

    @Test
    public void testTimes() throws TokenizerException {
        asserTokenizes("*", new Token[] { new TimesToken() });
    }

    @Test
    public void testDivide() throws TokenizerException {
        asserTokenizes("/", new Token[] { new DivideToken() });
    }

    @Test
    public void testMinus() throws TokenizerException {
        asserTokenizes("-", new Token[] { new MinusToken() });
    }

    @Test
    public void testEquals() throws TokenizerException {
        asserTokenizes("=", new Token[] { new EqualsToken() });
    }

    @Test
    public void testPeiod() throws TokenizerException {
        asserTokenizes(".", new Token[] { new PeriodToken() });
    }

    @Test
    public void testComma() throws TokenizerException {
        asserTokenizes(",", new Token[] { new CommaToken() });
    }

    @Test
    public void testLeftCurly() throws TokenizerException {
        asserTokenizes("{", new Token[] { new LeftCurlyToken() });
    }

    @Test
    public void testLeftSqr() throws TokenizerException {
        asserTokenizes("[", new Token[] { new LeftSqrBrktToken() });
    }

    @Test
    public void testRightCurly() throws TokenizerException {
        asserTokenizes("}", new Token[] { new RightCurlyToken() });
    }

    @Test
    public void testRightSqr() throws TokenizerException {
        asserTokenizes("]", new Token[] { new RightSqrBrktToken() });
    }

    @Test
    public void testSemiColon() throws TokenizerException {
        asserTokenizes(";", new Token[] { new SemiColonToken() });
    }

}