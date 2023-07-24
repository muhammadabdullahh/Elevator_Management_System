package com.example.elevator;
/**
 * The ElevatorGUI class is the main graphical user interface for the
 * Elevator Management System. It provides the user with a way to
 * interact with the elevator system, displaying the Elevator's state
 * and allowing for floor selection.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ElevatorGUI extends JFrame {
    private Elevator elevator;
    private ElevatorSimulationPanel simulationPanel;
    private ElevatorAnimator animator;
    private Set<Integer> requestedFloors = new HashSet<>();
    private Map<Integer, JButton> floorButtons = new HashMap<>();
    private boolean emergencyMode = false;
    private JLabel statusLabel;
    

    public ElevatorGUI() {
        setTitle("Elevator Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        elevator = new Elevator();
        simulationPanel = new ElevatorSimulationPanel(elevator);
        animator = new ElevatorAnimator(elevator, simulationPanel);

        JPanel buttonPanel = createButtonPanel();
        add(simulationPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        statusLabel = new JLabel();
        updateStatusLabel();
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.NORTH);

        animator.addFloorReachedListener(floor -> {
            requestedFloors.remove(floor);
            JButton button = floorButtons.get(floor);
            if (button != null) {
                button.setEnabled(true);
            }
        });

        animator.addDirectionChangedListener(direction -> updateStatusLabel());

        animator.addFloorReachedListener(floor -> {
            updateStatusLabel();
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));

        for (int i = 5; i >= 1; i--) {
            int floor = i;
            JButton button = new JButton("Floor " + floor);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!emergencyMode && !requestedFloors.contains(floor)) {
                        requestedFloors.add(floor);
                        animator.moveToFloor(floor);
                        button.setEnabled(false);
                    }
                    if (elevator.getCurrentFloor() == floor){
                        requestedFloors.remove(floor);
                        button.setEnabled(true);
                    }
                }
            });
            floorButtons.put(floor, button);
            buttonPanel.add(button);
        }

        JButton emergencyButton = new JButton("Emergency");
        emergencyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emergencyMode = !emergencyMode;
                if (emergencyMode) {
                    animator.handleEmergency();
                    for (JButton button : floorButtons.values()) {
                        button.setEnabled(false);
                    }
                } else {
                    for (Map.Entry<Integer, JButton> entry : floorButtons.entrySet()) {
                        if (!requestedFloors.contains(entry.getKey())) {
                            entry.getValue().setEnabled(true);
                        }
                    }
                }
            }
        });

        buttonPanel.add(emergencyButton);

        return buttonPanel;
    }

    private void updateStatusLabel() {
        Elevator.Direction direction = elevator.getDirection();
        statusLabel.setText("Floor: " + elevator.getCurrentFloor() + ", Direction: " + direction);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ElevatorGUI gui = new ElevatorGUI();
            gui.setVisible(true);
        });
    }
}
