package codegenerator.lexer;
import parser.Token;
public class IntegerTokenCodeGen implements Token{
    public final int value;

    public IntegerTokenCodeGen(final int value) {
        this.value = value;
    }

    public int hashCode() {
        return value;
    }

    public boolean equals(final Object other) {
        return (other instanceof IntegerTokenCodeGen &&
                value == ((IntegerTokenCodeGen)other).value);
    }

    public String toString() {
        return "IntegerToken(" + value + ")";
    }
}
