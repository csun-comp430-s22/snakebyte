package lexer;

public class StringToken implements Token {

    public int hashCode() {
        return 10;
    }

    public boolean equals(final Object other) {
        return other instanceof StringToken;
    }

    public String toString() {

        return "Str";
    }

}
/*
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