import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ActionsCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private final JPanel panel;
    private final JButton button;
    private final JPopupMenu popupMenu;
    private final JMenuItem editItem;
    private final JMenuItem deleteItem;
    private int valueOfPrimaryKey;
    private JTable table;
    private final AdminPage adminPageInstance;
    String url = "jdbc:mariadb://localhost:3306/Bookstore Managements";
    String user = "root";
    String password = "";
    Connection conn;
    Statement stmt;

    public ActionsCellEditor(JTable table, AdminPage adminPageInstance) {
        panel = new JPanel(new BorderLayout());
        button = new JButton("⁝");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        panel.add(button, BorderLayout.CENTER);

        popupMenu = new JPopupMenu();
        editItem = new JMenuItem("Edit");
        deleteItem = new JMenuItem("Delete");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        button.addActionListener(this);
        editItem.addActionListener(this);
        deleteItem.addActionListener(this);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                }
            }
        });
        this.adminPageInstance = adminPageInstance;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        valueOfPrimaryKey = (int) table.getValueAt(row, 0);
        this.table = table;
        if (isSelected) {
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setBackground(table.getBackground());
        }
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            popupMenu.show(button, button.getWidth(), button.getHeight());
        } else if (e.getSource() == editItem) {
            new EditWindow(adminPageInstance);
        } else if (e.getSource() == deleteItem) {
            try {
                conn = DriverManager.getConnection(url, user, password);
                String query = "DELETE FROM `tbl_book_records` WHERE book_id = " + valueOfPrimaryKey;
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                adminPageInstance.updateBookRecordTable(table);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }finally{
                try {
                    if(stmt != null){
                        stmt.close();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if(conn != null){
                        conn.close();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.out.println("Deleting is completed successfully!");
            JOptionPane.showMessageDialog(panel, "The Book with ID = " + valueOfPrimaryKey + " has been removed!");
        }
        fireEditingStopped();
    }
}
