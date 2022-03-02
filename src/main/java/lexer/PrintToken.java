package lexer;

public class PrintToken implements Token {
    public boolean equals(final Object other) {
        return other instanceof PrintToken;
    }

    public int hashCode() {
        return 22;
    }

    //this is the part I am not sure
    public String toString() {
        return "print";
    }

}