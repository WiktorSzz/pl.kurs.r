package pl.kurs.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kurs.exceptions.ShapeException;
import pl.kurs.models.ObjectMapperHolder;
import pl.kurs.models.Shape;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShapeService {

    public static Shape getShapeWithLargestArea(List<Shape> shapes) throws ShapeException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Shape::getArea))
                .orElseThrow(() -> new ShapeException("No shape with a largest area was found."));
    }


    public static Shape getShapeWithLargestPerimeter(List<Shape> shapes, Class<?> shapeType) throws ShapeException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .filter(shapeType::isInstance)
                .max(Comparator.comparingDouble(Shape::getPerimeter))
                .orElseThrow(() -> new ShapeException("No shape with a largest perimeter was found."));
    }


    public static void exportShapesToJson(List<Shape> shapes, String path) {
        if (shapes == null || shapes.isEmpty()) {
            throw new IllegalArgumentException("Shape list cannot be null or empty");
        }

        ObjectMapper mapper = ObjectMapperHolder.INSTANCE.getObjectMapper();

        try {
            mapper.writeValue(new File(path), shapes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Shape> importShapesFromJson(String path) {
        try {
            return ObjectMapperHolder.INSTANCE.getObjectMapper().readValue(new File(path), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}




