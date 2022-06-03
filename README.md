## Running the Application

```bash
sbt "runMain org.lcarvalho.robot.Application"
```

## Running All Tests

```bash
sbt test
```

## Running a specific test

```bash
sbt "testOnly *{TestName}"
```

### Example

```bash
sbt "testOnly *RobotSpec"
```
