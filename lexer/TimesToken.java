package lexer;

public class TimesToken implements Token{

    public boolean equals(final Object other) {
        return other instanceof TimesToken;
    }

    public int hashCode() {
        return 5;
    }

    public String toString() {
        return "*";
    }

    
}
