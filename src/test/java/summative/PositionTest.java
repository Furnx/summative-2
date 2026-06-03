package summative;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static za.co.wethinkcode.world.Direction.*;

class PositionTest {

    @Test
    void constructorSetsXAndY() {
        Position p = new Position(3, -2);
        assertEquals(3,  p.x());
        assertEquals(-2, p.y());
    }

    @Test
    void moveNorthIncreasesY() {
        Position result = new Position(0, 0).move(NORTH, 3);
        assertEquals(new Position(0, 3), result);
    }

    @Test
    void moveSouthDecreasesY() {
        assertEquals(new Position(0, -1), new Position(0, 0).move(SOUTH, 1));
    }

    @Test
    void moveEastIncreasesX() {
        assertEquals(new Position(6, 1), new Position(1, 1).move(EAST, 5));
    }

    @Test
    void moveWestDecreasesX() {
        assertEquals(new Position(0, -1), new Position(2, -1).move(WEST, 2));
    }

    @Test
    void moveThrowsForZeroSteps() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position(0, 0).move(NORTH, 0));
    }

    @Test
    void moveThrowsForNegativeSteps() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position(0, 0).move(EAST, -3));
    }

    @Test
    void moveDoesNotMutateOriginal() {
        Position original = new Position(1, 1);
        original.move(NORTH, 5);
        assertEquals(1, original.x());
        assertEquals(1, original.y());
    }

    @Test
    void equalsReturnsTrueForSameCoordinates() {
        assertEquals(new Position(4, -2), new Position(4, -2));
    }

    @Test
    void toStringFormatsCorrectly() {
        assertEquals("[3,-2]", new Position(3, -2).toString());
        assertEquals("[0,0]",  new Position(0, 0).toString());
    }
}