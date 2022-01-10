package ru.geekbrains.lesson;

public class Triangle {
    public static void main(String[] args) {
        System.out.println(getArea(3, 4, 5));
    }

    public static double getArea(int a, int b, int c) {
        if(a<=0 || b<=0 || c <= 0)
            throw new IllegalArgumentException("Одна из сторон треугольника меньше или равна 0");
        if(a+b <= c || b+c <= a || c+a <=b)
            throw new IllegalArgumentException("Треугольник с указанными сторонами не возможен");
        double p = (a + b + c) / 2;
        double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return area;
    }
}
