import javax.swing.*;
import java.awt.*;

class GradientColor extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle for the button shape
        int arcWidth = 50;
        int arcHeight = 50;
        int rectWidth = getWidth();
        int rectHeight = getHeight();

        // Define the gradient with specific RGB colors
        Color startColor = new Color(255, 204, 102);  // RGB for yellowish
        Color endColor = new Color(102, 204, 255);    // RGB for bluish

        // Linear gradient across the button
        GradientPaint gradient = new GradientPaint(0, 0, startColor, rectWidth, 0, endColor);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, rectWidth, rectHeight, arcWidth, arcHeight);
    }
}
