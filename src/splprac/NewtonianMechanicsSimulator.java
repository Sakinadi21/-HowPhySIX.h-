package splprac;

import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class NewtonianMechanicsSimulator extends JPanel implements ActionListener {
    private Timer timer;
    private int goatX = 50;
    private int manX = 800;
    private int goatY = 400;
    private int manY = 400;

    private int goatSpeed;
    private double manAcceleration;
    private double manVelocity = 0;

    private int reactionTriggerX;
    private boolean collided = false;
    private boolean reacted = false;
    private boolean maSoundPlayed = false;
    private boolean kickSoundPlayed = false;

    private Image goatImage, manImage, bgImage;

    public NewtonianMechanicsSimulator(int goatSpeed, double force, int reactionX) {
        this.goatSpeed = goatSpeed;
        this.manAcceleration = force / 10.0; 
        this.reactionTriggerX = reactionX;

        goatImage = new ImageIcon(getClass().getResource("/splprac/goat.png")).getImage();
        manImage = new ImageIcon(getClass().getResource("/splprac/man.png")).getImage();
        bgImage = new ImageIcon(getClass().getResource("/splprac/bgn.jpg")).getImage();

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

 
        g.setFont(new Font("Kalpurush", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("Newton's 1st Law: Goat walks at constant speed", 30, 30);
        g.drawString("Newton's 2nd Law: Man rushes with F = ma", 30, 50);
        g.drawString("Newton's 3rd Law: Goat kicks back!", 30, 70);

        g.drawImage(goatImage, goatX, goatY, 100, 100, this);
        g.drawImage(manImage, manX, manY, 100, 100, this);

        if (reacted) {
            g.setColor(Color.RED);
            g.drawString("MA!", goatX + 40, goatY - 10);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    
        if (!reacted) {
            goatX += goatSpeed;
            if (goatX > getWidth()) goatX = -100;
        }
        if (!collided) {
            manVelocity += manAcceleration;
            manX -= (int) manVelocity;
        }

        if (!collided && manX <= goatX + 80) {
            collided = true;
            manVelocity = 0;

            if (!maSoundPlayed) {
                playSound("ma.wav");
                maSoundPlayed = true;
            }
        }

        if (collided && !reacted && goatX >= reactionTriggerX) {
            reacted = true;
        }
        if (reacted) {
            manX += 5;
            manY -= 6;
            if (!kickSoundPlayed) {
                playSound("gkick.wav");
                kickSoundPlayed = true;
            }
        }

        repaint();
    }


    public void playSound(String fileName) {
        try {
            URL soundURL = getClass().getResource("/splprac/" + fileName);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + fileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            int speed = Integer.parseInt(JOptionPane.showInputDialog("Enter goat speed "));
            double force = Double.parseDouble(JOptionPane.showInputDialog("Enter man's force"));
            int reactionX = Integer.parseInt(JOptionPane.showInputDialog("Enter goat X for kick "));

            JFrame frame = new JFrame("Newton's Laws - Goat Story");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new NewtonianMechanicsSimulator(speed, force, reactionX));
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
        }
    }
}
