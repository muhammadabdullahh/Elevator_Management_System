package com.example.elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorAnimatorTest {
    private Elevator elevator;
    private ElevatorSimulationPanel panel;
    private ElevatorAnimator animator;

    @BeforeEach
    void setUp() {
        elevator = new Elevator();
        panel = new ElevatorSimulationPanel(elevator);
        animator = new ElevatorAnimator(elevator, panel);
        elevator.moveToFloor(1); // Set the initial position of the elevator to floor 1
        elevator.setDirection(Elevator.Direction.NONE); // Set the initial direction of the elevator to NONE
    }

    // Elevator tests
    @Test
    void testElevatorGetPosition() {
        assertEquals(1, Math.round(elevator.getPosition()));
    }

    @Test
    void testElevatorSetPosition() {
        elevator.setPosition(3.0);
        assertEquals(3, Math.round(elevator.getPosition()));
    }

    // ElevatorAnimator tests
    @Test
    void testInitialState() {
        assertFalse(animator.isMoving());
        assertEquals(Elevator.Direction.NONE, elevator.getDirection());
        assertEquals(1, Math.round(elevator.getPosition()));
    }

    @Test
    void testMoveToSingleFloor() throws InterruptedException {
        animator.moveToFloor(3);
        Thread.sleep(5000); // Sleep for 5 seconds to allow the elevator to move
        assertEquals(3, Math.round(elevator.getPosition()));
    }

    @Test
    void testMoveToMultipleFloors() throws InterruptedException {
        animator.moveToFloor(3);
        animator.moveToFloor(5);
        Thread.sleep(8000); // Sleep for 8 seconds to allow the elevator to move to both floors
        assertEquals(5, Math.round(elevator.getPosition()));
    }

    // ElevatorButton tests
    @Test
    void testElevatorButtonActionPerformed() {
        // Implement the test for the actionPerformed method of ElevatorButton.
    }

    // ElevatorGUI tests
    @Test
    void testElevatorGUICreateAndShowGUI() {
        // Implement the test for the createAndShowGUI method of ElevatorGUI.
    }

    // ElevatorSimulationPanel tests
    @Test
    void testElevatorSimulationPanelPaintComponent() {
        // Implement the test for the paintComponent method of ElevatorSimulationPanel.
    }

    @Test
    void testElevatorSimulationPanelUpdate() {
        // Implement the test for the update method of ElevatorSimulationPanel.
    }
}
