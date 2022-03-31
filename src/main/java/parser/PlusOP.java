package src.main.java.parser;


public class PlusOP implements Operator {
    public boolean equals(final Object other) {
        return other instanceof PlusOP;
    }

    public int hashCode() {
        return 100;
    }

    public String toString() {
        return "PlusOp";
    }
}