package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.craftinginterpreters.lox.ErrorCode.EX_USAGE;
import static java.lang.String.format;

public final class Lox {

    static boolean hadError = false;

    public static void main (String... args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(EX_USAGE.getCode());
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    public static void runFile (String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(ErrorCode.EX_DATAERR.getCode());
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

        for (Token token: tokens) {
            System.out.println(token);
        }
    }

    public static void error (int line, String message) {
        report(line, "", message);
    }

    private static void report (int line, String where, String message) {
        System.err.println(format("[line %d] Error %s: %s", line, where, message));
        hadError = true;
    }
}
