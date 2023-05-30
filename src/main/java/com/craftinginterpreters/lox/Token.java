package com.craftinginterpreters.lox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Token {
    private final TokenType type;
    private final String lexeme;
    private final Object literal;
    @Getter
    private final int line;

    @Override
    public String toString () {
        return type + " " + lexeme + " " + literal;
    }
}
