public class Expression {
    private String op;
    private Expression left;
    private Expression right;
    private double value;
    private boolean isLeaf;

    public Expression(String op) {
        this.op = op;
        this.left = null;
        this.right = null;
    }

    public void setOp(String op) {
        if (op == null || !"+-*/".contains(op)) {
            throw new IllegalArgumentException("Invalid operator: " + op);
        }
        this.op = op;
    }

    public double evaluate() {
        if (left == null && right == null) {
            try {
                return Double.parseDouble(op);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid number: " + op);
            }
        }
        double leftResult = left.evaluate();
        double rightResult = right.evaluate();
        return switch (op) {
            case "+" -> leftResult + rightResult;
            case "-" -> leftResult - rightResult;
            case "*" -> leftResult * rightResult;
            case "/" -> {
                if (rightResult == 0) throw new ArithmeticException("Division by zero");
                yield leftResult / rightResult;
            }
            default -> throw new IllegalStateException("Unknown operator: " + op);
        };
    }

    public String getOp() {
        return op;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
