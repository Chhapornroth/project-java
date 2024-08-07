
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AdminPage extends JFrame implements ActionListener, ItemListener, FocusListener{
    private static final Logger LOGGER = Logger.getLogger(AdminPage.class.getName());
    private final JPanel centerPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private final JPanel topLeftPanel = new JPanel();
    private final JPanel bottomLeftPanel = new JPanel();
    private final JPanel inputBookInformationPanel = new JPanel(new GridBagLayout());
    private JButton bookRecordButton, employeeRecords, saleRecords;
    private JTextField txtEmployeeId, txtEmployeeName, txtPhoneNumber;
    private JCheckBox MaleCheckBox, FemaleCheckBox;
    private JTextField txtBookId, txtTitle, txtAuthor, txtStock, searchTextField;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    JTable bookRecordsTable;
    JTable employeeRecordsTable;
    JTable saleRecordsTable;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Management";
    String user = "root";
    String password = "";
    Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;

    public AdminPage(){
        setTitle("Admin Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1250, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        leftPanel();
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
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
        topLeftPanel.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(20,40,0,40)));
        gbc.weightx = 1;
        gbc.weighty = 0.4/3.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(topLeftPanel, gbc);

        //add admin icon
        ImageIcon img = new ImageIcon("D:\\Java\\project-java\\icon\\Admin-1.png");
        Image image = img.getImage().getScaledInstance(200, 200,Image.SCALE_SMOOTH);
        ImageIcon iconImage = new ImageIcon(image);
        JLabel adminIcon = new JLabel(iconImage);
        topLeftPanel.add(adminIcon,BorderLayout.NORTH);

        JLabel textAdmin = new JLabel("WELCOME TO THE ADMIN PAGE");
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
        JButton homeButton = buttonInit(option, gbcInOptionPanel, "Home", 19, 0, 12);
        bookRecordButton = buttonInit(option, gbcInOptionPanel, "Book Records", 22, 1, 10);
        employeeRecords = buttonInit(option, gbcInOptionPanel, "Employee Records", 25, 2, 7);
        saleRecords = buttonInit(option, gbcInOptionPanel, "Sale Records", 23, 3, 9);

        SwingUtilities.invokeLater(homeButton::doClick);

        //this panel is here because we want to make a big gap or space below saleRecords Button
        gbcInOptionPanel.gridy = 4;
        gbcInOptionPanel.weighty = 1.0;
        gbcInOptionPanel.fill = GridBagConstraints.BOTH;
        option.add(new JPanel(), gbcInOptionPanel);

        homeButton.addActionListener(this);
        bookRecordButton.addActionListener(this);
        employeeRecords.addActionListener(this);
        saleRecords.addActionListener(this);
        /*---------- Options Panel ----------*/
    }
    private JButton buttonInit(JPanel panel, GridBagConstraints gbc, String buttonName, int iconSize, int y, int gapBetweenIconAndButtonName){
        Image IconImage = new ImageIcon("D:\\Java\\project-java\\icon\\" + buttonName +  "-icon.png").getImage().getScaledInstance(iconSize, iconSize,Image.SCALE_SMOOTH);
        JButton button;
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

        /*----- the center of the center panel -----*/
        gbcCenterPanel.gridy = 1;
        gbcCenterPanel.insets = new Insets(5, 0, 5 , 0);
        searchTextField = new JTextField("Search");
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 13));
        centerPanel.add(searchTextField, gbcCenterPanel);
        searchTextField.addFocusListener(this);
        /*----- the center of the center panel -----*/

        /*----- the bottom of the center panel -----*/
        gbcCenterPanel.weighty = 4.0/6.0;
        gbcCenterPanel.gridy = 2;
        if(namePanel.equals("BOOK RECORDS")){
            String[] bookRecordsColumn = {"ID", "Title", "Author's Name", "Stock", "Adding Date", "Actions"};
            DefaultTableModel bookRecordsModel = new DefaultTableModel(bookRecordsColumn, 0);
            bookRecordsTable = new JTable(bookRecordsModel);
            bookRecordsTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(bookRecordsTable);
            centerPanel.add(scrollPane, gbcCenterPanel);
            bookRecordsTable.setRowHeight(20);
            bookRecordsTable.getColumnModel().getColumn(5).setMaxWidth(50);
            bookRecordsTable.getColumnModel().getColumn(5).setCellRenderer(new ActionsCellRenderer());
            bookRecordsTable.getColumnModel().getColumn(5).setCellEditor(new ActionsCellEditor(bookRecordsTable, this, "BOOK RECORDS"));
            updateBookRecordTable(bookRecordsTable);
        }else if(namePanel.equals("EMPLOYEE RECORDS")){
            String[] columnNames = {"ID", "Full Name", "Gender", "Phone Number", "Date of Birth", "Actions"};
            DefaultTableModel employeeRecordsModel = new DefaultTableModel(columnNames, 0);
            employeeRecordsTable = new JTable(employeeRecordsModel);
            employeeRecordsTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(employeeRecordsTable);
            centerPanel.add(scrollPane, gbcCenterPanel);
            employeeRecordsTable.getColumnModel().getColumn(5).setMaxWidth(50);
            employeeRecordsTable.getColumnModel().getColumn(5).setCellRenderer(new ActionsCellRenderer());
            employeeRecordsTable.getColumnModel().getColumn(5).setCellEditor(new ActionsCellEditor(employeeRecordsTable, this, "EMPLOYEE RECORDS"));
            updateEmployeeRecordTable();
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
                centerPanel.setBorder(new CompoundBorder(new TitledBorder("HOME DASHBOARD"),new EmptyBorder(0,8,0,8)));
                centerPanel.setLayout(new BorderLayout());

                JPanel topHomePanel = new JPanel(new GridLayout());
                GridLayout gl = new GridLayout();
                topHomePanel.setLayout(gl);
                gl.setHgap(10);
                gl.setVgap(10);
                RoundedPanel rp1 = homePanelInformation(topHomePanel, 50, 194, 214, "book records", "Book Stock");
                RoundedPanel rp2 = homePanelInformation(topHomePanel, 181, 211, 145, "guy", "Employees");
                RoundedPanel rp3 = homePanelInformation(topHomePanel, 251, 221, 92, "Customer", "Transactions");
                MouseListener mouseListener = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getSource() == rp1){
                            SwingUtilities.invokeLater(bookRecordButton::doClick);
                        }else if(e.getSource() == rp2){
                            SwingUtilities.invokeLater(employeeRecords::doClick);
                        }else if(e.getSource() == rp3){
                            SwingUtilities.invokeLater(saleRecords::doClick);
                        }
                    }
                };
                rp1.addMouseListener(mouseListener);
                rp1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                rp2.addMouseListener(mouseListener);
                rp2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                rp3.addMouseListener(mouseListener);
                rp3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                centerPanel.add(topHomePanel, BorderLayout.NORTH);
                JPanel bottomHomePanel = new JPanel();
                centerPanel.add(bottomHomePanel, BorderLayout.SOUTH);
            }
            case "Book Records" ->{
                clearPanel();
                centerRightPanel("BOOK RECORDS", "ADDING BOOK INFORMATION");
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.insets = new Insets(0, 10, 10, 10);
                txtBookId = itemPosition(gbc, inputBookInformationPanel, "Book ID: ", 0, 0, 1, 0, 250);
                txtAuthor = itemPosition(gbc, inputBookInformationPanel, "Author Name:", 2, 0, 3, 0, 250);

                gbc.insets = new Insets(10, 10, 0, 10);
                txtTitle = itemPosition(gbc, inputBookInformationPanel, "Book Title: ", 0, 1, 1, 1, 250);
                txtStock = itemPosition(gbc, inputBookInformationPanel, "Stock: ", 2, 1, 3, 1, 250);

                JPanel datePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcDate = new GridBagConstraints();
                date(datePanel, gbcDate, "Adding Date:");
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
                    String author = txtAuthor.getText();
                    String name = txtTitle.getText();
                    int Stock;
                    try {
                        Stock = Integer.parseInt(txtStock.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Stock must be a valid number");
                        return;
                    }
                    Date date = Date.valueOf(Objects.requireNonNull(getSelectedDate()));
                    if (author.isEmpty() || name.isEmpty() || Stock <= 0) {
                        JOptionPane.showMessageDialog(null,"Please fill in all fields");
                    } else {
                        try {
                            connection = DriverManager.getConnection(url, user, password);
                            preparedStatement = connection.prepareStatement("INSERT INTO tbl_Book_Records(title, author_name, stock, adding_date) VALUES (?,?,?,?)");
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, author);
                            preparedStatement.setInt(3, Stock);
                            preparedStatement.setDate(4, date);
                            preparedStatement.executeUpdate();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }finally {
                            try {
                                if (preparedStatement != null) preparedStatement.close();
                                if (connection != null) connection.close();
                            } catch (SQLException e2) {
                                LOGGER.log(Level.SEVERE, "An error occurred", e2);
                            }
                        }
                        updateBookRecordTable(bookRecordsTable);
                        txtBookId.setText("");
                        txtAuthor.setText("");
                        txtTitle.setText("");
                        txtStock.setText("");
                        setSelectedDate("Select", "Select", "Select");
                    }
                });
            }
            case "Employee Records" ->{
                clearPanel();
                centerRightPanel("EMPLOYEE RECORDS", "ADDING EMPLOYEE INFORMATION");
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.insets = new Insets(0, 10, 10, 10);
                txtEmployeeId = itemPosition(gbc, inputBookInformationPanel, "Employee ID:", 0, 0, 1, 0, 250);
                gbc.gridx = 2;
                JLabel checkLabel = new JLabel("Gender: ");
                checkLabel.setFont(new Font("", Font.PLAIN, 14));
                inputBookInformationPanel.add(checkLabel, gbc);
                gbc.gridx = 3;
                JPanel checkPanel = new JPanel(new FlowLayout());
                inputBookInformationPanel.add(checkPanel, gbc);
                MaleCheckBox = itemPosition(checkPanel, "Male");
                MaleCheckBox.addItemListener(this);
                FemaleCheckBox = itemPosition(checkPanel, "Female");
                FemaleCheckBox.addItemListener(this);

                gbc.insets = new Insets(10, 10, 0, 10);
                txtEmployeeName = itemPosition(gbc, inputBookInformationPanel, "Full Name:", 0, 1, 1, 1, 250);
                txtPhoneNumber = itemPosition(gbc, inputBookInformationPanel, "Telephone:", 2, 1, 3, 1, 250);

                JPanel datePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcDate = new GridBagConstraints();
                date(datePanel, gbcDate, "Date of Birth:");
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
                    String name = txtEmployeeName.getText();
                    String gender = "";
                    if(MaleCheckBox.isSelected()){
                        gender = "Male";
                    }else if(FemaleCheckBox.isSelected()){
                        gender = "Female";
                    }
                    Date birthday = Date.valueOf(Objects.requireNonNull(getSelectedDate()));
                    String phone_number = txtPhoneNumber.getText();
                    if (gender.isEmpty() || name.isEmpty() || phone_number.isEmpty()) {
                        JOptionPane.showMessageDialog(null,"Please fill in all fields");
                    } else {
                        try{
                            connection = DriverManager.getConnection(url, user, password);
                            preparedStatement = connection.prepareStatement("INSERT INTO tbl_Employee_Records(name, gender, phone_number, birthday) VALUES (?, ?, ?, ?)");
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, gender);
                            preparedStatement.setString(3, phone_number);
                            preparedStatement.setDate(4, birthday);
                            preparedStatement.executeUpdate();
                        }catch (SQLException ex){
                            throw new RuntimeException(ex);
                        }finally {
                            try {
                                if (preparedStatement != null) preparedStatement.close();
                                if (connection != null) connection.close();
                            } catch (SQLException e2) {
                                LOGGER.log(Level.SEVERE, "An error occurred", e2);
                            }
                        }
                        updateEmployeeRecordTable();
                        txtEmployeeId.setText("");
                        MaleCheckBox.setSelected(false);
                        FemaleCheckBox.setSelected(false);
                        txtEmployeeName.setText("");
                        txtPhoneNumber.setText("");
                        setSelectedDate("Select", "Select", "Select");
                    }
                });
            }
            case "Sale Records" ->{
                clearPanel();
                centerPanel.setBorder(new CompoundBorder(new TitledBorder("TRANSACTIONS RECORDS"),new EmptyBorder(0,8,0,8)));
            }
        }
    }
    private RoundedPanel homePanelInformation(JPanel panel, int R, int G, int B, String imgName, String labelName1){
        RoundedPanel informationPanel = new RoundedPanel(20);
        informationPanel.setBackground(new Color(R, G, B));
        informationPanel.setPreferredSize(new Dimension(0, 240));
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        ImageIcon img = new ImageIcon("D:\\Java\\project-java\\icon\\"+ imgName +"-icon.png");
        Image image = img.getImage().getScaledInstance(175, 175,Image.SCALE_SMOOTH);
        ImageIcon iconImage = new ImageIcon(image);
        JLabel adminIcon = new JLabel(iconImage);
        informationPanel.add(adminIcon, BorderLayout.EAST);

        JPanel leftOfLeftHomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        leftOfLeftHomePanel.setOpaque(false);

        JLabel informationLabelStr = new JLabel("Total");
        informationLabelStr.setFont(new Font("Arial", Font.BOLD, 30));
        informationLabelStr.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        leftOfLeftHomePanel.add(informationLabelStr, gbc);

        JLabel informationLabelStr1 = new JLabel(labelName1);
        informationLabelStr1.setFont(new Font("Arial", Font.BOLD, 27));
        informationLabelStr1.setForeground(Color.WHITE);
        gbc.gridy = 1;
        leftOfLeftHomePanel.add(informationLabelStr1, gbc);

        String amount = "0";
        try{
            connection = DriverManager.getConnection(url, user, password);
            String sql = switch (labelName1) {
                case "Book Stock" -> "SELECT SUM(stock) AS Col_Name FROM tbl_Book_Records";
                case "Employees" -> "SELECT COUNT(name) AS Col_Name FROM tbl_Employee_Records";
                case "Transactions" -> "SELECT COUNT(*) AS Col_Name FROM tbl_Transactions_Records";
                default -> "";
            };
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int amountInt = resultSet.getInt("Col_Name");
                amount = String.valueOf(amountInt);
            }
        }catch(SQLException e){
            LOGGER.log(Level.SEVERE, "An error occurred", e);
        }finally {
            try{
                if (connection != null) connection.close();
            }catch(SQLException e){
                LOGGER.log(Level.SEVERE, "An error occurred", e);
            }
        }

        JLabel informationLabelInt = new JLabel(amount);
        informationLabelInt.setFont(new Font("Arial", Font.BOLD, 40));
        informationLabelInt.setForeground(Color.WHITE);
        gbc.gridy = 2;
        leftOfLeftHomePanel.add(informationLabelInt, gbc);

        informationPanel.add(leftOfLeftHomePanel, BorderLayout.CENTER);
        panel.add(informationPanel);

        return informationPanel;
    }
    public JTextField itemPosition(GridBagConstraints gbc, JPanel panel, String name, int labelX, int labelY , int textFieldX, int textFieldY, int width){
        JLabel label = new JLabel(name);
        JTextField textField;
        textField = new JTextField();
        label.setFont(new Font("", Font.PLAIN, 14));
        gbc.gridx = labelX;
        gbc.gridy = labelY;
        panel.add(label, gbc);
        gbc.gridx = textFieldX;
        gbc.gridy = textFieldY;
        textField.setPreferredSize(new Dimension(width, 30));
        panel.add(textField, gbc);
        return textField;
    }
    public JCheckBox itemPosition(JPanel panel, String name){
        JCheckBox checkBox = new JCheckBox(name);
        checkBox.setFont(new Font("", Font.PLAIN, 14));
        checkBox.setBorder(new EmptyBorder(0,0,0,20));
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
    public void date(JPanel panel, GridBagConstraints gbc, String labelName) {
        String[] days = IntStream.rangeClosed(0, 31).mapToObj(Integer::toString).toArray(String[]::new);
        days[0] = "Select";
        String[] months = {"Select", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        String[] years = IntStream.rangeClosed(1989, 2030).mapToObj(Integer::toString).toArray(String[]::new);
        years[0] = "Select";
        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);

        JLabel Name = new JLabel(labelName);
        gbc.insets = new Insets(10, 5, 10, 10);
        Name.setFont(new Font("", Font.PLAIN, 14));
        int x = 0, y = 0;
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
    public LocalDate getSelectedDate() {
        String dayStr = (String) dayComboBox.getSelectedItem();
        String monthStr = (String) monthComboBox.getSelectedItem();
        String yearStr = (String) yearComboBox.getSelectedItem();

        if ("Select".equals(dayStr) || "Select".equals(monthStr) || "Select".equals(yearStr)) {
            JOptionPane.showMessageDialog(null, "Please choose the right format!");
            return null;
        }else {
            assert dayStr != null;
            int day = Integer.parseInt(dayStr);
            assert monthStr != null;
            int month = getMonth(monthStr);
            assert yearStr != null;
            int year = Integer.parseInt(yearStr);
            return LocalDate.of(year, month, day);
        }
    }

    private static int getMonth(String monthStr) {
        int month = 0;
        switch (monthStr) {
            case "January" -> month = 1;
            case "February" -> month = 2;
            case "March" -> month = 3;
            case "April" -> month = 4;
            case "May" -> month = 5;
            case "June" -> month = 6;
            case "July" -> month = 7;
            case "August" -> month = 8;
            case "September" -> month = 9;
            case "October" -> month = 10;
            case "November" -> month = 11;
            case "December" -> month = 12;
        }
        return month;
    }
    public void setSelectedDate(String d, String m, String y){
        dayComboBox.setSelectedItem(d);
        monthComboBox.setSelectedItem(m);
        yearComboBox.setSelectedItem(y);
    }
    public void updateBookRecordTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tbl_Book_Records");
            while (resultSet.next()){
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author_name");
                int stock = resultSet.getInt("stock");
                LocalDate addingDate = resultSet.getDate("adding_date").toLocalDate();
                model.addRow(new Object[]{id, title, author, stock, addingDate});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "An error occurred", ex);
            }
        }
    }
    public void updateEmployeeRecordTable() {
        DefaultTableModel model = (DefaultTableModel) employeeRecordsTable.getModel();
        model.setRowCount(0); // Clear the table
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tbl_Employee_Records");
            while (resultSet.next()){
                int id = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String phone_number = resultSet.getString("phone_number");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                model.addRow(new Object[]{id, name, gender, phone_number, birthday});
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "An error occurred", ex);
            }
        }
    }
    public void updateSaleRecordTable() {
        DefaultTableModel model = (DefaultTableModel) saleRecordsTable.getModel();
        model.setRowCount(0); // Clear the table
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tbl_Transactions_Records");
            while (resultSet.next()){
                int txn_id = resultSet.getInt("txn_id");
                String cust_name = resultSet.getString("cust_name");
                int book_id = resultSet.getInt("book_id");
                String employee_id = resultSet.getString("employee_id");
                LocalDate birthday = resultSet.getDate("price").toLocalDate();
                model.addRow(new Object[]{txn_id, cust_name, book_id, employee_id, birthday});
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "An error occurred", ex);
            }
        }
    }
    @Override
    public void focusGained(FocusEvent e) {
        if (searchTextField.getText().equals("Search")){
            searchTextField.setText("");
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if (searchTextField.getText().isEmpty()){
            searchTextField.setText("Search");
        }
    }
}