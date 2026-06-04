package summative;

import summative.robot.SniperRobot;
import summative.robot.TankRobot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import summative.world.Obstacle;
import summative.world.World;

import javax.swing.text.Position;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    private World world;
    private Obstacle centralObstacle;
    private SniperRobot   sniper;
    private TankRobot     tank;

    @BeforeEach
    void setUp() {
        centralObstacle = new Obstacle(new Position(-1, 1), new Position(1, -1));
        world  = new World(5, 5, List.of(centralObstacle));
        sniper = new SniperRobot("HAL");
        tank   = new TankRobot("R2D2");
    }

    @Test
    void isInBoundsReturnsTrueForBoundaryPosition() {
        assertTrue(world.isInBounds(new Position(5, 5)));
        assertTrue(world.isInBounds(new Position(-5, -5)));
    }

    @Test
    void isInBoundsReturnsFalseOutsideBoundary() {
        assertFalse(world.isInBounds(new Position(6, 0)));
        assertFalse(world.isInBounds(new Position(0, -6)));
    }

    @Test
    void isBlockedReturnsTrueInsideObstacle() {
        assertTrue(world.isBlocked(new Position(0, 0)));
    }

    @Test
    void isBlockedReturnsFalseInOpenSpace() {
        assertFalse(world.isBlocked(new Position(4, 4)));
    }

    @Test
    void addRobotSucceedsInOpenSpace() {
        assertTrue(world.addRobot(sniper, new Position(3, 3)));
        assertTrue(world.listRobots().contains("HAL"));
    }

    @Test
    void addRobotFailsInsideObstacle() {
        assertFalse(world.addRobot(sniper, new Position(0, 0)));
        assertFalse(world.listRobots().contains("HAL"));
    }

    @Test
    void addRobotFailsOnOccupiedPosition() {
        world.addRobot(sniper, new Position(3, 3));
        assertFalse(world.addRobot(tank, new Position(3, 3)));
        assertFalse(world.listRobots().contains("R2D2"));
    }

    @Test
    void addRobotFailsOutOfBounds() {
        assertFalse(world.addRobot(sniper, new Position(10, 10)));
    }

    @Test
    void listRobotsPreservesInsertionOrder() {
        world.addRobot(sniper, new Position(3, 3));
        world.addRobot(tank,   new Position(4, 4));
        List<String> names = world.listRobots();
        assertEquals("HAL",  names.get(0));
        assertEquals("R2D2", names.get(1));
    }

    @Test
    void obstaclesReturnsUnmodifiableView() {
        assertThrows(UnsupportedOperationException.class,
                () -> world.obstacles().add(centralObstacle));
    }

    @Test
    void robotsReturnsUnmodifiableView() {
        world.addRobot(sniper, new Position(3, 3));
        assertThrows(UnsupportedOperationException.class,
                () -> world.robots().put("hack", sniper));
    }

    @Test
    void constructorThrowsForZeroWidth() {
        assertThrows(IllegalArgumentException.class,
                () -> new World(0, 5, List.of()));
    }

    @Test
    void constructorThrowsForNullObstaclesList() {
        assertThrows(IllegalArgumentException.class,
                () -> new World(5, 5, null));
    }

    @Test
    void obstacleBlocksPositionOnItsBoundary() {
        assertTrue(centralObstacle.blocks(new Position(-1, 1)));
        assertTrue(centralObstacle.blocks(new Position(1, -1)));
    }
}