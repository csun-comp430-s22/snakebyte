package parser;

public class IfExp implements Expression {
   

    

    public boolean equals(final Object other) {
        if (other instanceof IfExp) {
            return (true);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (108);
    }

    public String toString() {
        return ("IfExp(" +")");
    }
}