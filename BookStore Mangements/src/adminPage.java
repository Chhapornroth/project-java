
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/*
 * Click infos://hoist/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click infos://hoist/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
public final class adminPage extends JFrame implements ActionListener, ItemListener{
    private final JPanel centerPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private final JPanel topLeftPanel = new JPanel();
    private final JPanel bottomLeftPanel = new JPanel();
    private final JPanel inputBookInformationPanel = new JPanel(new GridBagLayout());
    private JTextField txtEmployeeId, txtEmployeeName, txtPhoneNumber;
    private JCheckBox MaleCheckBox, FemaleCheckBox; 
    private JTextField txtBookId, txtTitle, txtAuthor, txtStock;
    private JButton homeButton, BookRecordButton, EmployeeRecords, SaleRecords;
    private final List<Book> bookData = new ArrayList<>();
    private final List<Employee> employeeData = new ArrayList<>();
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    JTable bookRecordsTable;
    JTable employeeRecordsTable;
    
    public adminPage(){
        setTitle("Admin Page");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        leftPanel();
        add(leftPanel, BorderLayout.WEST);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);
        splitPane.setResizeWeight(0.12); // Set the initial split ratio to 12% for the left panel
        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void leftPanel() {
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        topLeftPanel(gbc);
        bottomLeftPanel(gbc);
    }
    
    public void topLeftPanel(GridBagConstraints gbc) {
        topLeftPanel.setLayout(new BorderLayout());
        topLeftPanel.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(20,0,0,0)));
        gbc.weightx = 1;
        gbc.weighty = 0.5/3.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(topLeftPanel, gbc);
        
        //add admin icon
        ImageIcon img = new ImageIcon("D:\\Java\\project-java\\icon\\Admin.png");
        Image image = img.getImage().getScaledInstance(100, 100,Image.SCALE_SMOOTH);
        ImageIcon iconImage = new ImageIcon(image);
        JLabel adminIcon = new JLabel(iconImage);
        topLeftPanel.add(adminIcon,BorderLayout.NORTH);
        
        JLabel textAdmin = new JLabel("WELCOME TO THE ADMIDPAGE");
        textAdmin.setFont(new Font("Arial",Font.BOLD,14));
        textAdmin.setHorizontalAlignment(SwingConstants.CENTER);
        topLeftPanel.add(textAdmin);
    }

    private void bottomLeftPanel(GridBagConstraints gbc) {
        bottomLeftPanel.setLayout(new GridBagLayout());
        bottomLeftPanel.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(12,12,10,12)));
        gbc.weighty = 2.5/3.0;
        gbc.gridy = 1;
        leftPanel.add(bottomLeftPanel, gbc);
        
        //create a label and drop it into bottomLeft panel
        bottomLeftPanel.setLayout(new BorderLayout());
        JLabel moreOption = new JLabel("More Options Below Here!");
        moreOption.setFont(new Font("Arial", Font.PLAIN, 15));
        moreOption.setHorizontalAlignment(SwingConstants.CENTER);
        moreOption.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(5,0,5,0)));
        bottomLeftPanel.add(moreOption,BorderLayout.NORTH);
        
        /*---------- Options Panel ----------*/
        JPanel option = new JPanel();
        option.setLayout(new GridBagLayout());
        option.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(0,0,0,0)));
        GridBagConstraints gbcInOptionPanel = new GridBagConstraints();
        bottomLeftPanel.add(option,BorderLayout.CENTER);
        
        //Button
        homeButton = buttonInit(option, gbcInOptionPanel, homeButton, "Home", 19, 0, 12);
        BookRecordButton = buttonInit(option, gbcInOptionPanel, BookRecordButton, "Book Records", 22, 1, 10);
        EmployeeRecords = buttonInit(option, gbcInOptionPanel, EmployeeRecords, "Employee Records", 25, 2, 7);
        SaleRecords = buttonInit(option, gbcInOptionPanel, SaleRecords, "Sale Records", 23, 3, 9);
        //this panel is here because we want to make a big gap or space below saleRecords Button
        gbcInOptionPanel.gridy = 4;
        gbcInOptionPanel.weighty = 1.0; 
        gbcInOptionPanel.fill = GridBagConstraints.BOTH;
        option.add(new JPanel(), gbcInOptionPanel);
        
        homeButton.addActionListener(this);
        BookRecordButton.addActionListener(this);
        EmployeeRecords.addActionListener(this);
        SaleRecords.addActionListener(this);
        /*---------- Options Panel ----------*/
    }
    private JButton buttonInit(JPanel panel, GridBagConstraints gbc, JButton button, String buttonName, int iconSize, int y, int gapBetweenIconAndButtonName){
        Image IconImage = new ImageIcon("D:\\Java\\project-java\\icon\\" + buttonName +  "-icon.png").getImage().getScaledInstance(iconSize, iconSize,Image.SCALE_SMOOTH);
        button = new JButton(buttonName, new ImageIcon(IconImage));
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setPreferredSize(new Dimension(0,38));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(gapBetweenIconAndButtonName);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(button,gbc);
        return button;
    }
    
    public void centerRightPanel(String namePanel, String nameTopPanel){
        centerPanel.setBorder(new CompoundBorder(new TitledBorder(namePanel),new EmptyBorder(0,10,9,10)));
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcCenterPanel = new GridBagConstraints();
            
        /*----- the top of the center panel -----*/
        gbcCenterPanel.weightx = 1;
        gbcCenterPanel.anchor = GridBagConstraints.WEST;
        gbcCenterPanel.fill = GridBagConstraints.BOTH;
        gbcCenterPanel.gridx = 0;
        gbcCenterPanel.gridy = 0;
        inputBookInformationPanel.setBorder(new CompoundBorder(new TitledBorder(nameTopPanel),new EmptyBorder(0,0,10,0)));
        centerPanel.add(inputBookInformationPanel,gbcCenterPanel);
        /*----- the top of the center panel -----*/
        
        /*----- the bottom of the center panel -----*/
        gbcCenterPanel.weighty = 5.0/6.0;
        gbcCenterPanel.gridy = 1;
        if(namePanel.equals("BOOK RECORDS")){
            String[] bookRecordsColumn = {"ID", "Title", "Author's Name", "Stock", "Adding Date", "Actions"};
            DefaultTableModel bookRecordsModel = new DefaultTableModel(bookRecordsColumn, 0);
            bookRecordsTable = new JTable(bookRecordsModel);

            bookRecordsTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(bookRecordsTable);
            centerPanel.add(scrollPane, gbcCenterPanel);
            bookRecordsTable.getColumnModel().getColumn(5).setPreferredWidth(50);
            bookRecordsTable.getColumnModel().getColumn(5).setMaxWidth(50);
            bookRecordsTable.getColumnModel().getColumn(5).setCellEditor(new ActionsEditor(bookRecordsTable));
        }else if(namePanel.equals("EMPLOYEE RECORDS")){
            String[] columnNames = {"ID", "Full Name", "Gender", "Phone Number", "Date of Birth"};
            DefaultTableModel employeeRecordsModel = new DefaultTableModel(columnNames, 0);
            employeeRecordsTable = new JTable(employeeRecordsModel);
            JScrollPane scrollPane = new JScrollPane(employeeRecordsTable);
            centerPanel.add(scrollPane, gbcCenterPanel);
        }
        /*----- the bottom of the center panel -----*/
    }
    
    private void clearPanel(){
        centerPanel.removeAll();
        inputBookInformationPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();
        inputBookInformationPanel.revalidate();
        inputBookInformationPanel.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "Home" ->{
                clearPanel();
                
                break;
            }
            case "Book Records" ->{
                clearPanel();
                centerRightPanel("BOOK RECORDS", "ADDING BOOK INFORMATION");
                GridBagConstraints gbc = new GridBagConstraints();
                
                gbc.insets = new Insets(0, 10, 10, 10);
                txtBookId = itemPosition(gbc, inputBookInformationPanel, "Book ID: ", 0, 0, txtBookId, 1, 0);
                txtAuthor = itemPosition(gbc, inputBookInformationPanel, "Author Name:", 2, 0, txtAuthor, 3, 0);
                
                gbc.insets = new Insets(10, 10, 0, 10);
                txtTitle = itemPosition(gbc, inputBookInformationPanel, "Book Title: ", 0, 1, txtTitle, 1, 1);
                txtStock = itemPosition(gbc, inputBookInformationPanel, "Stock: ", 2, 1, txtStock, 3, 1);
                
                JPanel datePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcDate = new GridBagConstraints();
                date(datePanel, gbcDate, "Adding Date:", 0, 0);
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 4;
                inputBookInformationPanel.add(datePanel, gbc);
                
                JButton submitButton = new JButton("Add");
                submitButton.setFont(new Font("",Font.BOLD,15));
                submitButton.setForeground(Color.blue);
                submitButton.setBackground(Color.blue);
                submitButton.setPreferredSize(new Dimension(80,40));
                gbc.gridx = 3;
                gbc.gridy = 3;
                gbc.gridwidth = 3;
                gbc.anchor = GridBagConstraints.SOUTHEAST;
                inputBookInformationPanel.add(submitButton, gbc);
                submitButton.addActionListener((ActionEvent e1) -> {
                    String id = txtBookId.getText();
                    String author = txtAuthor.getText();
                    String name = txtTitle.getText();
                    int Stock = 0; 
                    try {
                        Stock = Integer.parseInt(txtStock.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Stock must be a valid number");
                        return; 
                    }
                    
                    if (id.isEmpty() || author.isEmpty() || name.isEmpty() || Stock <= 0) {
                        JOptionPane.showMessageDialog(null,"Please fill in all fields");
                    } else {
                        String Date = getSelectedDate();
                        bookData.add(new Book(id, name, author, Stock, Date));
                        updateBookRecordTable();
                        txtBookId.setText("");
                        txtAuthor.setText("");
                        txtTitle.setText("");
                        txtStock.setText("");
                        dayComboBox.setSelectedIndex(0);
                        monthComboBox.setSelectedIndex(0);
                        yearComboBox.setSelectedIndex(0);
                    }
                });
                break;
            }
            case "Employee Records" ->{              
                clearPanel();
                centerRightPanel("EMPLOYEE RECORDS", "ADDING EMPLOYEE INFORMATION");
                GridBagConstraints gbc = new GridBagConstraints();
                
                gbc.insets = new Insets(0, 10, 10, 10);
                txtEmployeeId = itemPosition(gbc, inputBookInformationPanel, "Employee ID:", 0, 0, txtEmployeeId, 1, 0);
                gbc.gridx = 2;
                JLabel checkLabel = new JLabel("Gender: ");
                checkLabel.setFont(new Font("", Font.PLAIN, 14));
                inputBookInformationPanel.add(checkLabel, gbc);
                gbc.gridx = 3;
                JPanel checkPanel = new JPanel(new FlowLayout());
                inputBookInformationPanel.add(checkPanel, gbc);
                MaleCheckBox = itemPosition(checkPanel, "Male", MaleCheckBox);
                MaleCheckBox.addItemListener(this);
                FemaleCheckBox = itemPosition(checkPanel, "Female", FemaleCheckBox);
                FemaleCheckBox.addItemListener(this);
                
                gbc.insets = new Insets(10, 10, 0, 10);
                txtEmployeeName = itemPosition(gbc, inputBookInformationPanel, "Full Name:", 0, 1, txtEmployeeName, 1, 1);
                txtPhoneNumber = itemPosition(gbc, inputBookInformationPanel, "Telephone:", 2, 1, txtPhoneNumber, 3, 1);
                
                JPanel datePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcDate = new GridBagConstraints();
                date(datePanel, gbcDate, "Date of Birth:", 0, 0);
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 4;
                inputBookInformationPanel.add(datePanel, gbc);
                
                JButton submitButton = new JButton("Add");
                submitButton.setFont(new Font("",Font.BOLD,15));
                submitButton.setForeground(Color.blue);
                submitButton.setBackground(Color.blue);
                submitButton.setPreferredSize(new Dimension(80,40));
                gbc.gridx = 3;
                gbc.gridy = 3;
                gbc.gridwidth = 3;
                gbc.anchor = GridBagConstraints.SOUTHEAST;
                inputBookInformationPanel.add(submitButton, gbc);
                submitButton.addActionListener((ActionEvent e1) -> {
                    String id = txtEmployeeId.getText();
                    String name = txtEmployeeName.getText();
                    String gender = "";
                    if(MaleCheckBox.isSelected()){
                        gender = "Male";
                    }else if(FemaleCheckBox.isSelected()){
                        gender = "Female";
                    }
                    String phoneNumber = txtPhoneNumber.getText();
                    if (id.isEmpty()|| gender.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"Please fill in all fields");
                    } else {
                        String dateOfBirth = getSelectedDate();
                        employeeData.add(new Employee(id, name, gender, phoneNumber, dateOfBirth));
                        updateEmployeeRecordTable();
                        txtEmployeeId.setText("");
                        MaleCheckBox.setSelected(false);
                        FemaleCheckBox.setSelected(false);
                        txtEmployeeName.setText("");
                        txtPhoneNumber.setText("");
                        dayComboBox.setSelectedIndex(0);
                        monthComboBox.setSelectedIndex(0);
                        yearComboBox.setSelectedIndex(0);
                    }
                });
                break;
            }
            case "Sale Records" ->{
                break;
            }
            
        }
    }
    private JTextField itemPosition(GridBagConstraints gbc, JPanel panel, String name, int labelX, int labelY , JTextField textField, int textFieldX, int textFieldY){
        JLabel label = new JLabel(name);
        textField = new JTextField();
        label.setFont(new Font("", Font.PLAIN, 14));
        gbc.gridx = labelX;
        gbc.gridy = labelY;
        panel.add(label, gbc);
        gbc.gridx = textFieldX;     
        gbc.gridy = textFieldY;
        textField.setPreferredSize(new Dimension(250, 30));
        panel.add(textField, gbc);
        return textField;
    }
    private JCheckBox itemPosition(JPanel panel, String name, JCheckBox checkBox){
        checkBox = new JCheckBox(name);
        checkBox.setFont(new Font("", Font.PLAIN, 14));
        panel.add(checkBox);
        return checkBox;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == MaleCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
                FemaleCheckBox.setSelected(false);
            } else if (e.getSource() == FemaleCheckBox && e.getStateChange() == ItemEvent.SELECTED) {
                MaleCheckBox.setSelected(false);
            }
    }

    public void date(JPanel panel, GridBagConstraints gbc, String labelName, int x, int y) {
        String[] days = IntStream.rangeClosed(0, 31).mapToObj(Integer::toString).toArray(String[]::new);
        days[0] = "Select";
        String[] months = {"Select", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        int sy = 0, fy = 0;
        if (labelName.equals("Adding Date:")){
            sy = 2023;
            fy = 2030;
        } else if(labelName.equals("Date of Birth:")){
            sy = 1974;
            fy = 2024;
        }
        String[] years = IntStream.rangeClosed(sy, fy).mapToObj(Integer::toString).toArray(String[]::new);
        years[0] = "Select";
        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);
        
        JLabel Name = new JLabel(labelName);
        gbc.insets = new Insets(10, 5, 10, 10);
        Name.setFont(new Font("", Font.PLAIN, 14));
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(Name, gbc);
        
        addDateComponent(panel, gbc, " Day: ", dayComboBox, x + 1);
        addDateComponent(panel, gbc, " Month: ", monthComboBox, x + 3);
        addDateComponent(panel, gbc, " Year: ", yearComboBox, x + 5);
    }

    private void addDateComponent(JPanel panel, GridBagConstraints gbc, String labelText, JComboBox<String> comboBox, int gridx) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = gridx;
        panel.add(label, gbc);
        gbc.gridx = gridx + 1;
        panel.add(comboBox, gbc);
    }

    private String getSelectedDate() {
    String day = (String) dayComboBox.getSelectedItem();
    String month = (String) monthComboBox.getSelectedItem();
    String year = (String) yearComboBox.getSelectedItem();
    return day + "/" + month + "/" + year;
}
    private void updateBookRecordTable() {
        DefaultTableModel model = (DefaultTableModel) bookRecordsTable.getModel();
        model.setRowCount(0); // Clear the table
        for (Book book : bookData) {
            model.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getStock(), book.getDate()});
        }
    } 
    private void updateEmployeeRecordTable() {
        DefaultTableModel model = (DefaultTableModel) employeeRecordsTable.getModel();
        model.setRowCount(0); // Clear the table
        for (Employee employee : employeeData) {
            model.addRow(new Object[]{employee.getId(), employee.getName(), employee.getGender(), employee.getPhoneNumber(), employee.getDateOfBirth()});
        }
    }
}