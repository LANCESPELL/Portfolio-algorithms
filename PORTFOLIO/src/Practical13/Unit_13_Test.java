package Practical13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static Practical13.TokenType.BOOLEAN;
import static Practical13.TokenType.NUMERIC;
import static org.junit.jupiter.api.Assertions.*;

public class Unit_13_Test {

    private Interpreter interpreter;

    @BeforeEach
    void setup() {
        interpreter = new Interpreter();
    }

    // -------------------------
    // Token tests
    // -------------------------

    @Test
    void token_fromString_numeric() {
        Token t = Token.fromString("8.3");
        assertEquals(NUMERIC, t.getType());
        assertEquals(8.3, t.getNum(), 0.0001);
    }

    @Test
    void token_fromString_boolean() {
        Token t = Token.fromString("false");
        assertEquals(BOOLEAN, t.getType());
        assertFalse(t.getBool());
    }

    @Test
    void token_fromString_comparison() {
        Token t = Token.fromString(">");
        assertEquals(TokenType.COMPARISON, t.getType());
    }

    @Test
    void token_fromString_logical() {
        Token t = Token.fromString("AND");
        assertEquals(TokenType.LOGICAL, t.getType());
    }

    // -------------------------
    // Interpreter tests
    // -------------------------

    @Test
    void interpreter_comparison_lessThan() {
        interpreter.BeginInterpreter(List.of(
                new Token(NUMERIC, "5"),
                new Token(NUMERIC, "10"),
                new Token(TokenType.COMPARISON, "<")
        ));

        Token result = interpreter.popStack();
        assertEquals(BOOLEAN, result.getType());
        assertTrue(result.getBool());
    }

    @Test
    void interpreter_logical_and() {
        interpreter.BeginInterpreter(List.of(
                new Token(BOOLEAN, "true"),
                new Token(BOOLEAN, "false"),
                new Token(TokenType.LOGICAL, "AND")
        ));

        Token result = interpreter.popStack();
        assertEquals(BOOLEAN, result.getType());
        assertFalse(result.getBool());
    }

    @Test
    void interpreter_fullExample() {
        interpreter.BeginInterpreter(List.of(
                Token.fromString("8.3"),
                Token.fromString("5"),
                Token.fromString(">"),
                Token.fromString("false"),
                Token.fromString("AND")
        ));

        Token result = interpreter.popStack();
        assertEquals(BOOLEAN, result.getType());
        assertFalse(result.getBool());
    }

    @Test
    void interpreter_invalidInput_doesNotCrash() {
        interpreter.BeginInterpreter(List.of(
                new Token(TokenType.COMPARISON, "<")
        ));

        assertTrue(interpreter.getStackSize() >= 0);
    }
}
