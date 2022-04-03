package parser;

public class StringExp implements Expression {

    public final String value;

    public StringExp(final String value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (other instanceof StringExp &&
                value == ((StringExp) other).value);
    }

    public int hashCode() {
        return Integer.valueOf(value);
    }

    public String toString() {
        return "StringExp(" + value + ")";
    }

}
