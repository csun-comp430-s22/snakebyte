package typechecker.parser;

public class StringLiteralExp implements Expression {
    final String value;

    public StringLiteralExp(final String value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        if (other instanceof StringLiteralExp) {
            final StringLiteralExp asString = (StringLiteralExp) other;
            return value.equals(asString.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return "String Literal(" + value + ")";
    }
}
