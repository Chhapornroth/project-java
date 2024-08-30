import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionsCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private static final Logger LOGGER = Logger.getLogger(ActionsCellEditor.class.getName());
    private final JPanel panel;
    private final JButton button;
    private final JPopupMenu popupMenu;
    private final JMenuItem editItem, deleteItem, viewProfile;
    private final JMenu sortItem;
    private JMenuItem  ascendingItem, descendingItem;
    private int valueOfPrimaryKey;
    private JTable table;
    private int row;
    private final AdminPage adminPageInstance;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";
    Connection conn;
    Statement stmt;
    String namePanel;
    public ActionsCellEditor(JTable table, AdminPage adminPageInstance, String namePanel) {
        panel = new JPanel(new BorderLayout());
        button = new JButton("⁝");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        panel.add(button, BorderLayout.CENTER);

        popupMenu = new JPopupMenu();
        viewProfile = new JMenuItem("View Profile");
        if(namePanel.equals("EMPLOYEE RECORDS")){
            popupMenu.add(viewProfile);
        }
        editItem = new JMenuItem("Edit");
        if(!namePanel.equals("SALES TRANSACTIONS")){
            popupMenu.add(editItem);
        }
        sortItem = new JMenu("Sort");
        popupMenu.add(sortItem);
        ascendingItem = new JMenuItem("(Name or Title) Ascending");
        sortItem.add(ascendingItem);
        descendingItem = new JMenuItem("(Name or Title) Descending");
        sortItem.add(descendingItem);
        ascendingItem.addActionListener(this);
        descendingItem.addActionListener(this);

        deleteItem = new JMenuItem("Delete");
        popupMenu.add(deleteItem);

        button.addActionListener(this);
        editItem.addActionListener(this);
        deleteItem.addActionListener(this);
        viewProfile.addActionListener(this);

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
        this.namePanel = namePanel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        valueOfPrimaryKey = (int) table.getValueAt(row, 0);
        this.table = table;
        this.row = row;
        if (isSelected) {
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setBackground(table.getBackground());
        }
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = "";
        if (e.getSource() == button) {
            popupMenu.show(button, button.getWidth(), button.getHeight());
        } else if (e.getSource() == editItem) {
            new EditWindow(adminPageInstance, table, row, namePanel);
        }else if (e.getSource() == ascendingItem) {
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(rowSorter);
            rowSorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
        }else if(e.getSource() == descendingItem){
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(rowSorter);
            rowSorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
        }else if (e.getSource() == deleteItem) {
            query = switch (namePanel) {
                case "BOOK RECORDS" -> "DELETE FROM `tbl_book_records` WHERE book_id = " + valueOfPrimaryKey;
                case "EMPLOYEE RECORDS" ->
                        "DELETE FROM `tbl_employee_records` WHERE employee_id = " + valueOfPrimaryKey;
                case "SALES TRANSACTIONS" ->
                        "DELETE FROM `tbl_transactions_records` WHERE txn_id = " + valueOfPrimaryKey;
                default -> query;
            };
            try {
                conn = DriverManager.getConnection(url, user, password);
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                switch (namePanel) {
                    case "BOOK RECORDS" -> adminPageInstance.updateBookRecordTable();
                    case "EMPLOYEE RECORDS" -> adminPageInstance.updateEmployeeRecordTable();
                    case "SALES TRANSACTIONS" -> adminPageInstance.updateSaleRecordTable();
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "An error occurred", ex);
            }finally{
                try {
                    if(stmt != null) stmt.close();
                    if(conn != null) conn.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "An error occurred", ex);
                }
            }
            System.out.println("Deleting is completed successfully!");
            JOptionPane.showMessageDialog(panel, "The Book with ID = " + valueOfPrimaryKey + " has been removed!");
        } else if (e.getSource() == viewProfile) {
            
        }
        fireEditingStopped();
    }
}
