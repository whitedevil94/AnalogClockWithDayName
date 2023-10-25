import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnalogClockWithNumbers
{
    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Analog Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        ClockPanel clockPanel = new ClockPanel();
        frame.add(clockPanel);

        frame.setVisible(true);

        Timer timer = new Timer(1000, e -> 
        {
            clockPanel.repaint();
        });
        timer.start();
    }

    static class ClockPanel extends JPanel 
    {
        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);

            // Get the current time
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss E");
            String time = dateFormat.format(now);

            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            int clockRadius = Math.min(width, height) / 2 - 10;

            // Draw clock face (dark blue)
            g.setColor(new Color(0, 0, 139)); // Dark blue color
            g.fillOval(centerX - clockRadius, centerY - clockRadius, 2 * clockRadius, 2 * clockRadius);

            // Draw clock numbers inside the clock (yellow)
            g.setColor(Color.YELLOW); // Yellow color
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            for (int i = 1; i <= 12; i++) 
            {
                double angle = Math.toRadians(90 - (i / 12.0) * 360);
                int x = centerX + (int) (0.8 * clockRadius * Math.cos(angle));
                int y = centerY - (int) (0.8 * clockRadius * Math.sin(angle));
                String number = Integer.toString(i);
                int numberWidth = g.getFontMetrics().stringWidth(number);
                int numberHeight = g.getFontMetrics().getHeight();
                g.drawString(number, x - numberWidth / 2, y + numberHeight / 2);
            }

            // Draw clock hands (yellow)
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g.setColor(Color.YELLOW); // Yellow color
            int second = now.getSeconds();
            int minute = now.getMinutes();
            int hour = now.getHours() % 12;

            drawHand(g2, centerX, centerY, second, 60, clockRadius - 30); // Shorter hands
            drawHand(g2, centerX, centerY, minute, 60, clockRadius - 50); // Shorter hands
            drawHand(g2, centerX, centerY, hour * 5 + minute / 12, 60, clockRadius - 70); // Shorter hands

            // Display digital time (black)
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.setColor(Color.BLACK); // Black color
            g.drawString(time, 10, 30);
        }

        private void drawHand(Graphics2D g, int x, int y, int value, int range, int length) 
        {
            double angle = Math.toRadians(90 - (value / (double) range) * 360);
            int x2 = x + (int) (length * Math.cos(angle));
            int y2 = y - (int) (length * Math.sin(angle));
            g.drawLine(x, y, x2, y2);
        }
    }
}
