import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int cornerRadius;
    private boolean bottomRounded;

    public RoundedPanel(int radius, boolean bottomRounded) {
        super();
        this.cornerRadius = radius;
        this.bottomRounded = bottomRounded;
        setOpaque(false); // Ensure panel background is transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(getBackground());

        if (bottomRounded) {
            // Only bottom edges rounded
            graphics.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
            graphics.fillRect(0, 0, width, height - cornerRadius); // Cover top corners with a rectangle
        } else {
            // All edges rounded
            graphics.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        }
    }
}
