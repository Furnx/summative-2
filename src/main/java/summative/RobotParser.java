package summative;

public class RobotParser {

    // Question 1

    public static String fixRobotParser(String json) {
        if (json = null || json.isEmpty()) {      // error 1
            return null;                           // error 2: wrong sentinel
        }

        String lower = json.toLower();             // error 3
        int keyIndex = lower.indexOf("command":);  // error 4

        if (keyIndex = -1) {                       // error 5
            return "unknown"                       // error 6
        }

        int colonIndex = json.indexOf(':', keyIndex);
        int quoteStart = json.indexOf('"', colonIndex + 1);
        int quoteEnd   = json.indexOf('"', quoteStart + 1);

        return json.substring(quoteStart + 1, quoteEnd);
    }
}