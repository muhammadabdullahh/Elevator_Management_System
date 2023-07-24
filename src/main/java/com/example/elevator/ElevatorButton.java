package com.example.elevator;
/**
 * The ElevatorButton class represents a button inside the elevator.
 * It is responsible for triggering the elevator to move to the
 * corresponding floor when pressed.
 */

public class ElevatorButton {
    private int floor;
    private boolean isPressed;

    public ElevatorButton(int floor) {
        this.floor = floor;
        isPressed = false;
    }

    public void press() {
        isPressed = true;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public int getFloor() {
        return floor;
    }
}
