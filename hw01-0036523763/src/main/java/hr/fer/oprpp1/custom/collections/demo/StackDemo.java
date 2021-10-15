package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
    public static void main(String args[]){
        ObjectStack stack = new ObjectStack();
        String[] expression = args[0].split(" ");
        int operand1;
        int operand2;
        int result = 0;
        for (String element : expression) {
            char current = element.charAt(element.length() - 1);
            if (Character.isDigit(current)){
                stack.push(Integer.parseInt(element));
            } else {
                operand2 = (int) stack.pop();
                operand1 = (int) stack.pop();

                result = switch (current) {
                    case '+' -> operand1 + operand2;
                    case '-' -> operand1 - operand2;
                    case '/' -> {
                        if (operand2==0) throw new ArithmeticException("Division by zero is not allowed");
                        yield operand1 / operand2;
                    }
                    case '*' -> operand1 * operand2;
                    case '%' -> operand1 % operand2;
                    default -> throw new IllegalStateException("Unexpected operator: " + current);
                };
                stack.push(result);
            }
        }
        result = (int) stack.pop();
        if (stack.isEmpty()) {
            System.out.println("Expression evaluates to " + Integer.toString(result));
        } else {
            System.err.println("Invalid input, expression could not be evaluated");
        }
    }
}
