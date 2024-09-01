import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class CashRegister extends JFrame implements MouseListener, ItemListener, ActionListener{
    private final JPanel billSectionPanel = new JPanel(new BorderLayout());
    private final JPanel registerSectionPanel = new JPanel(new BorderLayout());
    private JTable billTable, bookTable;
    private RoundedPanel homeButton, profileButton, bookInfoButton, billButton, logOutButton;
    private JButton addButton, clearButton;
    private RoundedPanel highLightHomeButton, highLightProfileButton, highLightBookButton, highLightBillButton, highLightLogOutButton;
    private JTextField customerTextField, priceTextField, qtyTextField, searchField;
    private JComboBox<String> bookIDComboBox;
    private JCheckBox _5_Percents, _10_Percents, _20_Percents;
    private JPanel inputBookInfoPanel;
    private JLabel billLabel2;
    int id;
    String name;
    float disc = 0;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    float amount = 0;

    public CashRegister(int id, String name){
        super("Cash Register");
        this.id = id;
        this.name = name;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250,800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        billSection();
        registerSection();

        setVisible(true);
    }
    private void billSection(){
        billSectionPanel.setBackground(Color.WHITE);
        billSectionPanel.setPreferredSize(new Dimension(350,0));
        billSectionPanel.setBorder(new EmptyBorder(0, 5, 0,0));

        JLabel billLabel = new JLabel("All Items");
        billLabel.setHorizontalAlignment(SwingConstants.CENTER);
        billLabel.setPreferredSize(new Dimension(0,50));
        billLabel.setFont(new Font("Arial", Font.BOLD, 20));
        billSectionPanel.add(billLabel, BorderLayout.NORTH);

        String[] billTableHeaders = {"Item Name", "Price"};
        DefaultTableModel billTableModel = new DefaultTableModel(billTableHeaders, 0);
        billTable = new JTable(billTableModel);
        JScrollPane scrollBillTable = new JScrollPane(billTable);
        scrollBillTable.getViewport().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollBillTable, BorderLayout.CENTER);
        clearButton = new JButton("Clear ALL");
        clearButton.setBackground(new Color(255, 225, 112));
        panel.add(clearButton, BorderLayout.SOUTH);
        clearButton.addActionListener(this);

        billSectionPanel.add(panel, BorderLayout.CENTER);
        billLabel2 = new JLabel("TOTAL : $");
        billLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        billLabel2.setBackground(new Color(255, 225, 112));
        billLabel2.setPreferredSize(new Dimension(0,50));
        billLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        billSectionPanel.add(billLabel2, BorderLayout.SOUTH);

        add(billSectionPanel, BorderLayout.WEST);
    }

    private void registerSection(){
        JPanel registerSectionButtonAction = new JPanel(new GridBagLayout());
        registerSectionButtonAction.setBackground(Color.WHITE);
        registerSectionButtonAction.setPreferredSize(new Dimension(850, 150));
        registerSectionButtonAction.setBorder(new CompoundBorder(new EmptyBorder(0,10,0,10),new TitledBorder("Action Buttons")));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,15,0,15);

        RoundedPanel[] homes = initializingButton(registerSectionButtonAction, gbc, 0, 251, 221, 92, "Home");
        homeButton = homes[0];
        highLightHomeButton = homes[1];

        RoundedPanel[] profiles = initializingButton(registerSectionButtonAction, gbc, 1, 42, 58, 97, "Profile");
        profileButton = profiles[0];
        highLightProfileButton = profiles[1];

        RoundedPanel[] books = initializingButton(registerSectionButtonAction, gbc, 2, 181, 211, 145, "Book Info");
        bookInfoButton = books[0];
        highLightBookButton = books[1];

        RoundedPanel[] invoices = initializingButton(registerSectionButtonAction, gbc, 3, 210, 156, 213, "Invoice");
        billButton = invoices[0];
        highLightBillButton = invoices[1];

        RoundedPanel[] logouts = initializingButton(registerSectionButtonAction, gbc, 4, 50, 194, 214, "Log out");
        logOutButton = logouts[0];
        highLightLogOutButton = logouts[1];
        simulatePanelClick(homeButton);
        registerSectionPanel.add(registerSectionButtonAction, BorderLayout.NORTH);
        add(registerSectionPanel, BorderLayout.CENTER);
    }
    private void homeButton(){
        inputBookInfoPanel = new JPanel(new BorderLayout());
        inputBookInfoPanel.setBackground(Color.WHITE);
        inputBookInfoPanel.setBorder(new CompoundBorder(new EmptyBorder(0,10,47,10),new TitledBorder("Hello")));

        JPanel insertBookInfoPanel = new JPanel(new GridBagLayout());
        insertBookInfoPanel.setBackground(Color.WHITE);
        insertBookInfoPanel.setBorder(new CompoundBorder(new EmptyBorder(0,10,10,10),new TitledBorder("Insert Book")));
        GridBagConstraints gbc2 = new GridBagConstraints();

        customerTextField = initializingComponents("Customer Name: ", gbc2, 0, 0, insertBookInfoPanel);

        comboBox("book_id", "SELECT book_id FROM tbl_book_records",gbc2, 2, 0, insertBookInfoPanel);

        JTextField employeeNameTextField;
        employeeNameTextField = initializingComponents("Employee Name: ", gbc2, 0, 1, insertBookInfoPanel);
        employeeNameTextField.setText(name);
        employeeNameTextField.setEnabled(false);

        priceTextField = initializingComponents("Price: ", gbc2, 2, 1, insertBookInfoPanel);

        qtyTextField = initializingComponents("Item Quantity: ", gbc2, 0, 2, insertBookInfoPanel);

        gbc2.gridx = 2;
        gbc2.gridy = 2;
        gbc2.insets = new Insets(0,50,0,0);
        JLabel discountLabel = new JLabel("Discount: ");
        insertBookInfoPanel.add(discountLabel, gbc2);

        gbc2.gridx++;
        gbc2.insets = new Insets(0,10,0,0);
        JPanel discountPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc3 = new GridBagConstraints();
        discountPanel.setPreferredSize(new Dimension(200,30));
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.insets = new Insets(0,0,0,25);
        _5_Percents = new JCheckBox("5%");
        _5_Percents.setPreferredSize( new Dimension(42, 25));
        _5_Percents.setFocusPainted(false);
        discountPanel.add(_5_Percents, gbc3);
        _5_Percents.addItemListener(this);

        gbc3.gridx++;
        gbc3.insets = new Insets(0,10,0,0);
        _10_Percents = new JCheckBox("10%");
        _10_Percents.setPreferredSize( new Dimension(50, 25));
        _10_Percents.setFocusPainted(false);
        discountPanel.add(_10_Percents, gbc3);
        _10_Percents.addItemListener(this);

        gbc3.gridx++;
        gbc3.insets = new Insets(0,30,0,0);
        _20_Percents = new JCheckBox("20%");
        _20_Percents.setPreferredSize( new Dimension(50, 25));
        _20_Percents.setFocusPainted(false);
        discountPanel.add(_20_Percents, gbc3);
        _20_Percents.addItemListener(this);
        insertBookInfoPanel.add(discountPanel, gbc2);

        gbc2.gridx = 3;
        gbc2.gridy = 3;
        gbc2.insets = new Insets(5,150,30,0);
        addButton = new JButton("ADD");
        addButton.addActionListener(this);
        insertBookInfoPanel.add(addButton, gbc2);

        inputBookInfoPanel.add(insertBookInfoPanel, BorderLayout.NORTH);
        registerSectionPanel.add(inputBookInfoPanel, BorderLayout.CENTER);
    }
    private void bookInfoButton(){
        String[] cName = {"ID", "Title", "Author's Name", "Stock", "Adding Date"};
        DefaultTableModel model = new DefaultTableModel(cName, 0);
        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        inputBookInfoPanel.add(scrollPane, BorderLayout.CENTER);
        registerSectionPanel.add(inputBookInfoPanel, BorderLayout.CENTER);
        bookRecordTable();
    }
    private void billButton(){
        String[] cName = {"TXN ID", "Customer Name", "Book ID", "Employee ID", "Price", "Qty", "Discount", "Date", "Total"};
        DefaultTableModel model = new DefaultTableModel(cName, 0);
        billTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        inputBookInfoPanel.add(scrollPane, BorderLayout.CENTER);
        registerSectionPanel.add(inputBookInfoPanel, BorderLayout.CENTER);
        transactionRecordTable();
    }
    private JTextField initializingComponents(String labelName, GridBagConstraints gbc, int x, int y, JPanel panel){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(0, 0, 20, 10);
        if(Objects.equals(labelName, "Customer Name: ")){
            gbc.insets = new Insets(20, 0, 20, 10);
        }
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        if(Objects.equals(labelName, "Price: ")){
            gbc.insets = new Insets(0,50,0,0);
        }
        JLabel label = new JLabel(labelName);
        panel.add(label, gbc);
        JTextField textField;
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx++;
        if(Objects.equals(labelName, "Price: ")){
            gbc.insets = new Insets(0,10,0,0);
        }
        panel.add(textField, gbc);

        return textField;
    }
    private JComboBox comboBox(String columnLabelSQL, String sql, GridBagConstraints gbc, int x, int y, JPanel panel){
        bookIDComboBox = new JComboBox<>();
        bookIDComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(19, 50, 0, 0);
        JLabel label = new JLabel("Book ID: ");
        panel.add(label, gbc);
        bookIDComboBox.addItem("Select");
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                bookIDComboBox.addItem(resultSet.getString(columnLabelSQL));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        gbc.insets = new Insets(19, 10, 0, 0);
        gbc.gridx++;
        panel.add(bookIDComboBox, gbc);
        return bookIDComboBox;
    }
    private RoundedPanel[] initializingButton(JPanel panel, GridBagConstraints gbc, int x, int R, int G, int B, String name){
        gbc.gridx = x;
        gbc.gridy = 0;
        RoundedPanel roundedPanel = new RoundedPanel(30, false);
        roundedPanel.setBackground(new Color(R, G, B));
        roundedPanel.setPreferredSize(new Dimension(76, 76));
        RoundedPanel highLighter = new RoundedPanel(30, false);
        highLighter.setBackground(new Color(R, G, B));
        System.out.println(highLighter.getBackground());
        highLighter.setPreferredSize(new Dimension(68, 68));
        ImageIcon image =new ImageIcon(new ImageIcon("D:\\Java\\project-java\\icon\\"+  name +"-icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(image);
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Times new roman", Font.BOLD, 20));
        panel.add(imageLabel, gbc);
        panel.add(highLighter, gbc);
        panel.add(roundedPanel, gbc);
        gbc.gridy++;
        panel.add(nameLabel, gbc);
        roundedPanel.addMouseListener(this);

        return new RoundedPanel[]{roundedPanel, highLighter};
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == homeButton){
            if(inputBookInfoPanel != null){
                registerSectionPanel.remove(inputBookInfoPanel);
            }
            registerSectionPanel.revalidate();;
            registerSectionPanel.repaint();
            homeButton();
        }else if(e.getSource() == profileButton){

        }else if(e.getSource() == bookInfoButton){
            registerSectionPanel.remove(inputBookInfoPanel);
            inputBookInfoPanel.removeAll();
            registerSectionPanel.revalidate();
            registerSectionPanel.repaint();
            bookInfoButton();
        }else if(e.getSource() == billButton){
            registerSectionPanel.remove(inputBookInfoPanel);
            inputBookInfoPanel.removeAll();
            registerSectionPanel.revalidate();
            registerSectionPanel.repaint();
            billButton();
        }else if(e.getSource() == logOutButton){
            dispose();
            AdminCashierDashboard adminCashierDashboard = new AdminCashierDashboard();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == homeButton){
            highLightHomeButton.setBackground(Color.WHITE);
        }else if(e.getSource() == profileButton){
            highLightProfileButton.setBackground(Color.WHITE);
        }else if(e.getSource() == bookInfoButton){
            highLightBookButton.setBackground(Color.WHITE);
        }else if(e.getSource() == billButton){
            highLightBillButton.setBackground(Color.WHITE);
        }else if(e.getSource() == logOutButton){
            highLightLogOutButton.setBackground(Color.WHITE);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == homeButton){
            highLightHomeButton.setBackground(new Color(251, 221, 92));
        }else if(e.getSource() == profileButton){
            highLightProfileButton.setBackground(new Color(42, 58, 97));
        }else if(e.getSource() == bookInfoButton){
            highLightBookButton.setBackground(new Color(181, 211, 145));
        }else if(e.getSource() == billButton){
            highLightBillButton.setBackground(new Color(210, 156, 213));
        }else if(e.getSource() == logOutButton){
            highLightLogOutButton.setBackground(new Color(50, 194, 214));
        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == _5_Percents && e.getStateChange() == ItemEvent.SELECTED) {
            disc = 0.05f;
            _10_Percents.setSelected(false);
            _20_Percents.setSelected(false);
        } else if (e.getSource() == _10_Percents && e.getStateChange() == ItemEvent.SELECTED) {
            disc = 0.1f;
            _5_Percents.setSelected(false);
            _20_Percents.setSelected(false);
        }else if (e.getSource() == _20_Percents && e.getStateChange() == ItemEvent.SELECTED) {
            disc = 0.2f;
            _5_Percents.setSelected(false);
            _10_Percents.setSelected(false);
        }else if (e.getStateChange() == ItemEvent.DESELECTED) {
            disc = 0.0f;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            if(customerTextField.getText().isEmpty() || Objects.equals(bookIDComboBox.getSelectedItem(), "Select") || priceTextField.getText().isEmpty() || qtyTextField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please Complete all REQUIRED Field. OPTIONAL For Discount!!!");
            }else {
                billTableAction();
                try {
                    connection = DriverManager.getConnection(url, user, password);
                    preparedStatement = connection.prepareStatement("INSERT INTO `tbl_transactions_records`(`customer_name`, `book_id`, `employee_id`, `price`, `qty`, `discount`, `date`) VALUES ( ?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, customerTextField.getText());
                    int ID = Integer.parseInt(Objects.requireNonNull(bookIDComboBox.getSelectedItem()).toString());
                    preparedStatement.setInt(2, ID);
                    preparedStatement.setInt(3, id);
                    preparedStatement.setFloat(4, Float.parseFloat(priceTextField.getText()));
                    int QTY = Integer.parseInt(qtyTextField.getText());
                    preparedStatement.setInt(5, QTY);
                    preparedStatement.setFloat(6, disc);
                    Date currentDate = new Date(System.currentTimeMillis());
                    preparedStatement.setDate(7, currentDate);
                    preparedStatement.executeUpdate();
                    customerTextField.setText("");
                    bookIDComboBox.setSelectedItem("Select");
                    priceTextField.setText("");
                    qtyTextField.setText("");
                    _5_Percents.setSelected(false);
                    _10_Percents.setSelected(false);
                    _20_Percents.setSelected(false);

                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT `stock` FROM `tbl_book_records` WHERE book_id = " + ID);
                    int stock = 0;
                    while (resultSet.next()) {
                        stock = resultSet.getInt("stock");
                    }
                    preparedStatement = connection.prepareStatement("UPDATE `tbl_book_records` SET `stock`= ? WHERE book_id = ?");
                    preparedStatement.setInt(1, (stock - QTY));
                    preparedStatement.setInt(2, ID);
                    preparedStatement.executeUpdate();
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                } finally {
                    try {
                        if (resultSet != null) resultSet.close();
                        if (statement != null) statement.close();
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e2) {
                        throw new RuntimeException(e2);
                    }

                }
            }
        }
        if(e.getSource() == clearButton){
            amount = 0;
            DefaultTableModel model = (DefaultTableModel) billTable.getModel();
            model.setRowCount(0);
            billLabel2.setText("TOTAL : $");
            billSectionPanel.revalidate();
            billSectionPanel.repaint();
        }
    }
    private void bookRecordTable() {
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
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
                throw new RuntimeException(ex);
            }
        }
    }
    private void transactionRecordTable(){
        DefaultTableModel model = (DefaultTableModel) billTable.getModel();
        model.setRowCount(0);
        billSectionPanel.revalidate();
        billSectionPanel.repaint();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tbl_transactions_records");
            while (resultSet.next()){
                int txn_id = resultSet.getInt("txn_id");
                String customerName = resultSet.getString("customer_name");
                int bookID = resultSet.getInt("book_id");
                int emp_id = resultSet.getInt("employee_id");
                float price = resultSet.getFloat("price");
                int qty = resultSet.getInt("qty");
                float discount = resultSet.getFloat("discount");
                float total = resultSet.getFloat("total");
                Date date = resultSet.getDate("date");
                model.addRow(new Object[]{txn_id, customerName, bookID, emp_id, price, qty, discount, total, date});
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
                throw new RuntimeException(ex);
            }
        }
    }
    private void billTableAction(){
        DefaultTableModel mode = (DefaultTableModel) billTable.getModel();
        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT title FROM `tbl_book_records` WHERE book_id = " + bookIDComboBox.getSelectedItem());
            String title = "";
            while (resultSet.next()){
                title = resultSet.getString("title");
            }
            float total = Integer.parseInt(qtyTextField.getText()) * Float.parseFloat(priceTextField.getText());
            if (_5_Percents.isSelected()) {
                total = total - (total * 0.05f);
            }
            if(_10_Percents.isSelected()){
                total = total - (total * 0.1f);
            }
            if(_20_Percents.isSelected()){
                total = total - (total * 0.2f);
            }
            mode.addRow(new Object[]{title, total});
            amount = amount + total;
            billLabel2.setText("TOTAL : $" + amount);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void simulatePanelClick(RoundedPanel panel) {
        MouseEvent clickEvent = new MouseEvent(panel, MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(), 0, panel.getWidth() / 2, panel.getHeight() / 2,
                1, false, MouseEvent.BUTTON1);
        panel.dispatchEvent(clickEvent);
    }
}
