package parser;

public class BooleanExp implements Expression {

    public final Boolean value;

    public BooleanExp(final Boolean value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (other instanceof BooleanExp &&
                value == ((BooleanExp) other).value);
    }

    public int hashCode() {
        if (value) {
            return 1;
        } else {
            return 0;
        }

    }

    public String toString() {
        return "BooleanExp(" + Boolean.toString(value) + ")";
    }

}
