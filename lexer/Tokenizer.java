package lexer;

import java.util.List;
import java.util.ArrayList;

/* tokens that need to be implemented, the hash code it return must match its position on this list to avoid duplicate hash codes

1: IfToken - done
2: ElseToken - done
3: 
4;
5:
6:









*/
public class Tokenizer {
    private final String input;
    private int offset;

    public Tokenizer(final String input) {
        this.input = input;
        offset = 0;
    }

    public void skipWhitespace() {
        while (offset < input.length() &&
                Character.isWhitespace(input.charAt(offset))) {
            offset++;
        }
    }

    public Token tokenizeSingle() throws TokenizerException {
        skipWhitespace();
        if (offset < input.length()) {
            if (input.startsWith("true", offset)) {
                offset += 4;
                return new TrueToken();
            } else if (input.startsWith("false", offset)) {
                offset += 5;
                return new FalseToken();
            } else if (input.startsWith("if", offset)) {
                offset += 2;
                return new IfToken();
            } else if (input.startsWith("(", offset)) {
                offset += 1;
                return new LeftParenToken();
            } else if (input.startsWith(")", offset)) {
                offset += 1;
                return new RightParenToken();
            } else if (input.startsWith("{", offset)) {
                offset += 1;
                return new LeftCurlyToken();
            } else if (input.startsWith("}", offset)) {
                offset += 1;
                return new RightCurlyToken();
            } else if (input.startsWith("else", offset)) {
                offset += 4;
                return new ElseToken();
            } else {
                throw new TokenizerException();
            }
        } else {
            return null;
        }
    }

    public List<Token> tokenize() throws TokenizerException {
        final List<Token> tokens = new ArrayList<Token>();
        Token token = tokenizeSingle();

        while (token != null) {
            tokens.add(token);
            token = tokenizeSingle();
        }

        return tokens;
    }
}
