import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditWindow extends JFrame implements ActionListener, ItemListener {
    private static final Logger LOGGER = Logger.getLogger(EditWindow.class.getName());
    private final AdminPage adminPageInstance;
    private JTextField jTextField1, jTextField2, jTextField3, jTextField4;
    private final int row;
    private final JTable table;
    private final String namePanel;
    private JCheckBox MaleCheckBox, FemaleCheckBox;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Management";
    String user = "root";
    String password = "";
    Connection conn;
    PreparedStatement stmt;

    public EditWindow(AdminPage adminPageInstance, JTable table, int row, String namePanel){
        super("Manipulating Data");
        setSize(700, 300);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.adminPageInstance = adminPageInstance;
        this.row = row;
        this.table = table;
        this.namePanel = namePanel;
        //all components
        panelComponents(adminPageInstance);
        setData();
    }
    private void panelComponents(AdminPage adminPageInstance){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        TitledBorder titledBorder = new TitledBorder("Edit Data");
        titledBorder.setTitleColor(Color.BLUE);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        panel.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 10, 10), new TitledBorder(titledBorder)));
        add(panel, BorderLayout.CENTER);
        gbc.insets  = new Insets(0, 10, 10, 10);
        int w  = 200;

        String jLabel1 = "", jLabel3 = "", jLabel4 = "", jLabel5 ="";
        if(namePanel.equals("BOOK RECORDS")){
            jLabel1 = "Book ID:";
            jLabel3 = "Author Name:";
            jLabel4 = "Stock:";
            jLabel5 = "Adding date";
            jTextField2 = adminPageInstance.itemPosition(gbc, panel, "Book Title:", 2, 0, 3, 0, w);
        }else if(namePanel.equals("EMPLOYEE RECORDS")){
            jLabel1 = "Employee ID:";
            gbc.gridx = 2;
            JLabel checkLabel = new JLabel("Gender:");
            checkLabel.setFont(new Font("", Font.PLAIN, 14));
            panel.add(checkLabel, gbc);
            gbc.gridx = 3;
            JPanel checkPanel = new JPanel(new FlowLayout());
            panel.add(checkPanel, gbc);
            MaleCheckBox = adminPageInstance.itemPosition(checkPanel, "Male");
            MaleCheckBox.addItemListener(this);
            FemaleCheckBox = adminPageInstance.itemPosition(checkPanel, "Female");
            FemaleCheckBox.addItemListener(this);
            jLabel3 = "Full Name:";
            jLabel4 = "Telephone:";
            jLabel5 = "Date of Birth:";
        }
        jTextField1 = adminPageInstance.itemPosition(gbc, panel, jLabel1, 0, 0, 1, 0, w);
        jTextField1.setEnabled(false);
        jTextField3 = adminPageInstance.itemPosition(gbc, panel, jLabel3, 0, 1, 1, 1, w);
        jTextField4 = adminPageInstance.itemPosition(gbc, panel, jLabel4, 2, 1, 3, 1, w);
        gbc.insets  = new Insets(0, 10, 10, 10);
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        adminPageInstance.date(panel1, gbc1, jLabel5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(panel1, gbc);
        JButton editButton = new JButton("OK");
        add(editButton, BorderLayout.SOUTH);
        editButton.addActionListener(this);
    }
    private void setData(){
        int columnIndex = 0;
        jTextField1.setText(String.valueOf((int)table.getModel().getValueAt(row, 0)));
        if(namePanel.equals("BOOK RECORDS")){
            jTextField2.setText((String)table.getModel().getValueAt(row, 1));
            columnIndex = 2;
            jTextField4.setText(String.valueOf((int)table.getModel().getValueAt(row, 3)));
        }else if(namePanel.equals("EMPLOYEE RECORDS")){
            if(table.getModel().getValueAt(row, 2).equals("Male")){
                MaleCheckBox.setSelected(true);
            }else{
                FemaleCheckBox.setSelected(true);
            }
            columnIndex = 1;
            jTextField4.setText(String.valueOf(table.getModel().getValueAt(row, 3)));
        }
        jTextField3.setText((String)table.getModel().getValueAt(row, columnIndex));
        LocalDate localDate = (LocalDate)table.getModel().getValueAt(row, 4);
        String day = String.valueOf(localDate.getDayOfMonth());
        int month = (localDate.getMonthValue());
        String year = String.valueOf(localDate.getYear());
        adminPageInstance.setSelectedDate(day, getMonth(month), year);
    }
    private static String getMonth(int monthInt) {
        String month = "";
        switch (monthInt) {
            case 1 -> month = "January";
            case 2 -> month = "February";
            case 3 -> month = "March";
            case 4 -> month = "April";
            case 5 -> month = "May";
            case 6 -> month = "June";
            case 7 -> month = "July";
            case 8 -> month = "August";
            case 9 -> month = "September";
            case 10 -> month = "October";
            case 11 -> month = "November";
            case 12 -> month = "December";
        }
        return month;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            conn = DriverManager.getConnection(url, user, password);
            if(namePanel.equals("BOOK RECORDS")){
                stmt = conn.prepareStatement("UPDATE `tbl_Book_Records` SET `title`= ?,`author_name`= ?,`stock`= ?,`adding_date`= ? WHERE book_id = ?");
                stmt.setString(1, jTextField2.getText());
                stmt.setString(2, jTextField3.getText());
            }else if(namePanel.equals("EMPLOYEE RECORDS")){
                String gender = "";
                if(MaleCheckBox.isSelected()){
                    gender = "Male";
                }else if(FemaleCheckBox.isSelected()) {
                    gender = "Female";
                }
                stmt = conn.prepareStatement("UPDATE `tbl_Employee_Records` SET `name`= ?,`gender`= ?,`phone_number`= ?,`birthday`= ? WHERE `employee_id` = ?");
                stmt.setString(1, jTextField3.getText());
                stmt.setString(2, gender);
            }
            stmt.setString(3, jTextField4.getText());
            stmt.setDate(4, Date.valueOf(Objects.requireNonNull(adminPageInstance.getSelectedDate())));
            stmt.setString(5, jTextField1.getText());
            stmt.executeUpdate();
            if(namePanel.equals("BOOK RECORDS")){
                adminPageInstance.updateBookRecordTable(table);
            }
            else if(namePanel.equals("EMPLOYEE RECORDS")){
                adminPageInstance.updateEmployeeRecordTable();
            }
            this.dispose();
        }catch (SQLException ex){
            LOGGER.log(Level.SEVERE, "An error occurred", ex);
        }finally{
            try{
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch (SQLException ex){
                LOGGER.log(Level.SEVERE, "An error occurred", ex);
            }
        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == MaleCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
            FemaleCheckBox.setSelected(false);
        } else if (e.getSource() == FemaleCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
            MaleCheckBox.setSelected(false);
        }
    }
}