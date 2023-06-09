package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.craftinginterpreters.shared.ErrorCode.*;
import static java.lang.String.format;

public final class Lox {
    private static final Interpreter interpreter = new Interpreter();

    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    public static void main (String... args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(EX_USAGE);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    public static void runFile (String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(EX_DATAERR);
        if (hadRuntimeError) System.exit(EX_SOFTWARE);
    }

    private static void runPrompt () throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            // This happens when the user types Control+D
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run (String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

       Parser parser = new Parser(tokens);
       List<Stmt> statements = parser.parse();

       // Stop if there was a syntax error.
        if (hadError) return;

        interpreter.interpret(statements);
    }

    public static void error (int line, String message) {
        report(line, "", message);
    }

    public static void error (Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, format(" at '%s'", token.lexeme), message);
        }
    }

    private static void report (int line, String where, String message) {
        System.err.printf("[line %d] Error %s: %s%n", line, where, message);
        hadError = true;
    }

    public static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }
}
