package lexer;

public class IntegerValue implements Token {

    public final int value;
     public IntegerValue(final int value) {
        this.value = value;
     }   
    public boolean equals(final Object other) {
        if(other instanceof IntegerValue) {
            final IntegerValue asInt = (IntegerValue) other;
            return value == asInt.value;
        }else{
            return false;
        }
    }
        //Two objects: obj1 and obj2
        //obj1.hashCode() == obj2.hashCode() // true
    public int hashCode() {
        return value;
    }
    public String toString() {
        return "IntegerValue(" + value + ")";
    }
}
