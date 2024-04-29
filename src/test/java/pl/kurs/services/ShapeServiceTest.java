package pl.kurs.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurs.exceptions.ShapeException;
import pl.kurs.models.*;
import pl.kurs.models.ObjectMapperHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class ShapeServiceTest {
    List<Shape> shapes;
    ShapeFactory shapeFactory;
    Circle circle;
    Square square;
    Rectangle rectangle;
    String filePath = "src/main/resources/shapeTestList.json";
    File testFile;


    @BeforeEach
    public void init() throws IOException {
        shapeFactory = new ShapeFactory();
        circle = shapeFactory.createCircle(25);
        square = shapeFactory.createSquare(7);
        rectangle = shapeFactory.createRectangle(12, 5);
        shapes = new ArrayList<>();
        shapes.add(shapeFactory.createCircle(18));
        shapes.add(circle);
        shapes.add(shapeFactory.createSquare(5));
        shapes.add(square);
        shapes.add(shapeFactory.createRectangle(10, 5));
        shapes.add(rectangle);
        testFile = new File(filePath);
        if (!testFile.exists())
            testFile.createNewFile();
    }

    @Test
    public void shouldFindLargestAreaShapeAndReturnCircleWith25Radius() throws ShapeException {
        Circle expectedCircle = circle;
        Shape largestAreaShape = ShapeService.getShapeWithLargestArea(shapes);

        assertEquals(expectedCircle, largestAreaShape);
    }


    @Test
    void shouldThrowEmployeeExceptionWhenListIsNullInGetShapeWithLargestAreaMethod() {
        ShapeException thrown = assertThrows(ShapeException.class, () ->
                ShapeService.getShapeWithLargestArea(null)
        );

        assertEquals("No shape with a largest area was found.", thrown.getMessage());
    }

    @Test
    public void shouldFindLargestPerimeterSquareAndReturnSquareWith7Side() throws ShapeException {
        Square expectedRectangle = square;
        Shape largestPerimeterShape = ShapeService.getShapeWithLargestPerimeter(shapes, Square.class);
        assertEquals(expectedRectangle, largestPerimeterShape);
    }

    @Test
    public void shouldFindLargestPerimeterRectangleShapeAndReturnRectangleWith12WidthAnd5Height() throws ShapeException {
        Rectangle expectedRectangle = rectangle;
        Shape largestPerimeterShape = ShapeService.getShapeWithLargestPerimeter(shapes, Rectangle.class);
        assertEquals(expectedRectangle, largestPerimeterShape);
    }


    @Test
    public void shouldFindLargestPerimeterCircleShapeAndReturnCircleWith25Radius() throws ShapeException {
        Circle expectedCircle = circle;
        Shape largestPerimeterShape = ShapeService.getShapeWithLargestPerimeter(shapes, Circle.class);
        assertEquals(expectedCircle, largestPerimeterShape);

    }

    @Test
    public void shouldThrowEmployeeExceptionWhenListIsNullInGetShapeWithLargestPerimeterMethod() {
        List<Shape> shapes = new ArrayList<>();
        ShapeException thrown = assertThrows(ShapeException.class, () ->
                ShapeService.getShapeWithLargestPerimeter(shapes, Square.class)

        );
        assertEquals("No shape with a largest perimeter was found.", thrown.getMessage());
    }

    @Test
    void shouldExportShapesListToFileAsJsonWhenExportShapesToJsonMethodIsCalled() throws IOException {
        ShapeService.exportShapesToJson(shapes, filePath);

        String jsonFromFile;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            jsonFromFile = reader.lines().collect(Collectors.joining());
        }

        ObjectMapper mapper = ObjectMapperHolder.INSTANCE.getObjectMapper();
        JsonNode treeFromJson = mapper.readTree(jsonFromFile);
        JsonNode treeFromExpected = mapper.readTree(mapper.writeValueAsString(shapes));

        assertEquals(treeFromExpected, treeFromJson);
    }

    @Test
    void exportShapesToJson_ThrowsIllegalArgumentException_IfListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShapeService.exportShapesToJson(null, "nonePath.json");
        });
    }

    @Test
    void shouldHandleIOExceptionWhenFileIsReadOnly() {

        File readOnlyFile = new File("path/to/read-only-file.json");
        readOnlyFile.setReadOnly();

        List<Shape> shapesToTest = new ArrayList<>();
        shapesToTest.add(new Circle(10));


        ShapeService.exportShapesToJson(shapesToTest, readOnlyFile.getPath());

    }

    @Test
    public void shouldImportShapesListFromJson() throws IOException {
        List<Shape> shapesToTest = shapes;
        ObjectMapperHolder.INSTANCE.getObjectMapper().writeValue(testFile, shapesToTest);

        List<Shape> shapesFromJson = ShapeService.importShapesFromJson(filePath);

        assertEquals(shapesToTest, shapesFromJson);
    }

    @Test
    void importShapesFromJson_ReturnsNull_WhenIOExceptionOccurs() {
        String nonExistentFilePath = "non_existent_file.json";

        List<Shape> result = ShapeService.importShapesFromJson(nonExistentFilePath);


        assertNull(result);
    }


}

