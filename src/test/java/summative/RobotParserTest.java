package summative;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RobotParserTest {

    @Test
    void parsesLowercaseCommandKey() {
        String json = "{\"robot\":\"HAL\",\"command\":\"forward\",\"arguments\":[5]}";
        assertEquals("forward", RobotParser.fixRobotParser(json));
    }

    @Test
    void parsesMixedCaseCommandKey() {
        String json = "{\"robot\":\"R2D2\",\"Command\":\"look\",\"arguments\":[]}";
        assertEquals("look", RobotParser.fixRobotParser(json));
    }

    @Test
    void returnsUnknownWhenKeyAbsent() {
        String json = "{\"robot\":\"EVE\",\"arguments\":[]}";
        assertEquals("unknown", RobotParser.fixRobotParser(json));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {""})
    void returnsUnknownForNullOrEmpty(String input) {
        assertEquals("unknown", RobotParser.fixRobotParser(input));
    }

    @Test
    void parsesLaunchCommand() {
        String json = "{\"robot\":\"BOT\",\"command\":\"launch\",\"arguments\":[\"sniper\",1,1]}";
        assertEquals("launch", RobotParser.fixRobotParser(json));
    }
}