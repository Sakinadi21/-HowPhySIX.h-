package splprac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PhysicsSimulatorSelection extends JFrame {
     CardLayout cardLayout;  //CardLayout eksate ank panel rakeh, but ekti panel dekha jai।
    JPanel mainPanel, selectionPanel; //mainPanel e shb panel rakar jonno main container
//selectionPanel e shb simulation button
    PhysicsSimulatorSelection() {
        setTitle("Physics Simulator");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout=new CardLayout();
        mainPanel=new JPanel(cardLayout);
        selectionPanel=new JPanel(new GridLayout(4, 2, 10, 10));
        selectionPanel.setBackground(Color.BLACK);
        String[] simulations = {
                "Projectile Motion","Dual Angle Trajectory","Multiple Projectile",
                "Car Collision","Newton's laws","Work", "Shooting Gun",
                "Gravity","Pendulum", "Periodic Motion", "Ideal Gas"
        };
        for(String sim:simulations) {
            JButton button=new JButton(sim);
            button.setFont(new Font("Kalpurush", Font.BOLD, 18));
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
//proti simulation er jnno button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    if(sim.equals("Projectile Motion")) {
                        showProjectileInputDialog();
                    } else if(sim.equals("Car Collision")) {
                        showCarCollisionInputDialog();
                    } else if(sim.equals("Shooting Gun")) {
                        showGunShootingInputDialog();
                    }
                    else if(sim.equals("Dual Angle Trajectory")) {
                        showMultipleProjectileInputDialog();
                    } else if(sim.equals("Multiple Projectile")) {
                        showMultiProjectileSimulationDialog();
                    }
                    else if(sim.equals("Newton's laws")) {
                        showMechanicsInputDialog();
                    }

                    else if(sim.equals("Pendulum")) {
                        showPendulumInputDialog();
                    } else if(sim.equals("Work")) {
                        showWorkSimulationInputDialog();
                    }
                    else if(sim.equals("Gravity")) {
                        showAppleSimulationInputDialog();
                    } else if(sim.equals("Periodic Motion")) {
                        showPeriodicMotionInputDialog();
                    }
                    else if(sim.equals("Ideal Gas")) {
                        showIdealGasSimulationDialog();
                    }

                    //user button click e actionPerformed() er vitore method call hbe ।
                }
            });
            selectionPanel.add(button);
        }
        mainPanel.add(selectionPanel, "Selection");
// proti ti simulation er janno panel create
        for(String sim:simulations) {
            JPanel simPanel=new JPanel();
            simPanel.setBackground(Color.BLUE);
            simPanel.setLayout(new BorderLayout());
            JLabel label=new JLabel(sim,SwingConstants.CENTER);
            label.setFont(new Font("Kalpurush", Font.BOLD, 30));
            label.setForeground(Color.WHITE);
            simPanel.add(label,BorderLayout.CENTER);
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            simPanel.add(backButton, BorderLayout.SOUTH);
            mainPanel.add(simPanel, sim);
        }
        add(mainPanel);
        cardLayout.show(mainPanel, "Selection");
    }

    private void showProjectileInputDialog() {
        String velocityInput = JOptionPane.showInputDialog("Enter initial velocity (m/s): ");
        String angleInput = JOptionPane.showInputDialog("Enter angle of projection (degrees): ");
        try {
            double velocity = Double.parseDouble(velocityInput);
            double angle = Double.parseDouble(angleInput);

            JPanel projectilePanel = new JPanel(new BorderLayout());
            projectilePanel.add(new ProjectileSimulator(velocity, angle), BorderLayout.CENTER);

            // Create and style Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Add back button to bottom of the panel
            projectilePanel.add(backButton, BorderLayout.SOUTH);

            mainPanel.add(projectilePanel, "Projectile Motion");
            cardLayout.show(mainPanel, "Projectile Motion");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for velocity and angle.");
        }
    }


    private void showCarCollisionInputDialog() {
        try {
            double mass1 = Double.parseDouble(JOptionPane.showInputDialog("Enter mass of Car 1 (kg): "));
            double mass2 = Double.parseDouble(JOptionPane.showInputDialog("Enter mass of Car 2 (kg): "));
            double speed1 = Double.parseDouble(JOptionPane.showInputDialog("Enter speed of Car 1 : "));
            double speed2 = Double.parseDouble(JOptionPane.showInputDialog("Enter speed of Car 2 : "));

            CarCollisionSimulator simulation = new CarCollisionSimulator(mass1, mass2, speed1, speed2);

            JPanel simulationPanel = new JPanel(new BorderLayout());
            simulationPanel.add(simulation, BorderLayout.CENTER);

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(simulationPanel);
                if (window != null) window.dispose();
            });
            simulationPanel.add(backButton, BorderLayout.SOUTH);

            JFrame frame = new JFrame("Car Collision Simulation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1600, 800);
            frame.setLocationRelativeTo(null);  // Center on screen
            frame.add(simulationPanel);
            frame.setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values.");
        }
    }


    private void showMultipleProjectileInputDialog() {
        try {
            double velocity1 = Double.parseDouble(JOptionPane.showInputDialog("Enter velocity 1 (m/s):"));
            double angle1 = Double.parseDouble(JOptionPane.showInputDialog("Enter angle 1 (degrees):"));
            double velocity2 = Double.parseDouble(JOptionPane.showInputDialog("Enter velocity 2 (m/s):"));
            double angle2 = Double.parseDouble(JOptionPane.showInputDialog("Enter angle 2 (degrees):"));

            // Create the simulation component
            proj simulation = new proj(velocity1, angle1, velocity2, angle2);

            // Create panel with layout
            JPanel simulationPanel = new JPanel(new BorderLayout());
            simulationPanel.add(simulation, BorderLayout.CENTER);

            // Create and style Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(simulationPanel);
                if (window != null) window.dispose();  // Close only this window
            });

            // Add Back button at the bottom
            simulationPanel.add(backButton, BorderLayout.SOUTH);

            // Set up the frame
            JFrame frame = new JFrame("Projectile Motion Simulation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(simulationPanel);
            frame.setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
        }
    }


    private void showGunShootingInputDialog() {
        String bulletSpeedInput = JOptionPane.showInputDialog("Enter bullet speed : ");
        try {
            double bulletSpeed = Double.parseDouble(bulletSpeedInput);

            // Create main panel to hold simulation and back button
            JPanel gunPanel = new JPanel(new BorderLayout());

            GunShootingSimulator gunShootingSimulator = new GunShootingSimulator(bulletSpeed);
            gunPanel.add(gunShootingSimulator, BorderLayout.CENTER);

            // Create and style Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

            // Close the frame when Back is pressed
            backButton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(gunPanel);
                if (window != null) window.dispose();
            });

            gunPanel.add(backButton, BorderLayout.SOUTH);

            // Create frame and show simulation
            JFrame frame = new JFrame("Gun Shooting Simulation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1600, 800);
            frame.add(gunPanel);
            frame.setVisible(true);

            // Start firing after short delay
            Timer fireTimer = new Timer(3, e -> gunShootingSimulator.fireBullet());
            fireTimer.setRepeats(false);
            fireTimer.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for bullet speed.");
        }
    }


    private void showPeriodicMotionInputDialog() {
        String ampStr = JOptionPane.showInputDialog("Enter amplitude:");
        String freqStr = JOptionPane.showInputDialog("Enter frequency (Hz):");

        try {
            double amplitude = Double.parseDouble(ampStr);
            double frequency = Double.parseDouble(freqStr);

            // Create main panel with layout
            JPanel motionPanel = new JPanel(new BorderLayout());

            // Add the periodic motion simulation in the center
            motionPanel.add(new PeriodicMotion(amplitude, frequency), BorderLayout.CENTER);

            // Create and style the Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Add the Back button to the bottom of the panel
            motionPanel.add(backButton, BorderLayout.SOUTH);

            // Add to card layout and switch to it
            mainPanel.add(motionPanel, "Periodic Motion");
            cardLayout.show(mainPanel, "Periodic Motion");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for amplitude and frequency.");
        }
    }

    private void showPendulumInputDialog() {
        String lengthStr = JOptionPane.showInputDialog("Enter pendulum length (meters):");
        String angleStr = JOptionPane.showInputDialog("Enter max angle (degrees):");

        try {
            double length = Double.parseDouble(lengthStr);
            double maxAngle = Double.parseDouble(angleStr);

            // Create main panel with layout
            JPanel pendulumPanel = new JPanel(new BorderLayout());

            // Add pendulum simulation in center
            pendulumPanel.add(new PendulumWithImages(length, maxAngle), BorderLayout.CENTER);

            // Create and style Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Add button at the bottom of the panel
            pendulumPanel.add(backButton, BorderLayout.SOUTH);

            // Add to main panel and switch to it
            mainPanel.add(pendulumPanel, "Pendulum Motion");
            cardLayout.show(mainPanel, "Pendulum Motion");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for length and angle.");
        }
    }


    private void showWorkSimulationInputDialog() {
        String massInput = JOptionPane.showInputDialog("Enter mass (kg):");
        String heightInput = JOptionPane.showInputDialog("Enter height (m):");

        try {
            double mass = Double.parseDouble(massInput);
            double height = Double.parseDouble(heightInput);
            double gravity = 9.8;

            // Create simulation panel
            JPanel workPanel = new JPanel(new BorderLayout());
            workPanel.add(new WorkSimulation(mass, height, gravity), BorderLayout.CENTER);

            // Create and style Back button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Add Back button to the bottom of the panel
            workPanel.add(backButton, BorderLayout.SOUTH);

            // Add to main panel and show
            mainPanel.add(workPanel, "Work Simulation");
            cardLayout.show(mainPanel, "Work Simulation");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for mass and height.");
        }
    }


    private void showAppleSimulationInputDialog() {
        String input = JOptionPane.showInputDialog("Enter initial velocity of apple:");

        try {
            double initialVelocity = Double.parseDouble(input);

            // Create a label for velocity display
            JLabel velocityLabel = new JLabel("Velocity: 0 px/frame", SwingConstants.CENTER);
            velocityLabel.setFont(new Font("Kalpurush", Font.BOLD, 16));

            // Create Back Button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Create simulation panel with layout
            JPanel applePanel = new JPanel(new BorderLayout());
            applePanel.add(velocityLabel, BorderLayout.NORTH);
            applePanel.add(new AppleFallingSimulation(initialVelocity, velocityLabel), BorderLayout.CENTER);
            applePanel.add(backButton, BorderLayout.SOUTH);  // Add the back button at the bottom

            // Add and show the panel
            mainPanel.add(applePanel, "Apple Falling Simulation");
            cardLayout.show(mainPanel, "Apple Falling Simulation");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
        }
    }

    private void showIdealGasSimulationDialog() {
        try {
            String numStr = JOptionPane.showInputDialog("Enter number of particles:");
            int numParticles = Integer.parseInt(numStr);

            String speedStr = JOptionPane.showInputDialog("Enter speed of particles (e.g., 2):");
            int speed = Integer.parseInt(speedStr);

            // Create simulation panel
            IdealGasSimulation gasPanel = new IdealGasSimulation(numParticles, speed);
            gasPanel.setLayout(new BorderLayout()); // Ensure layout supports placement

            // Create Back Button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            // Add Back button to bottom
            gasPanel.add(backButton, BorderLayout.SOUTH);

            // Add to main panel and show
            mainPanel.add(gasPanel, "Ideal Gas Simulation");
            cardLayout.show(mainPanel, "Ideal Gas Simulation");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
        }
    }

    private void showMultiProjectileSimulationDialog() {
        try {
            String countStr = JOptionPane.showInputDialog(this, "How many projectiles?");
            int count = Integer.parseInt(countStr);

            java.util.List<Double> velocities = new ArrayList<>();
            java.util.List<Double> angles = new ArrayList<>();

            for (int i = 1; i <= count; i++) {
                String vStr = JOptionPane.showInputDialog(this, "Enter velocity " + i + " (m/s):");
                String aStr = JOptionPane.showInputDialog(this, "Enter angle " + i + " (degrees):");
                velocities.add(Double.parseDouble(vStr));
                angles.add(Double.parseDouble(aStr));
            }

            // Create simulation panel
            MultiProjectileSimulation simPanel = new MultiProjectileSimulation(velocities, angles);
            simPanel.setLayout(new BorderLayout()); // Set layout to place back button

            // Add back button directly to the simulation panel
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
            backButton.setBackground(Color.BLACK);
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

            simPanel.add(backButton, BorderLayout.SOUTH);

            // Add to mainPanel and show it
            mainPanel.add(simPanel, "MultiProjectile");
            cardLayout.show(mainPanel, "MultiProjectile");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
        }
    }

    private void showMechanicsInputDialog() {
        try {
            String speedInput = JOptionPane.showInputDialog(this, "Enter goat speed:");
            String forceInput = JOptionPane.showInputDialog(this, "Enter man's force:");
            String reactionXInput = JOptionPane.showInputDialog(this, "Enter goat X for kick:");

            int speed = Integer.parseInt(speedInput);
            double force = Double.parseDouble(forceInput);
            int reactionX = Integer.parseInt(reactionXInput);

            // Check if the panel already exists
            Component[] components = mainPanel.getComponents();
            boolean panelExists = false;
            for (Component c : components) {
                if (c.getName() != null && c.getName().equals("NewtonPanel")) {
                    panelExists = true;
                    break;
                }
            }

            if (!panelExists) {
                JPanel mechanicsPanel = new JPanel(new BorderLayout());
                mechanicsPanel.setName("NewtonPanel"); // name for checking later
                mechanicsPanel.add(new NewtonianMechanicsSimulator(speed, force, reactionX), BorderLayout.CENTER);

                JButton backButton = new JButton("Back");
                backButton.setFont(new Font("Kalpurush", Font.BOLD, 20));
                backButton.setBackground(Color.BLACK);
                backButton.setForeground(Color.WHITE);
                backButton.setFocusPainted(false);
                backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                backButton.addActionListener(e -> cardLayout.show(mainPanel, "Selection"));

                mechanicsPanel.add(backButton, BorderLayout.SOUTH);
                mainPanel.add(mechanicsPanel, "Newton's laws");
            }

            cardLayout.show(mainPanel, "Newton's laws");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers.");
        }
    }


    public static void main(String[] args) {
        PhysicsSimulatorSelection frame = new PhysicsSimulatorSelection();
        frame.setVisible(true);
    }
}
