package parser;


public class TimesOp implements Operator {
    public boolean equals(final Object other) {
        return other instanceof TimesOp;
    }

    public int hashCode() {
        return 102;
    }

    public String toString() {
        return "TimesOp";
    }
}