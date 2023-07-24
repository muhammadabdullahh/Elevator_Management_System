package com.example.elevator;

/**
 * The Elevator class represents an elevator in a building, providing
 * functionality for managing its position, doors, and direction.
 * This class also allows for listening to direction changes and handles
 * emergency mode.
 */

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    public enum Direction {
        UP("^"), DOWN("v"), NONE("-");

        private String symbol;

        Direction(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private double position;
    private boolean doorsOpen;
    private Direction direction;
    private List<DirectionChangedListener> directionChangedListeners;
    private boolean emergencyMode = false;


    public Elevator() {
        position = 1;
        doorsOpen = false;
        direction = Direction.NONE;
        directionChangedListeners = new ArrayList<>();
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public boolean areDoorsOpen() {
        return doorsOpen;
    }

    public void openDoors() {
        doorsOpen = true;
    }

    public void closeDoors() {
        doorsOpen = false;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        notifyDirectionChangedListeners();
    }

    public void moveToFloor(int floor) {
        setDirection(floor > position ? Direction.UP : Direction.DOWN);
    }

    public void addDirectionChangedListener(DirectionChangedListener listener) {
        directionChangedListeners.add(listener);
    }

    private void notifyDirectionChangedListeners() {
        directionChangedListeners.forEach(listener -> listener.onDirectionChanged(direction));
    }

    public boolean isInEmergencyMode() {
        return emergencyMode;
    }

    public void setInEmergencyMode(boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }

    public interface DirectionChangedListener {
        void onDirectionChanged(Direction direction);
    }

    public int getCurrentFloor() {
        return (int) Math.round(position);
    }
}
