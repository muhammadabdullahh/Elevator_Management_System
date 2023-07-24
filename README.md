# Elevator Management System (EMS)

A simple and efficient Elevator Management System that simulates the movement of an elevator.

## Features

* Elevator position tracking
* Door open/close mechanism
* Direction control
* Emergency mode
* Event listeners for direction changes

## Requirements

* Java 8 or higher
* Maven (optional, if you want to build the project using Maven)

## Installation

1. Clone the repository or download the source code.
2. Navigate to the project's root directory (the one containing the `pom.xml` file).

## Building with Maven

1. Run `mvn clean install` to build the project.

2. Run `mvn test` to run the tests.

## Usage

This project is designed as a library. You can use the `Elevator` class in your own applications to simulate elevator behavior. Here's a simple example:

```java
import com.example.elevator.Elevator;
import com.example.elevator.Elevator.Direction;

public class ElevatorDemo {
    public static void main(String[] args) {
        Elevator elevator = new Elevator();

        elevator.addDirectionChangedListener(direction -> System.out.println("Elevator direction changed to: " + direction));

        elevator.moveToFloor(5);
        elevator.openDoors();
        elevator.closeDoors();
        elevator.moveToFloor(1);
    }
}
