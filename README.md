# jlox

Java Lox interpreter as outlined in the first part of _Crafting Interpreters_ by Robert Nystrom.

Author: Louis Pahlavi \
Date: 30.05.2023

## Building the project
```bash
./gradlew build
```

## Running the project

Run interactive prompt:
```bash
java -cp build/classes/java/main com.craftinginterpreters.lox.Lox
```

Run Lox file:
```bash
java -cp build/classes/java/main com.craftinginterpreters.lox.Lox <file>
```