package pl.kurs.app;

import pl.kurs.exceptions.ShapeException;
import pl.kurs.models.Shape;
import pl.kurs.services.ShapeFactory;
import pl.kurs.models.Square;
import pl.kurs.services.ShapeService;

import java.util.Arrays;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws ShapeException {


        ShapeFactory factory = ShapeFactory.getInstance();

        Shape circle1 = factory.createCircle(2);
        Shape circle2 = factory.createCircle(3);
        Shape circle3 = factory.createCircle(3);
        Shape circle4 = factory.createCircle(3);
        Shape square1 = factory.createSquare(4);
        Shape square2 = factory.createSquare(4);
        Shape square3 = factory.createSquare(4);
        Shape square4 = factory.createSquare(5);
        Shape rectangle1 = factory.createRectangle(5, 2);

        List<Shape> shapes = Arrays.asList(circle1, circle2, circle3, circle4, square1, square2, square3, square4, rectangle1);

        Shape largestAreaShape = ShapeService.getShapeWithLargestArea(shapes);
        System.out.println("Shape with the largest area: " + largestAreaShape);

        Shape largestPerimeterShape = ShapeService.getShapeWithLargestPerimeter(shapes, Square.class);
        System.out.println("Shape with the largest perimeter: " + largestPerimeterShape);


        String jsonFilePath = "src/main/resources/shapeList.json";
        ShapeService.exportShapesToJson(shapes, jsonFilePath);


        ShapeService.importShapesFromJson(jsonFilePath);

        System.out.println("Zaliczyłem test! :)");


    }
}






