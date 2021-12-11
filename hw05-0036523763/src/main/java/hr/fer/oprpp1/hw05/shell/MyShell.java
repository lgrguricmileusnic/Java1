package hr.fer.oprpp1.hw05.shell;

public class MyShell {
    public static void main(String[] args) {
        Environment env = new MyShellEnvironment();
        System.out.println("Welcome to MyShell v 1.0");
        System.out.print(env.getPromptSymbol());


    }
}
