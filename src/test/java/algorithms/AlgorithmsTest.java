package algorithms;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmsTest {

    @Test
    void testAboveAverage(){
        List<Integer> input = List.of(0,9, 2, 3, 4, 5, 6, 7, 8, 1, 10);
        Algorithms algorithms = new Algorithms();
        List<Integer> result = algorithms.aboveAverage(input);

        assertEquals(5, result.size());
        assertTrue(result.contains(6));
        assertTrue(result.contains(7));
        assertTrue(result.contains(8));
        assertTrue(result.contains(9));
        assertTrue(result.contains(10));
    }

}