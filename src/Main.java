public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("123 + 124 - 2");
        System.out.println(lexer.tokenize());
    }
}