# Assessment 005 - Summative

## Learning Outcomes assessed

- Java Syntax & Debugging
- OOP (Encapsulation)
- OOP (Inheritance & Abstraction)
- Collections & Data Structures
- Enums & Lifecycle State
- Client/Server Architecture & Networking
- JSON Protocol & Serialisation
- Unit Testing
---

## `coding/` Questions

The skeleton project is provided via Maven. All coding questions have starter code with deliberate errors or gaps. Tests are provided — your implementation must make them pass. **Do not modify the test files.**
 
---

### Question 1 — `fixRobotParser(String json)`

During the Robot World project, your team wrote a method to extract the `command` field from an incoming JSON request string. The method has multiple syntax errors and one logical error. Fix all of them so the tests pass.

The method must return the value of the `"command"` key from a raw JSON string, or `"unknown"` if the key is not present. The key match must be case-insensitive (i.e. `"Command"` and `"COMMAND"` should also match).

- **Input:** `{"robot":"HAL","command":"forward","arguments":[5]}`
- **Output:** `"forward"`
  | Input | Expected return |
  | --- | --- |
  | `{"robot":"HAL","command":"forward","arguments":[5]}` | `"forward"` |
  | `{"robot":"R2D2","Command":"look","arguments":[]}` | `"look"` |
  | `{"robot":"EVE","arguments":[]}` | `"unknown"` |
  | `null` | `"unknown"` |

> **Note:** There are exactly 6 errors in the starter code. The final `return` statement is correct — do not change it.
 
---

### Question 2 — `Position` and `move(Direction direction, int steps)`

Implement the `Position` class and its `move` method from the Robot World server. This is a greenfield task — the full method signature and fields are described below, but you write the implementation from scratch.

The class must be **immutable** — `move()` returns a new `Position` and leaves the original unchanged.

Movement rules from the Robot World spec:

- `NORTH` increases `y`
- `SOUTH` decreases `y`
- `EAST` increases `x`
- `WEST` decreases `x`
#### Fields

| Field | Type | Details |
| --- | --- | --- |
| `x` | `int` | Horizontal coordinate. **Private and immutable.** |
| `y` | `int` | Vertical coordinate. **Private and immutable.** |

#### Methods

| Method | Details |
| --- | --- |
| `x()` | Returns `x`. No setter. |
| `y()` | Returns `y`. No setter. |
| `move(Direction direction, int steps)` | Returns a new `Position` offset by `steps` in `direction`. Throws `IllegalArgumentException` if `steps < 1`. |
| `equals(Object o)` | Two positions are equal if their `x` and `y` values match. |
| `toString()` | Returns the position formatted as `"[x,y]"` e.g. `"[3,-2]"`. |

```java
public enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

- **Input:** `new Position(0, 0).move(Direction.NORTH, 3)`
- **Output:** a `Position` equal to `[0,3]`
  | Call | Expected result |
  | --- | --- |
  | `new Position(0,0).move(NORTH, 3)` | `[0,3]` |
  | `new Position(2,-1).move(WEST, 2)` | `[0,-1]` |
  | `new Position(0,0).move(SOUTH, 1)` | `[0,-1]` |
  | `new Position(1,1).move(EAST, 5)` | `[6,1]` |
  | `new Position(0,0).move(NORTH, 0)` | throws `IllegalArgumentException` |

> **Note:** Immutability is tested explicitly. The original `Position` must be unchanged after calling `move()`.
 
---

### Question 3 — `Robot`, `SniperRobot`, and `TankRobot`

Implement an abstract `Robot` base class and two concrete subclasses — `SniperRobot` and `TankRobot` — that differ in their default configuration. This mirrors the "makes of robots" concept from the Robot World client requirements.

#### `Robot` (Abstract Class)

**File:** `src/main/java/za/co/wethinkcode/robot/Robot.java`

##### Nested Enum: `RobotStatus`

```java
public enum RobotStatus { NORMAL, REPAIR, RELOAD, DEAD }
```

##### Fields

| Field | Type | Details |
| --- | --- | --- |
| `name` | `String` | The robot's name. **Private and immutable.** |
| `position` | `Position` | Current position. **Private.** Defaults to `[0,0]`. |
| `direction` | `Direction` | Direction the robot faces. **Private.** Defaults to `NORTH`. |
| `shields` | `int` | Current shield hits remaining. **Private.** |
| `shots` | `int` | Current shots remaining. **Private.** |
| `status` | `RobotStatus` | Operational status. **Private.** Defaults to `NORMAL`. |

##### Constructor

Accepts `name`, `maxShields`, and `maxShots`. Sets `shields = maxShields` and `shots = maxShots`. Throws `IllegalArgumentException` if `name` is `null` or blank.

##### Methods

| Method | Details |
| --- | --- |
| `name()` | Returns `name`. No setter. |
| `position()` | Returns current position. |
| `direction()` | Returns current direction. |
| `shields()` | Returns current shield strength. |
| `shots()` | Returns shots remaining. |
| `status()` | Returns current status. |
| `updatePosition(Position)` | Updates position. |
| `updateDirection(Direction)` | Updates direction. |
| `updateStatus(RobotStatus)` | Updates status. |
| `takeHit()` | Reduces `shields` by 1. If `shields` reaches `0`, sets `status` to `DEAD`. |
| `maxShields()` | **Abstract.** Each subclass returns its configured maximum. |
| `maxShots()` | **Abstract.** Each subclass returns its configured maximum. |
| `toString()` | Includes `name`, `position`, `direction`, `shields`, `shots`, and `status`. |
 
---

#### `SniperRobot`

**File:** `src/main/java/za/co/wethinkcode/robot/SniperRobot.java`

A long-range, fragile robot.

| | Details |
| --- | --- |
| Extends | `Robot` |
| `maxShields()` | Returns `1` |
| `maxShots()` | Returns `1` |
 
---

#### `TankRobot`

**File:** `src/main/java/za/co/wethinkcode/robot/TankRobot.java`

A heavily armoured robot with fewer shots.

| | Details |
| --- | --- |
| Extends | `Robot` |
| `maxShields()` | Returns `5` |
| `maxShots()` | Returns `3` |
 
---

### Question 4 — `Obstacle` and `World`

Implement the `Obstacle` class and the core of the `World` class. This is the server-side domain model from the Robot World spec — a configurable grid that holds robots and obstacles and enforces movement boundaries.

#### `Obstacle`

**File:** `src/main/java/za/co/wethinkcode/world/Obstacle.java`

A rectangular region of the world that blocks robot movement.

##### Fields

| Field | Type | Details |
| --- | --- | --- |
| `topLeft` | `Position` | Top-left corner of the obstacle. **Private and immutable.** |
| `bottomRight` | `Position` | Bottom-right corner. **Private and immutable.** |

##### Methods

| Method | Details |
| --- | --- |
| `topLeft()` | Returns `topLeft`. |
| `bottomRight()` | Returns `bottomRight`. |
| `blocks(Position p)` | Returns `true` if position `p` falls inside or on the boundary of this obstacle. |
| `toString()` | Returns a readable summary including both corner positions. |
 
---

#### `World`

**File:** `src/main/java/za/co/wethinkcode/world/World.java`

The server's world grid. The centre is always `[0,0]`. Valid x-coordinates are `[-width, width]` and valid y-coordinates are `[-height, height]`.

##### Fields

| Field | Type | Details |
| --- | --- | --- |
| `width` | `int` | Half-width of the world. **Private and immutable.** |
| `height` | `int` | Half-height of the world. **Private and immutable.** |
| `obstacles` | `List<Obstacle>` | All obstacles. **Private.** Stored as a defensive copy. |
| `robots` | `Map<String, Robot>` | All robots keyed by name. **Private.** |

##### Constructor

Accepts `width`, `height`, and `List<Obstacle> obstacles`. Throws `IllegalArgumentException` if `width < 1`, `height < 1`, or `obstacles` is `null`. Stores a **defensive copy** of the obstacles list.

##### Methods

| Method | Details |
| --- | --- |
| `width()` | Returns `width`. |
| `height()` | Returns `height`. |
| `obstacles()` | Returns an **unmodifiable** view of the obstacles list. |
| `robots()` | Returns an **unmodifiable** view of the robots map. |
| `isInBounds(Position p)` | Returns `true` if `-width <= p.x() <= width` AND `-height <= p.y() <= height`. |
| `isBlocked(Position p)` | Returns `true` if any obstacle blocks `p` OR another robot already occupies `p`. |
| `addRobot(Robot robot, Position startPosition)` | Adds the robot at `startPosition` if in bounds and not blocked. Returns `true` on success, `false` otherwise. |
| `listRobots()` | Returns robot names in insertion order as a `List<String>`. |

> **Design Tip:** `isBlocked()` must check both the obstacles list and the robots map. A position occupied by another robot is just as blocked as one inside a mountain.
 
---

## UML Class Diagram

Produce a UML class diagram for this project using [draw.io](https://draw.io). No other tool is allowed.

Your diagram must include all seven types (`Position`, `Direction`, `Obstacle`, `World`, `Robot`, `SniperRobot`, `TankRobot`) and show the following for each:

- Class or enum name
- All fields with their types and access modifiers (`+` public, `-` private, `#` protected)
- All methods with their return types and parameters
- Relationships between types — inheritance arrows where one class extends another, association arrows where one class holds a reference to another, and a dependency arrow from `World` to `Obstacle`
  Export your diagram as a **PNG or PDF** and place it in the root of your project named `uml.pdf` or `uml.png`.

---

## Long-Format Questions

Please answer these in `answers.txt`. **Do not remove the comments and do not change the format.**
 
---

### Comprehension Question 1 — Encapsulation (10 Points)

Your teammate has reviewed your `World` class and left this comment in the code review:

> *"Why are you returning `Collections.unmodifiableList(obstacles)` instead of just returning `obstacles` directly? The caller can already see the list — what difference does it make?"*

Write your reply to this code review comment. Your explanation should cover what encapsulation is and why it matters, what an unmodifiable view achieves that a direct reference does not, and give a concrete example of the kind of bug that could happen in the Robot World system if the obstacles list were exposed directly.
 
---

### Comprehension Question 2 — Inheritance and Abstraction (10 Points)

A new team member is looking at the `Robot` abstract class and the `SniperRobot` and `TankRobot` subclasses. They ask:

> *"Why don't you just have one `Robot` class with a `type` field set to `"sniper"` or `"tank"` and use if-statements in the methods? What does making it abstract actually give you?"*

Explain abstract classes and inheritance to your teammate. Your answer should cover what an abstract class is and what the `abstract` keyword forces subclasses to do, why that is preferable to a single class with `if` or `switch` on a type field, and what would need to change if the team wanted to add a third robot make — `ScoutRobot` — to the system.
 
---

### Comprehension Question 3 — Concurrency and Threads (15 Points)

Your Robot World server works perfectly when you test it with one client. As soon as a second client connects, strange things start happening — robots teleport, positions get corrupted, and sometimes the server crashes with a `ConcurrentModificationException`.

Your tech lead says: *"This is a classic concurrency bug. You need to understand threads before we can fix it."*

Explain concurrency and threads in writing. Your answer should cover what a thread is and why the server creates a new one per client connection (refer to the `MultiServers` example from the project), what a race condition is and why it causes the bugs described above, what the shared state in your World server is and why it is at risk, and at least one concrete strategy for protecting that shared state. You do not need to write code — describe the concept and the approach clearly enough that a teammate could act on it.
 
---

### Comprehension Question 4 — JSON Protocol and Serialisation (10 Points)

A product manager watching your Robot World demo asks:

> *"I get that the client sends messages to the server. But why JSON? Why not just send the command as a plain string — like just sending the word `forward`?"*

Explain the Robot World protocol design to this product manager. Your answer should cover what serialisation is and why data needs to be converted before sending it over a network, what JSON is and what makes it suitable as a protocol format between two Java programs, why a structured format like `{"robot":"HAL","command":"forward","arguments":[5]}` is better than a plain string for this system, and what deserialisation means on the server side when it receives that message.
 
---

### Comprehension Question 5 — Unit Testing and TDD (5 Points)

Looking back at the Robot World project, your team wrote most of the tests after the implementation was already done. Your tech lead says that is the wrong order and asks you to reflect on why.

Explain what Test-Driven Development is and why the order matters. Your answer should cover what TDD means (the red-green-refactor cycle), why writing tests first changes how you design your classes, and give one specific example from the Robot World system — a method or class — where writing the test first would have caught a design problem earlier. Keep your answer grounded in code you actually wrote or saw during the project.
 