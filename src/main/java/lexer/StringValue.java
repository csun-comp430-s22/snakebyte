public class StringValue implements Token {
    final String value;
    public StringValue(final String value) {
        this.value = value;
    }
    public boolean equals(final Object other) {
        if(other instanceof StringValue) {
            final StringValue asString = (StringValue) other;
            return value.equals(asString.value);
        }else{
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