import java.util.ArrayList;
import java.util.Arrays;
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
            advance();
            return true;
        }
        return false;
    }

    public Expression parse() {
        return parse_expression();
    }

    private int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };
    }

    private Expression parse_expression(int minPrecedence) {
        Expression left = parse_primary();

        List<TokenType> oplist = new ArrayList<>(Arrays.asList(TokenType.MINUS, TokenType.PLUS, TokenType.STAR, TokenType.SLASH));

        while (peek().type != TokenType.EOF && oplist.contains(peek().type) && precedence(peek().text) >= minPrecedence) {
            Token op = advance();
            int nextMinPrecedence = precedence(op.text) + 1;
            Expression right = parse_expression(nextMinPrecedence);
            Expression newLeft = new Expression(op.text);
            newLeft.setLeft(left);
            newLeft.setRight(right);
            left = newLeft;
        }

        return left;
    }

    private Expression parse_expression() {
        return parse_expression(1);
    }

    private Expression parse_primary() {
        Token token = peek();

        if (token.type == TokenType.EOF) {
            throw new IllegalStateException("Unexpected end of line");
        }

        if (token.type == TokenType.NUMBER) {
            advance();
            return new Expression(token.text);
        } else if (token.type == TokenType.LPAREN) {
            advance();
            Expression expr = parse_expression();
            if (!(peek().type == TokenType.RPAREN)) {
                throw new IllegalStateException("Expected ')' found ");
            }
            advance();
            return expr;
        } else {
            throw new IllegalStateException("Unexpected token: " + token);
        }
    }
}
