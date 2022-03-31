package src.main.java.parser;
public class IntegerToken implements Token {
    public final int value;
    public IntegerToken(final int value){
        this.value= value;
    }
    public boolean equals(final Object other) {
        return other instanceof IntegerToken;
    }
    public int hashCode() {
        return 9;
    }
    public String toString() {
        return "IntegerToken";
    }
}