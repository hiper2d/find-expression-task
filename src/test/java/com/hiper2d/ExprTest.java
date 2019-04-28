package com.hiper2d;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExprTest {

    private Expr expr = new Expr();

    @Test
    @DisplayName("Should return true")
    void canBeEqualTo24() {
        assertTrue(expr.canBeEqualTo24(new int[] { 1, 2, 7, 2 }));
        assertTrue(expr.canBeEqualTo24(new int[] { 4, 1, 8, 7 }));
        assertTrue(expr.canBeEqualTo24(new int[] { 1, 2, 3, 4 }));
        assertTrue(expr.canBeEqualTo24(new int[] { 1, 5, 5, 5 }));
        assertTrue(expr.canBeEqualTo24(new int[] { 4, 1, 5, 6 }));
    }

    @Test
    @DisplayName("Should return false")
    void cantBeEqualTo24() {
        assertFalse(expr.canBeEqualTo24(new int[] { 1, 2, 1, 2 }));
        assertFalse(expr.canBeEqualTo24(new int[] { 9, 9, 9, 9 }));
        assertFalse(expr.canBeEqualTo24(new int[] { 1, 1, 1, 1 }));
    }

    @Test
    @DisplayName("Should throw exception if arguments are not in 0-9 range")
    void shouldThrowException() {
        assertThrows(NumberFormatException.class, () -> expr.canBeEqualTo24(new int[] { 11, 1, 1, 1 }));
        assertThrows(NumberFormatException.class, () -> expr.canBeEqualTo24(new int[] { 1, -1, 1, 1 }));
        assertThrows(NumberFormatException.class, () -> expr.canBeEqualTo24(new int[] { 1, 1, 1, 0 }));
    }
}
