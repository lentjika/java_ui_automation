package ru.geekbrains.lesson;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void getArea() {
        assertAll(() -> assertEquals(Triangle.getArea(3, 4, 5), 6),
                () -> assertEquals(Triangle.getArea(4, 3, 5), 6),
                () -> assertEquals(Triangle.getArea(5, 4, 3), 6));
    }

    @Test
    void getAreaIllegalArguments() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(0, 4, 5);
        });
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(1, 0, 5);
        });
        Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(1, 4, 0);
        });
        assertAll(() -> assertEquals(exception.getMessage(), "Одна из сторон треугольника меньше или равна 0"),
                () -> assertEquals(exception2.getMessage(), "Одна из сторон треугольника меньше или равна 0"),
                () -> assertEquals(exception3.getMessage(), "Одна из сторон треугольника меньше или равна 0"));
    }

    @Test
    void getAreaNotTriangle(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(1, 1, 3);
        });
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(1, 3, 1);
        });
        Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Triangle.getArea(3, 1, 1);
        });
        String exMessage = "Треугольник с указанными сторонами не возможен";
        assertAll(() -> assertEquals(exception.getMessage(), exMessage),
                () -> assertEquals(exception2.getMessage(), exMessage),
                () -> assertEquals(exception3.getMessage(), exMessage));
    }
}