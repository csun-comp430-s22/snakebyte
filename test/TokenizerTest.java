package lexer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class TokenizerTest throws TokennizerException {
    @Test
    public void testTrue() throws TokennizerException{
        Tokenizer tokenizer = new Tokenizer("true");
        assertEquals(1, tokenizer.getOffset());
        Assert.assertTrue(tokenizer instanceof TrueToken);
    }
}