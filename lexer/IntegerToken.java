package lexer;

public class IntegerToken implements Token {
    public final int value;

    public IntegerToken(final int value) {
        this.value = value;
    }

    public int hashCode() {

        return 9;
    }
    public boolean equals(final Object other) {
        if (other instanceof IntegerToken) {
            final IntegerToken otherInt = (IntegerToken)other;
            return value == otherInt.value;
        } else {
            return false;
        }
    }
    @Override
    public String toString(){

        return "Value: " + value; 
        //Casts value as a String without using a toString method

    }
}