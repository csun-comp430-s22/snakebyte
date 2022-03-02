package lexer;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class TokenizerTest{
    @Test
    public void testTrueItSelf() throws TokennizerException{
        Tokenizer tokenizer = new Tokenizer("true");
        List<Token> tokens = tokenizer.tokenize();
        assertEquals(1, tokens.size());
        Token trueToken = tokens.get(0);
        assertTrue(trueToken instanceof TrueToken);
    }
}