package com.craftinginterpreters.lox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    @Override
    public String toString () {
        return type + " " + lexeme + " " + literal;
    }
}
