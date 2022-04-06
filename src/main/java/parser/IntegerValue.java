package parser;

public class IntegerValue {
    public final int value;

    public IntegerValue(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        if (other instanceof IntegerValue) {
            final IntegerValue asInt = (IntegerValue) other;
            return value == asInt.value;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntegerValue(" + value + ")";
    }
}
