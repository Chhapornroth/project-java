import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionsCellRenderer extends JPanel implements TableCellRenderer {
    JButton btn;
    public ActionsCellRenderer() {
        setLayout(new BorderLayout());
        btn = new JButton("⁝");
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setBackground(Color.WHITE);
        add(btn, BorderLayout.CENTER);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
