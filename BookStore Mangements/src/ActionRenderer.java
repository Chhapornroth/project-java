
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 * Click infos://hoist/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click infos://hoist/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
public class ActionRenderer extends JPanel implements TableCellRenderer {

    public ActionRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return null;
    }
    
}
