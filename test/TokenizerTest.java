package lexer;
import org.junit.Test.Assert.assertEquals;
public class TokenizerTest {
    @Test
    public void testTrue() {
        Tokenizer tokenizer = new Tokenizer("true");
        assertEquals(1, tokenizer.getOffset());
        Assert.assertTrue(tokenizer instanceof TrueToken);
    }
}