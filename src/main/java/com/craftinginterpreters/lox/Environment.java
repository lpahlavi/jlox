package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

public final class Environment {
    private final Map<String, Object> values = new HashMap<>();

    public Object get (Token name) {
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }
        throw new RuntimeError(name, String.format("Undefined variable '%s'.", name.lexeme));
    }

    public void define (String name, Object value) {
        // Note: this allows variable re-definition since we do not check for existence!
        values.put(name, value);
    }

    public void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }
        throw new RuntimeError(name, String.format("Undefined variable '%s'.", name.lexeme));
    }
}
