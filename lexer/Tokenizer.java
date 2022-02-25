package lexer;

import java.util.List;
import java.util.ArrayList;

/* tokens that need to be implemented, the hash code it return must match its position on this list to avoid duplicate hash codes

// add items to end of list
// mark done when implemented

1: IfToken - done
2: ElseToken - done
3: + PlusToken - done
4; - MinusToken - done
5: * TimesToken - done
6: / DivideToken - done
7: = EqualsToken - done
8: NewToken - done
9: IntegerToken - done
10: StringToken - done
11: BooleanToken - done
12: [ LeftSqrBrktToken - done
13: ] RighttSqrBrktToken - done
14: { LeftCurlyToken - done
15: } RightCurlyToken - done
16: ( LeftParenToken - done
17: ) RightParenToken - done
18: WhileToken  - done
19: ReturnToken - done
20: BreakToken - done
21: ; SemiColonToken - done
22: PrintToken - done
23: ExtendsToken - done
24: TrueToken - done
25: FalseToken - done
26: ClassToken - done
27: FunctionToken - done
28: CommaToken - done
29: PeriodToken - done
30: ThisToken - done


/// items below may not need to be done since not in the syntax
 >
 <
 ==
 &&
 != 
 !
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

            if (input.startsWith("if", offset)) {
                offset += 2;
                return new IfToken();
            } else if (input.startsWith("else", offset)) {
                offset += 4;
                return new ElseToken();
            } else if (input.startsWith("+", offset)) {
                offset += 1;
                return new PlusToken();
            } else if (input.startsWith("-", offset)) {
                offset += 1;
                return new MinusToken();
            } else if (input.startsWith("*", offset)) {
                offset += 1;
                return new TimesToken();
            } else if (input.startsWith("/", offset)) {
                offset += 1;
                return new DivideToken();
            } else if (input.startsWith("=", offset)) {
                offset += 1;
                return new EqualsToken();
            } else if (input.startsWith("new", offset)) {
                offset += 3;
                return new NewToken();
            } else if (input.startsWith("Int", offset)) {
                offset += 3;
                return new IntegerToken();
            } else if (input.startsWith("Str", offset)) {
                offset += 3;
                return new StringToken();
            } else if (input.startsWith("Bool", offset)) {
                offset += 4;
                return new BooleanToken();
            } else if (input.startsWith("[", offset)) {
                offset += 1;
                return new LeftSqrBrktToken();
            } else if (input.startsWith("]", offset)) {
                offset += 1;
                return new RightSqrBrktToken();
            } else if (input.startsWith("{", offset)) {
                offset += 1;
                return new LeftCurlyToken();
            } else if (input.startsWith("}", offset)) {
                offset += 1;
                return new RightCurlyToken();
            } else if (input.startsWith("(", offset)) {
                offset += 1;
                return new LeftParenToken();
            } else if (input.startsWith(")", offset)) {
                offset += 1;
                return new RightParenToken();
            } else if (input.startsWith("while", offset)) {
                offset += 5;
                return new WhileToken();
            } else if (input.startsWith("return", offset)) {
                offset += 6;
                return new ReturnToken();
            } else if (input.startsWith("break", offset)) {
                offset += 5;
                return new BreakToken();
            } else if (input.startsWith(";", offset)) {
                offset += 1;
                return new SemiColonToken();
            } else if (input.startsWith("print", offset)) {
                offset += 5;
                return new PrintToken();
            } else if (input.startsWith("extends", offset)) {
                offset += 7;
                return new ExtendsToken();
            } else if (input.startsWith("true", offset)) {
                offset += 4;
                return new TrueToken();
            } else if (input.startsWith("false", offset)) {
                offset += 5;
                return new FalseToken();
            } else if (input.startsWith("class", offset)) {
                offset += 5;
                return new ClassToken();
            } else if (input.startsWith("function", offset)) {
                offset += 8;
                return new FunctionToken();
            } else if (input.startsWith(",", offset)) {
                offset += 1;
                return new CommaToken();
            } else if (input.startsWith(".", offset)) {
                offset += 1;
                return new PeriodToken();
            } else if (input.startsWith("this", offset)) {
                offset += 4;
                return new ThisToken();
            }

            else {
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
