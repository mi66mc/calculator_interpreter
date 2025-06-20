public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("1 + 1 - 5 * 3 + 1");
        Parser parser = new Parser(lexer.tokenize());
        System.out.println(parser.parse().evaluate());
    }
}