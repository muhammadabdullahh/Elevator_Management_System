package com.example.elevator;
/**
 * The ElevatorAnimator class is responsible for visually animating
 * the movement and state changes of the Elevator. It handles the
 * rendering of the elevator's position, doors, and direction.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ElevatorAnimator {
    private Elevator elevator;
    private ElevatorSimulationPanel panel;
    private Timer animationTimer;
    private static final int DOOR_WAIT_TIME_MS = 3500;
    private List<FloorReachedListener> floorReachedListeners = new ArrayList<>();
    private Queue<Integer> floorQueue = new LinkedList<>();
    private boolean isWaiting = false;
    private List<DirectionChangedListener> directionChangedListeners = new ArrayList<>();
    private boolean isMoving = false;
    private Timer doorWaitTimer;

    public ElevatorAnimator(Elevator elevator, ElevatorSimulationPanel panel) {
        this.elevator = elevator;
        this.panel = panel;

        elevator.addDirectionChangedListener(direction -> panel.repaint());
    }

    public boolean isMoving() {
        return animationTimerRunning();
    }

    public void moveToFloor(int floor) {
        if (!floorQueue.contains(floor)) {
            floorQueue.add(floor);
        }

        if (!animationTimerRunning() && !isWaiting) {
            moveToNextFloor();
        }
    }

    private void moveToNextFloor() {
        if (isMoving) {
            return;
        }

        if (!floorQueue.isEmpty()) {
            Integer nextFloor = floorQueue.poll();
            double targetPosition = nextFloor;
            double distance = Math.abs(targetPosition - elevator.getPosition());

            if (distance > 0) {
                int duration = (int) Math.round(distance / (1.0 / 60.0)); // frames
                double step = distance / duration;

                animationTimer = new Timer(1000 / 60, new AnimationListener(targetPosition, step));
                animationTimer.start();
            }
        }
    }

    public void addFloorReachedListener(FloorReachedListener listener) {
        floorReachedListeners.add(listener);
    }

    private void notifyFloorReachedListeners(int floor) {
        floorReachedListeners.forEach(listener -> listener.onFloorReached(floor));
    }

    private boolean animationTimerRunning() {
        return animationTimer != null && animationTimer.isRunning();
    }

    public void addDirectionChangedListener(DirectionChangedListener listener) {
        directionChangedListeners.add(listener);
    }

    public interface DirectionChangedListener {
        void onDirectionChanged(Elevator.Direction direction);
    }

    private class AnimationListener implements ActionListener {
        private double targetPosition;
        private double step;

        public AnimationListener(double targetPosition, double step) {
            this.targetPosition = targetPosition;
            this.step = step;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double currentPosition = elevator.getPosition();

            if (Math.abs(currentPosition - targetPosition) <= step) {
                elevator.setPosition(targetPosition);
                elevator.moveToFloor((int) targetPosition);
                elevator.openDoors();
                panel.repaint();
                animationTimer.stop();

                // Notify listeners
                notifyFloorReachedListeners((int) targetPosition);

                // Wait before closing the doors
                if (!isWaiting) {
                    isWaiting = true;
                    if (doorWaitTimer != null) {
                        doorWaitTimer.stop();
                    }
                    doorWaitTimer = new Timer(DOOR_WAIT_TIME_MS, evt -> {
                        elevator.closeDoors();
                        panel.repaint();
                        isWaiting = false;

                        // Move to the next floor in the queue
                        moveToNextFloor();
                    });
                    doorWaitTimer.start();
                }
            } else {
                elevator.setPosition(currentPosition + (currentPosition < targetPosition ? step : -step));
                elevator.setDirection(currentPosition < targetPosition ? Elevator.Direction.UP : Elevator.Direction.DOWN);
                notifyDirectionChangedListeners(elevator.getDirection());
            }

            panel.repaint();
        }
    }

    private void notifyDirectionChangedListeners(Elevator.Direction direction) {
        directionChangedListeners.forEach(listener -> listener.onDirectionChanged(direction));
    }

    public interface FloorReachedListener {
        void onFloorReached(int floor);
    }

    public void handleEmergency() {
        if (animationTimerRunning()) {
            animationTimer.stop();
        }

        if (isWaiting) {
            isWaiting = false;
        }

        floorQueue.clear();
        int currentFloor = (int) Math.round(elevator.getPosition());
        if (currentFloor != 1) {
            moveToFloor(1);
        }
    }
}

