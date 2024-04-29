package pl.kurs.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kurs.services.ShapeFactory;

import static org.junit.jupiter.api.Assertions.*;

class ShapeFactoryTest {
    private ShapeFactory shapeFactory;

    @BeforeEach
    void setUp() {
        shapeFactory = ShapeFactory.getInstance();
        shapeFactory.clearCache();
    }


    @Test
    void shouldReturnTheSameSquareFromCacheWhenCreatedWithTheSameSize() {
        double side = 5.0;
        String key = "Square:" + side;
        assertFalse(shapeFactory.cache.containsKey(key));

        Square firstSquare = shapeFactory.createSquare(side);
        Square secondSquare = shapeFactory.createSquare(side);

        assertTrue(shapeFactory.cache.containsKey(key));
        assertSame(firstSquare, secondSquare);
        assertSame(firstSquare, shapeFactory.cache.get(key));
    }

    @Test
    void shouldCacheMultipleSquaresWithDifferentSizes() {
        double side1 = 5.0;
        double side2 = 10.0;
        String key1 = "Square:" + side1;
        String key2 = "Square:" + side2;

        Square firstSquare = shapeFactory.createSquare(side1);
        Square secondSquare = shapeFactory.createSquare(side2);

        assertTrue(shapeFactory.cache.containsKey(key1));
        assertTrue(shapeFactory.cache.containsKey(key2));
        assertNotSame(firstSquare, secondSquare);
    }


    @Test
    void shouldReturnTheSameCircleFromCacheWhenCreatedWithTheSameSize() {
        double radius = 3.0;
        String key = "Circle:" + radius;
        assertFalse(shapeFactory.cache.containsKey(key));

        Circle firstCircle = shapeFactory.createCircle(radius);
        Circle secondCircle = shapeFactory.createCircle(radius);

        assertTrue(shapeFactory.cache.containsKey(key));
        assertSame(firstCircle, secondCircle);
        assertSame(firstCircle, shapeFactory.cache.get(key));
    }

    @Test
    void shouldCacheMultipleCircleWithDifferentSizes() {
        double radius1 = 3.0;
        double radius2 = 5.0;
        String key1 = "Circle:" + radius1;
        String key2 = "Circle:" + radius2;

        Circle firstCircle1 = shapeFactory.createCircle(radius1);
        Circle secondCircle = shapeFactory.createCircle(radius2);

        assertTrue(shapeFactory.cache.containsKey(key1));
        assertTrue(shapeFactory.cache.containsKey(key2));
        assertNotSame(firstCircle1, secondCircle);
    }


    @Test
    void shouldReturnTheSameRectangleFromCacheWhenCreatedWithTheSameSize() {
        double width = 4.0;
        double height = 6.0;
        String key = "Rectangle:" + width + ":" + height;
        assertFalse(shapeFactory.cache.containsKey(key));

        Rectangle firstRectangle = shapeFactory.createRectangle(width, height);
        Rectangle secondRectangle = shapeFactory.createRectangle(width, height);

        assertTrue(shapeFactory.cache.containsKey(key));
        assertSame(firstRectangle, secondRectangle);
        assertSame(firstRectangle, shapeFactory.cache.get(key));
    }

    @Test
    void shouldCacheMultipleRectangleWithDifferentSizes() {
        double width1 = 4.0;
        double height1 = 6.0;

        double width2 = 6.0;
        double height2 = 8.0;
        String key1 = "Rectangle:" + width1 + ":" + height1;
        String key2 = "Rectangle:" + width2 + ":" + height2;


        Rectangle firstRectangle = shapeFactory.createRectangle(width1, height1);
        Rectangle secondRectangle = shapeFactory.createRectangle(width2, height2);

        assertTrue(shapeFactory.cache.containsKey(key1));
        assertTrue(shapeFactory.cache.containsKey(key2));
        assertNotSame(firstRectangle, secondRectangle);
    }
}
