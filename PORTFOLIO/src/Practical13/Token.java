package Practical13;

/**
 * Token stores a TokenType and a String value.
 * Rules 1-4 are handled by fromString().
 */
public class Token {

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() { return type; }
    public String getValue() { return value; }

    public double getNum() {
        if (type != TokenType.NUMERIC) throw new IllegalArgumentException("Not NUMERIC: " + type);
        return Double.parseDouble(value);
    }

    public boolean getBool() {
        if (type != TokenType.BOOLEAN) throw new IllegalArgumentException("Not BOOLEAN: " + type);
        return Boolean.parseBoolean(value);
    }

    /**
     * Converts raw input into a Token:
     * 1. Comparison := > | <
     * 2. Logical := AND | OR
     * 3. Num := Double
     * 4. Bool := Boolean
     */
    public static Token fromString(String raw) {
        String s = raw.trim();

        if (s.equals(">") || s.equals("<")) return new Token(TokenType.COMPARISON, s);
        if (s.equalsIgnoreCase("AND") || s.equalsIgnoreCase("OR")) return new Token(TokenType.LOGICAL, s.toUpperCase());
        if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) return new Token(TokenType.BOOLEAN, s.toLowerCase());

        try {
            Double.parseDouble(s);
            return new Token(TokenType.NUMERIC, s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid token: " + raw);
        }
    }

    @Override
    public String toString() {
        return "Token Type:" + type + " Value:" + value;
    }
}
