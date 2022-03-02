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
     //test TrueToken.java class for code coverage
    @Test
    public void testTrueToken() throws TokenizerException {
        TrueToken trueToken = new TrueToken();
        assertTrue(trueToken.equals(new TrueToken()));
        assertEquals(24, trueToken.hashCode());
        assertEquals("true", trueToken.toString());
    }
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