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
31: VarToken - done
32: SuperToken - done
33: < LessThanToken- done
34: > GreaterThanToken - done
/// items below may not need to be done since not in the syntax
 >
 <
 ==
 &&
 != 
 !
*/

//*******COPY OF THE ORIGIN CODE */
// public Token tokenizeSingle() throws TokenizerException {
//         Token retval = null;
//         skipWhitespace();
//         if (offset < input.length()) {
//             retval = tryTokenizerVar();
//             if (retval == null) {
//                 if (input.startsWith("+", offset)) {
//                     offset += 1;
//                     retval = new PlusToken();
//                 } else if (input.startsWith("-", offset)) {
//                     offset += 1;
//                     retval = new MinusToken();
//                 } else if (input.startsWith("*", offset)) {
//                     offset += 1;
//                     retval = new TimesToken();
//                 } else if (input.startsWith("/", offset)) {
//                     offset += 1;
//                     retval = new DivideToken();
//                 } else if (input.startsWith("=", offset)) {
//                     offset += 1;
//                     retval = new EqualsToken();
//                 } else if (input.startsWith("[", offset)) {
//                     offset += 1;
//                     retval = new LeftSqrBrktToken();
//                 } else if (input.startsWith("]", offset)) {
//                     offset += 1;
//                     retval = new RightSqrBrktToken();
//                 } else if (input.startsWith("{", offset)) {
//                     offset += 1;
//                     retval = new LeftCurlyToken();
//                 } else if (input.startsWith("}", offset)) {
//                     offset += 1;
//                     retval = new RightCurlyToken();
//                 } else if (input.startsWith("(", offset)) {
//                     offset += 1;
//                     retval = new LeftParenToken();
//                 } else if (input.startsWith(")", offset)) {
//                     offset += 1;
//                     retval = new RightParenToken();
//                 } else if (input.startsWith(";", offset)) {
//                     offset += 1;
//                     retval = new SemiColonToken();
//                 } else if (input.startsWith(",", offset)) {
//                     offset += 1;
//                     retval = new CommaToken();
//                 } else if (input.startsWith(".", offset)) {
//                     offset += 1;
//                     retval = new PeriodToken();
//                 } else {
//                     throw new TokenizerException();
//                 }
//             }
//         }
//         return retval;
//     }
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

    public IntegerValue tryTokenizeInteger() {
        skipWhitespace();

        // 12345
        String number = "";

        while (offset < input.length() &&
                Character.isDigit(input.charAt(offset))) {
            number += input.charAt(offset);
            offset++;
        }

        if (number.length() > 0) {
            // convert string to an integer
            return new IntegerValue(Integer.parseInt(number));
        } else {
            return null;
        }
    }

    public StringValue tryTokenizeString() {
        skipWhitespace();
        String string = "";
        Boolean stringEnded = false;

        if (offset < input.length() && input.charAt(offset) == '"') {
            string += input.charAt(offset);
            offset++;
            while (offset < input.length()) {
                if (input.charAt(offset) != '"') {
                    string += input.charAt(offset);
                    offset++;
                } else {
                    if (string.charAt(string.length() - 1) == '\\') {
                        string += input.charAt(offset);
                        offset++;
                    } else {
                        string += input.charAt(offset);
                        offset++;
                        stringEnded = true;
                        break;
                    }
                }
            }

            if (stringEnded)
                return new StringValue(string);
        }
        return null;
    }

    public Token tryTokenizerVar() {
        skipWhitespace();
        String name = "";
        // check if the name is special, if true emit a special token
        // otherwise emit a variable token
        // first letter must be a letter
        // every other letter must be a letter or a digit
        if (offset < input.length() && Character.isLetter(input.charAt(offset))) {
            name += input.charAt(offset);
            offset++;
            while (offset < input.length() &&
                    Character.isLetterOrDigit(input.charAt(offset))) {
                name += input.charAt(offset);
                offset++;
            }
            // name is hold a possible variable name
            // name could be "true"
            if (name.equals("true")) {
                return new TrueToken();
            } else if (name.equals("false")) {
                return new FalseToken();
            } else if (name.equals("this")) {
                return new ThisToken();
            } else if (name.equals("if")) {
                return new IfToken();
            } else if (name.equals("else")) {
                return new ElseToken();
            } else if (name.equals("while")) {
                return new WhileToken();
            } else if (name.equals("break")) {
                return new BreakToken();
            } else if (name.equals("return")) {
                return new ReturnToken();
            } else if (name.equals("print")) {
                return new PrintToken();
            } else if (name.equals("extends")) {
                return new ExtendsToken();
            } else if (name.equals("class")) {
                return new ClassToken();
            } else if (name.equals("function")) {
                return new FunctionToken();
            } else if (name.equals("new")) {
                return new NewToken();
            } else if (name.equals("Bool")) {
                return new BooleanToken();
            } else if (name.equals("Int")) {
                return new IntegerToken();
            } else if (name.equals("Str")) {
                return new StringToken();
            } else if (name.equals("Super")) {
                return new SuperToken();
            } else {
                return new VarToken(name);
            }

        } else {
            return null;
        }
        // return new VarToken(name);
    }

    public Token tokenizeSymbol() throws TokenizerException {
        Token retval = null;
        skipWhitespace();
        if (offset < input.length()) {
            retval = tryTokenizerVar();
            if (retval == null) {
                if (input.startsWith("+", offset)) {
                    offset += 1;
                    retval = new PlusToken();
                } else if (input.startsWith("-", offset)) {
                    offset += 1;
                    retval = new MinusToken();
                } else if (input.startsWith("*", offset)) {
                    offset += 1;
                    retval = new TimesToken();
                } else if (input.startsWith("/", offset)) {
                    offset += 1;
                    retval = new DivideToken();
                } else if (input.startsWith("=", offset)) {
                    offset += 1;
                    retval = new EqualsToken();
                } else if (input.startsWith("[", offset)) {
                    offset += 1;
                    retval = new LeftSqrBrktToken();
                } else if (input.startsWith("]", offset)) {
                    offset += 1;
                    retval = new RightSqrBrktToken();
                } else if (input.startsWith("{", offset)) {
                    offset += 1;
                    retval = new LeftCurlyToken();
                } else if (input.startsWith("}", offset)) {
                    offset += 1;
                    retval = new RightCurlyToken();
                } else if (input.startsWith("(", offset)) {
                    offset += 1;
                    retval = new LeftParenToken();
                } else if (input.startsWith(")", offset)) {
                    offset += 1;
                    retval = new RightParenToken();
                } else if (input.startsWith(";", offset)) {
                    offset += 1;
                    retval = new SemiColonToken();
                } else if (input.startsWith(",", offset)) {
                    offset += 1;
                    retval = new CommaToken();
                } else if (input.startsWith(".", offset)) {
                    offset += 1;
                    retval = new PeriodToken();
                }
                else if (input.startsWith("<", offset)) {
                    offset += 1;
                    retval = new LessThanToken();
                }
                else if (input.startsWith(">", offset)) {
                    offset += 1;
                    retval = new GreaterThanToken();
                }
                // } else {
                // throw new TokenizerException();
                // }
            }
        }
        return retval;
    }

    public Token tokenizeSingle() throws TokenizerException {
        Token retval = null;
        skipWhitespace();
        if (offset < input.length() &&
                ((retval = tryTokenizeString()) == null &&
                        (retval = tryTokenizeInteger()) == null &&
                        (retval = tokenizeSymbol()) == null &&
                        (retval = tryTokenizerVar()) == null)) {
            throw new TokenizerException();
        }
        return retval;
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
