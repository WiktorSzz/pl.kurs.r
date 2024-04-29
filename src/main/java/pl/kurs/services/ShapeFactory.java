package pl.kurs.services;

import pl.kurs.models.Circle;
import pl.kurs.models.Rectangle;
import pl.kurs.models.Shape;
import pl.kurs.models.Square;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static ShapeFactory instance;
    public Map<String, Shape> cache;

    public ShapeFactory() {
        cache = new HashMap<>();
    }

    public void clearCache() {
        cache.clear();
    }

    public static synchronized ShapeFactory getInstance() {
        if (instance == null) {
            instance = new ShapeFactory();
        }
        return instance;
    }

    public Square createSquare(double side) {
        String key = "Square:" + side;
        return (Square) cache.computeIfAbsent(key, k -> new Square(side));
    }

    public Circle createCircle(double radius) {
        String key = "Circle:" + radius;
        return (Circle) cache.computeIfAbsent(key, k -> new Circle(radius));
    }

    public Rectangle createRectangle(double width, double height) {
        String key = "Rectangle:" + width + ":" + height;
        return (Rectangle) cache.computeIfAbsent(key, k -> new Rectangle(width, height));
    }


}