package summative;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private SniperRobot sniper;
    private TankRobot   tank;

    @BeforeEach
    void setUp() {
        sniper = new SniperRobot("HAL");
        tank   = new TankRobot("R2D2");
    }

    @Test
    void sniperMaxShieldsIsOne() {
        assertEquals(1, sniper.maxShields());
    }

    @Test
    void sniperMaxShotsIsOne() {
        assertEquals(1, sniper.maxShots());
    }

    @Test
    void tankMaxShieldsIsFive() {
        assertEquals(5, tank.maxShields());
    }

    @Test
    void tankMaxShotsIsThree() {
        assertEquals(3, tank.maxShots());
    }

    @Test
    void newRobotStatusIsNormal() {
        assertEquals(Robot.RobotStatus.NORMAL, sniper.status());
    }

    @Test
    void newRobotFacesNorth() {
        assertEquals(Direction.NORTH, tank.direction());
    }

    @Test
    void takeHitKillsSniperInOneHit() {
        sniper.takeHit();
        assertEquals(Robot.RobotStatus.DEAD, sniper.status());
        assertEquals(0, sniper.shields());
    }

    @Test
    void takeHitReducesTankShieldsByOne() {
        tank.takeHit();
        assertEquals(Robot.RobotStatus.NORMAL, tank.status());
        assertEquals(4, tank.shields());
    }

    @Test
    void takeHitKillsTankAfterFiveHits() {
        for (int i = 0; i < 5; i++) tank.takeHit();
        assertEquals(Robot.RobotStatus.DEAD, tank.status());
    }

    @Test
    void sniperIsInstanceOfRobot() {
        assertInstanceOf(Robot.class, sniper);
    }

    @Test
    void tankIsInstanceOfRobot() {
        assertInstanceOf(Robot.class, tank);
    }

    @Test
    void constructorThrowsForBlankName() {
        assertThrows(IllegalArgumentException.class, () -> new SniperRobot(""));
        assertThrows(IllegalArgumentException.class, () -> new TankRobot("  "));
    }

    @Test
    void toStringContainsName() {
        assertTrue(sniper.toString().contains("HAL"));
    }
}