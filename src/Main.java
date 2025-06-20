public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("3 + 2 * (4 - 1)");
        Parser parser = new Parser(lexer.tokenize());
        System.out.println(parser.parse());
    }
}