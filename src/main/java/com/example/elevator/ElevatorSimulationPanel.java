package com.example.elevator;
/**
 * The ElevatorSimulationPanel class serves as the main container for
 * the Elevator Management System's graphical components. It includes
 * the ElevatorAnimator, ElevatorButton, and other UI elements for
 * presenting the elevator's state and receiving user input.
 */

import javax.swing.*;
import java.awt.*;

public class ElevatorSimulationPanel extends JPanel {
    private Elevator elevator;

    public ElevatorSimulationPanel(Elevator elevator) {
        this.elevator = elevator;
        setPreferredSize(new Dimension(400, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int floorHeight = getHeight() / 5;
        int elevatorY = getHeight() - (int) ((elevator.getPosition() - 1) * floorHeight) - floorHeight;

        g.setColor(Color.BLACK);
        g.fillRect(50, elevatorY, 100, floorHeight);

        if (elevator.areDoorsOpen()) {
            g.setColor(Color.GRAY);
            g.fillRect(50, elevatorY, 10, floorHeight);
            g.fillRect(140, elevatorY, 10, floorHeight);
        }
    }
}
