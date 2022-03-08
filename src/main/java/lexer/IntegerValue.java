package lexer;

public class IntegerValue implements Token {

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

    // Two objects: obj1 and obj2
    // obj1.hashCode() == obj2.hashCode() // true
    public int hashCode() {
        return value;
    }

    public String toString() {
        return "IntegerValue(" + value + ")";
    }
}
/*
 * Jacks code moved from integer token class
 * will need this code for the compiler
 * public class IntegerToken implements Token {
 * public final int value;
 * 
 * public IntegerToken(final int value) {
 * this.value = value;
 * }
 * 
 * public int hashCode() {
 * 
 * return 9;
 * }
 * public boolean equals(final Object other) {
 * if (other instanceof IntegerToken) {
 * final IntegerToken otherInt = (IntegerToken)other;
 * return value == otherInt.value;
 * } else {
 * return false;
 * }
 * }
 * 
 * @Override
 * public String toString(){
 * 
 * return "Value: " + value;
 * //Casts value as a String without using a toString method
 * 
 * }
 * }
 */