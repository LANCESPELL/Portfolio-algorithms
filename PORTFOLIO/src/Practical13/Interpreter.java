package Practical13;

import java.util.List;
import java.util.Stack;

/**
 * Stack-based postfix interpreter.
 *
 * Rules:
 * 5. Bool := Num Num Comparison
 * 6. Bool := Bool Bool Logical
 *
 * Time Complexity:
 * - Each token processed once -> O(n)
 * Sub-optimal:
 * - Printing stack after each rule is O(k) per print (required by spec).
 */
public class Interpreter {

    private final Stack<Token> stack = new Stack<>();

    public void BeginInterpreter(List<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            System.out.println("No tokens provided.");
            return;
        }

        for (Token token : tokens) {
            stack.push(token);

            if (token.getType() == TokenType.COMPARISON) {
                ComparisonOperation();
            } else if (token.getType() == TokenType.LOGICAL) {
                LogicalOperation();
            }
        }

        if (stack.size() == 1 && stack.peek().getType() == TokenType.BOOLEAN) {
            System.out.println("FINAL RESULT: " + stack.peek().getValue());
        } else {
            System.out.println("Finished but did not reduce to single BOOLEAN.");
            System.out.println("Final Stack: " + stack);
        }
    }

    // Rule 5
    public void ComparisonOperation() {
        if (stack.size() < 3) {
            System.out.println("Not enough tokens for comparison. Stack: " + stack);
            return;
        }

        Token op = stack.pop();
        Token rhs = stack.pop();
        Token lhs = stack.pop();

        if (lhs.getType() != TokenType.NUMERIC || rhs.getType() != TokenType.NUMERIC) {
            System.out.println("Comparison requires NUMERIC values.");
            stack.push(lhs);
            stack.push(rhs);
            stack.push(op);
            return;
        }

        boolean result;
        double a = lhs.getNum();
        double b = rhs.getNum();

        if (op.getValue().equals(">")) result = a > b;
        else if (op.getValue().equals("<")) result = a < b;
        else {
            System.out.println("Invalid comparison operator.");
            stack.push(lhs);
            stack.push(rhs);
            stack.push(op);
            return;
        }

        stack.push(new Token(TokenType.BOOLEAN, String.valueOf(result)));

        System.out.println("Rule 5 applied: " + a + " " + op.getValue() + " " + b + " = " + result);
        System.out.println("Stack: " + stack);
    }

    // Rule 6
    public void LogicalOperation() {
        if (stack.size() < 3) {
            System.out.println("Not enough tokens for logical operation. Stack: " + stack);
            return;
        }

        Token op = stack.pop();
        Token rhs = stack.pop();
        Token lhs = stack.pop();

        if (lhs.getType() != TokenType.BOOLEAN || rhs.getType() != TokenType.BOOLEAN) {
            System.out.println("Logical operation requires BOOLEAN values.");
            stack.push(lhs);
            stack.push(rhs);
            stack.push(op);
            return;
        }

        boolean a = lhs.getBool();
        boolean b = rhs.getBool();
        boolean result;

        if (op.getValue().equalsIgnoreCase("AND")) result = a && b;
        else if (op.getValue().equalsIgnoreCase("OR")) result = a || b;
        else {
            System.out.println("Invalid logical operator.");
            stack.push(lhs);
            stack.push(rhs);
            stack.push(op);
            return;
        }

        stack.push(new Token(TokenType.BOOLEAN, String.valueOf(result)));

        System.out.println("Rule 6 applied: " + a + " " + op.getValue() + " " + b + " = " + result);
        System.out.println("Stack: " + stack);
    }

    // Helpers for tests
    public Token popStack() {
        return stack.pop();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public int getStackSize() {
        return stack.size();
    }
}
