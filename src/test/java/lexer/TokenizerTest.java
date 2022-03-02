package lexer;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class TokenizerTest{
    /*
    "true" -- pass
    " true" -- pass
    "true " -- pass
    " true " -- pass
    "truefalse" -- pass
    " truefalse" -- pass
    "truefalse " -- pass
    " truefalse " -- pass
    "true false" -- pass
    "(true)" -- pass
    "(true" -- pass
    "true)" -- pass
     */
     //test token classes
     //test TrueToken.java class for code coverage
    @Test
    public void testIfToeknClass(){
        IfToken ifToken = new IfToken();
        assertTrue(ifToken.equals(new IfToken()));
        assertEquals(1,ifToken.hashCode());
        assertEquals("if",ifToken.toString());
    }
    @Test
    public void testElseTokenClass(){
        ElseToken elseToken = new ElseToken();
        assertTrue(elseToken.equals(new ElseToken()));
        assertEquals(2,elseToken.hashCode());
        assertEquals("else",elseToken.toString());
    }
    @Test
    public void testPlusTokenClass(){
        PlusToken plusToken = new PlusToken();
        assertTrue(plusToken.equals(new PlusToken()));
        assertEquals(3,plusToken.hashCode());
        assertEquals("+",plusToken.toString());
    }
    @Test
    public void testMinusTokenClass(){
        MinusToken minusToken = new MinusToken();
        assertTrue(minusToken.equals(new MinusToken()));
        assertEquals(4,minusToken.hashCode());
        assertEquals("-",minusToken.toString());
    }
    @Test
    public void testTimesTokenClass(){
        TimesToken timesToken = new TimesToken();
        assertTrue(timesToken.equals(new TimesToken()));
        assertEquals(5,timesToken.hashCode());
        assertEquals("*",timesToken.toString());
    }
    @Test
    public void testDivideTokenClass(){
        DivideToken divideToken = new DivideToken();
        assertTrue(divideToken.equals(new DivideToken()));
        assertEquals(6,divideToken.hashCode());
        assertEquals("/",divideToken.toString());
    }
    @Test
    public void testEqualsTokenClass(){
        EqualsToken equalsToken = new EqualsToken();
        assertTrue(equalsToken.equals(new EqualsToken()));
        assertEquals(7,equalsToken.hashCode());
        assertEquals("=",equalsToken.toString());
    }
    @Test
    public void testNewTokenClass(){
        NewToken newToken = new NewToken();
        assertTrue(newToken.equals(new NewToken()));
        assertEquals(8,newToken.hashCode());
        assertEquals("new",newToken.toString());
    }
    @Test
    public void testIntegerTokenClass(){
        IntegerToken integerToken = new IntegerToken();
        assertTrue(integerToken.equals(new IntegerToken()));
        assertEquals(9,integerToken.hashCode());
        assertEquals("Int",integerToken.toString());
    }
    @Test
    public void testStringTokenClass(){
        StringToken stringToken = new StringToken();
        assertTrue(stringToken.equals(new StringToken()));
        assertEquals(10,stringToken.hashCode());
        assertEquals("Str",stringToken.toString());
    }
    @Test
    public void testBoolTokenClass(){
        BooleanToken boolToken = new BooleanToken();
        assertTrue(boolToken.equals(new BooleanToken()));
        assertEquals(11,boolToken.hashCode());
        assertEquals("Bool",boolToken.toString());
    }
    @Test
    public void testLeftSqrBracketTokenClass(){
        LeftSqrBrktToken leftSqrBracketToken = new LeftSqrBrktToken();
        assertTrue(leftSqrBracketToken.equals(new LeftSqrBrktToken()));
        assertEquals(12,leftSqrBracketToken.hashCode());
        assertEquals("[",leftSqrBracketToken.toString());
    }
    @Test
    public void testRightSqrBracketTokenClass(){
        RightSqrBrktToken rightSqrBracketToken = new RightSqrBrktToken();
        assertTrue(rightSqrBracketToken.equals(new RightSqrBrktToken()));
        assertEquals(13,rightSqrBracketToken.hashCode());
        assertEquals("]",rightSqrBracketToken.toString());
    }
    @Test
    public void testLeftCurlyBracketTokenClass(){
        LeftCurlyToken leftCurlyBracketToken = new LeftCurlyToken();
        assertTrue(leftCurlyBracketToken.equals(new LeftCurlyToken()));
        assertEquals(14,leftCurlyBracketToken.hashCode());
        assertEquals("{",leftCurlyBracketToken.toString());
    }
    @Test
    public void testRightCurlyBracketTokenClass(){
        RightCurlyToken rightCurlyBracketToken = new RightCurlyToken();
        assertTrue(rightCurlyBracketToken.equals(new RightCurlyToken()));
        assertEquals(15,rightCurlyBracketToken.hashCode());
        assertEquals("}",rightCurlyBracketToken.toString());
    }
    @Test
    public void testLeftParenTokenClass(){
        LeftParenToken leftParenToken = new LeftParenToken();
        assertTrue(leftParenToken.equals(new LeftParenToken()));
        assertEquals(16,leftParenToken.hashCode());
        assertEquals("(",leftParenToken.toString());
    }
    @Test
    public void testRightParenTokenClass(){
        RightParenToken rightParenToken = new RightParenToken();
        assertTrue(rightParenToken.equals(new RightParenToken()));
        assertEquals(17,rightParenToken.hashCode());
        assertEquals(")",rightParenToken.toString());
    }
    @Test
    public void testWhileTokenClass(){
        WhileToken whileToken = new WhileToken();
        assertTrue(whileToken.equals(new WhileToken()));
        assertEquals(18,whileToken.hashCode());
        assertEquals("while",whileToken.toString());
    }
    @Test
    public void testReturnTokenClass(){
        ReturnToken returnToken = new ReturnToken();
        assertTrue(returnToken.equals(new ReturnToken()));
        assertEquals(19,returnToken.hashCode());
        assertEquals("return",returnToken.toString());
    }
    @Test
    public void testBreakTokenClass(){
        BreakToken breakToken = new BreakToken();
        assertTrue(breakToken.equals(new BreakToken()));
        assertEquals(20,breakToken.hashCode());
        assertEquals("break",breakToken.toString());
    }
    @Test
    public void testSemiColonTokenClass(){
        SemiColonToken semiColonToken = new SemiColonToken();
        assertTrue(semiColonToken.equals(new SemiColonToken()));
        assertEquals(21,semiColonToken.hashCode());
        assertEquals(";",semiColonToken.toString());
    }
    @Test
    public void testPrintTokenClass(){
        PrintToken printToken = new PrintToken();
        assertTrue(printToken.equals(new PrintToken()));
        assertEquals(22,printToken.hashCode());
        assertEquals("print",printToken.toString());
    }
    @Test
    public void testExtendsTokenClass(){
        ExtendsToken extendsToken = new ExtendsToken();
        assertTrue(extendsToken.equals(new ExtendsToken()));
        assertEquals(23,extendsToken.hashCode());
        assertEquals("extends",extendsToken.toString());
    }
    @Test
    public void testTrueTokenClass(){
        TrueToken trueToken = new TrueToken();
        assertTrue(trueToken.equals(new TrueToken()));
        assertEquals(24,trueToken.hashCode());
        assertEquals("true",trueToken.toString());
    }
    @Test
    public void testFalseTokenClass(){
        FalseToken falseToken = new FalseToken();
        assertTrue(falseToken.equals(new FalseToken()));
        assertEquals(25,falseToken.hashCode());
        assertEquals("false",falseToken.toString());
    }
    @Test
    public void testClassTokenClass(){
        ClassToken classToken = new ClassToken();
        assertTrue(classToken.equals(new ClassToken()));
        assertEquals(26,classToken.hashCode());
        assertEquals("class",classToken.toString());
    }   
    @Test
    public void testFunctionTokenClass(){
        FunctionToken functionToken = new FunctionToken();
        assertTrue(functionToken.equals(new FunctionToken()));
        assertEquals(27,functionToken.hashCode());
        assertEquals("function",functionToken.toString());
    }
    @Test
    public void testCommaTokenClass(){
        CommaToken commaToken = new CommaToken();
        assertTrue(commaToken.equals(new CommaToken()));
        assertEquals(28,commaToken.hashCode());
        assertEquals(",",commaToken.toString());
    }
    @Test
    public void testDotTokenClass(){
        PeriodToken dotToken = new PeriodToken();
        assertTrue(dotToken.equals(new PeriodToken()));
        assertEquals(29,dotToken.hashCode());
        assertEquals(".",dotToken.toString());
    }
    @Test
    public void testThisTokenClass(){
        ThisToken thisToken = new ThisToken();
        assertTrue(thisToken.equals(new ThisToken()));
        assertEquals(30,thisToken.hashCode());
        assertEquals("this",thisToken.toString());
    }



    ////test token with different cases
    @Test
    public void testTrueItSelf() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("true");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueEmptyBefore() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer(" true");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueEmptyAfter() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("true ");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueEmptyBeforeAndAfter() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer(" true ");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    //test case : "truetrue"
    @Test
    public void testTrueAndFalse() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("truefalse");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueAndFalseEmptyBefore() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer(" truefalse");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueAndFalseEmptyAfter() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("truefalse ");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);

    }
    @Test
    public void testTrueAndFalseEmptyBeforeAndAfter() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer(" truefalse ");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueAndFalseEmptyBetween() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("true false");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueLeftParentheses() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("(true");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(1);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueRightParentheses() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("true)");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(2, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
    @Test
    public void testTrueLeftParenthesesAndRightParentheses() throws TokenizerException{
        Tokenizer tokenizer = new Tokenizer("(true)");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(3, tokens.size());
        Token trueToken = tokens.get(1);
        assertTrue(trueToken instanceof TrueToken);
    }

}