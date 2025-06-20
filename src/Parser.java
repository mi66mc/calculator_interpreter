import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token advance() {
        return tokens.get(pos++);
    }

    private boolean match(TokenType type) {
        if (peek().type == type) {
            return true;
        }
        return false;
    }

    public int parse() {
        return expression();
    }

    private int expression() {
        int r = term();
        while (match(TokenType.PLUS) || match(TokenType.MINUS)) {
            Token op = advance();
            int right = term();
            r = op.type == TokenType.PLUS ? r + right : r - right;
        }
        return r;
    }

    private int term() {
        int r = factor();
        while (match(TokenType.STAR) || match(TokenType.SLASH)) {
            Token op = advance();
            int right = factor();
            r = op.type == TokenType.STAR ? r * right : r / right;
        }
        return r;
    }

    private int factor() {
        if (match(TokenType.NUMBER)) {
            return Integer.parseInt(advance().text);
        } else if (match(TokenType.LPAREN)) {
            advance();
            int val = expression();
            if (!match(TokenType.RPAREN)) {
                throw new RuntimeException("Expected ')'");
            }
            advance();
            return val;
        } else {
            throw new RuntimeException("Expected '(' or a number");
        }
    }
}
