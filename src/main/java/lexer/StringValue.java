package lexer;

public class StringValue implements Token {
    final String value;

    public StringValue(final String value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        if (other instanceof StringValue) {
            final StringValue asString = (StringValue) other;
            return value.equals(asString.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return "StringValue(" + value + ")";
    }
}

/*
 * Jacks code moved from string token class
 * commented out, will need this code later for the compiler
 * public class StringToken implements Token {
 * public final String value;
 * 
 * public StringToken(String value){
 * 
 * this.value=value;
 * }
 * public int hashCode(){
 * return 10;
 * }
 * 
 * public boolean equals (final Object other){
 * return other instanceof StringToken;
 * }
 * 
 * public String toString(){
 * 
 * return value;
 * }
 * 
 * }
 */