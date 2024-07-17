
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
/*
 * Click infos://hoist/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click infos://hoist/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
public class ActionsEditor extends AbstractCellEditor implements TableCellEditor{
    private final JPanel panel;

    public ActionsEditor(JTable table) {
        panel = new JPanel(new BorderLayout());
        String[] model = {"...", "Edit", "Delete"};
        JComboBox<String> comboBox = new JComboBox<>(model);
        panel.add(comboBox, BorderLayout.CENTER);
        
        if (Objects.equals(comboBox.getSelectedItem(), "Edit")) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to remove.");
            }
        } else if (Objects.equals(comboBox.getSelectedItem(), "Delete")) {

            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getEditingRow());
            ((DefaultTableModel) table.getModel()).removeRow(row);
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
    
}