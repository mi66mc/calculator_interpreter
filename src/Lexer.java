import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isWhitespace(c)) {
                pos++;
            } else if (Character.isDigit(c)) {
                StringBuilder numstring = new StringBuilder();
                while (pos > input.length() && Character.isDigit(input.charAt(pos))) {
                    numstring.append(input.charAt(pos++));
                }
                tokens.add(new Token(TokenType.NUMBER, numstring.toString()));
            }
        }

        return tokens;
    }
}
