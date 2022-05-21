package codegenerator.lexer;
import parser.Token;
public class Symbol {
    public final String asString;
    public final Token asToken;

    public Symbol(final String asString,
                  final Token asToken) {
        this.asString = asString;
        this.asToken = asToken;
    }

    public int hashCode() {
        return asString.hashCode() + asToken.hashCode();
    }

    public boolean equals(final Object other) {
        if (other instanceof Symbol) {
            final Symbol asSymbol = (Symbol)other;
            return (asString.equals(asSymbol.asString) &&
                    asToken.equals(asSymbol.asToken));
        } else {
            return false;
        }
    }

    public String toString() {
        return ("Symbol(" + asString + ", " + asToken.toString() + ")");
    }
}
