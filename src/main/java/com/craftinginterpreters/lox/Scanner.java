package com.craftinginterpreters.lox;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;
import static java.util.Map.entry;

@RequiredArgsConstructor
public final class Scanner {

    private static final Map<String, TokenType> KEYWORDS = Map.ofEntries(
            entry("and", AND),
            entry("class", CLASS),
            entry("else", ELSE),
            entry("false", FALSE),
            entry("for", FOR),
            entry("fun", FUN),
            entry("if", IF),
            entry("nil", NIL),
            entry("or", OR),
            entry("print", PRINT),
            entry("return", RETURN),
            entry("super", SUPER),
            entry("this", THIS),
            entry("true", TRUE),
            entry("var", VAR),
            entry("while", WHILE)
    );

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;

    List<Token> scanTokens () {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            // One or two character lexemes.
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);
            case '-' -> addToken(MINUS);
            case '+' -> addToken(PLUS);
            case ';' -> addToken(SEMICOLON);
            case '*' -> addToken(STAR);
            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
            case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);
            // Comments (and division symbol).
            case '/' -> {
                // Single line comment
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                // C-style block comments.
                } else if (match('*')) {
                    blockComment();
                } else {
                    addToken(SLASH);
                }
            }
            // Whitespace.
            case ' ', '\r', '\t' -> {}
            case '\n' -> line++;
            // Longer lexemes.
            case '"' -> string();
            default -> {
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifierOrKeyword();
                } else {
                    Lox.error(line, "Unexpected character.");
                }
            }
        }
    }

    private void addToken (TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private boolean match(char expected) {
        if (peek() != expected) {
            return false;
        }
        advance();
        return true;
    }

    private char advance () {
        return source.charAt(current++);
    }

    private char peek () {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext () {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }
        advance(); // The closing quote.
        String value = source.substring(start + 1, current - 1); // Trim surrounding quotes.
        addToken(STRING, value);
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }
        // Consume fractional part, note that trailing dots are not allowed.
        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) {
                advance();
            }
        }
        Double value = Double.parseDouble(source.substring(start, current));
        addToken(NUMBER, value);
    }

    private void identifierOrKeyword() {
        while (isAlphanumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = KEYWORDS.getOrDefault(text, IDENTIFIER);
        addToken(type);
    }

    private void blockComment() {
        while (!(peek() == '*' && peekNext() == '/') && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated block comment.");
            return;
        }
        // Consume closing '*/'.
        advance();
        advance();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_');
    }

    private boolean isAlphanumeric (char c) {
        return isAlpha(c) || isDigit(c);
    }
}
