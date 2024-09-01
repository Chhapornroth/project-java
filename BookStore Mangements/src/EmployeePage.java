import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeePage extends JFrame implements ActionListener, FocusListener, ItemListener {
    private JPanel centerPanel;
    private final JPanel centerPanelOfCenter = new RoundedPanel(25, false);
    private JTextField customerTextField;
    private JTextField priceTextField;
    private JTextField qtyTextField;
    private JTextField searchField;
    private JComboBox<String> bookIDComboBox;
    private JCheckBox _5_Percents, _10_Percents, _20_Percents;
    private float disc = 0;
    private JButton homeButton, bookInfoButton, billButton, logOutButton, addButton;
    private JTable bookTable, billTable;
    private final int id;
    private final String name;
    private final String phoneNumber;
    private String email, imageIcon;
    private String clickOnWhichButton;
    private boolean exists;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    PreparedStatement preparedStatement1;
    ResultSet resultSet;
    public EmployeePage(int id, String name, String phoneNumber){
        super("FOR EMPLOYEES ONLY!!!");
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        //WEST
        JPanel westPanel = new JPanel();
        westPanel.setBorder(new EmptyBorder(0,100,0,0));
        add(westPanel, BorderLayout.WEST);

        //EAST
        JPanel eastPanel = new JPanel();
        eastPanel.setBorder(new EmptyBorder(0,0,0,100));
        add(eastPanel, BorderLayout.EAST);

        centerPanel();
        bottomLeftPanel();

        setVisible(true);
    }
    private void centerPanel(){
        centerPanel = new JPanel(null);
        add(centerPanel, BorderLayout.CENTER);

        RoundedPanel bigCover = new RoundedPanel(25, true);
        bigCover.setLayout(new BorderLayout());
        bigCover.setBounds(0,180,350,240);
        bigCover.setBackground(Color.white);

        RoundedPanel coverColor = new RoundedPanel(25, true);
        coverColor.setBackground(new Color(251, 251, 157));
        coverColor.setBounds(0, 75, 350, 120);

        RoundedPanel whitePanel = new RoundedPanel(200, false);
        whitePanel.setBackground(new Color(255, 255, 255));
        whitePanel.setBounds(82, 95, 185, 185);
        JPanel img;
        if(isExists()){
            img = getRoundedPanel(new ImageIcon(new ImageIcon(imageIcon).getImage().getScaledInstance(1080, 1080, Image.SCALE_SMOOTH)));
        }else {
            img = getRoundedPanel(new ImageIcon("D:\\Java\\project-java\\icon\\profile.png"));
        }


        img.setBounds(86, 100, 175,175);

        JLabel nameProfile = new JLabel(name,  SwingConstants.CENTER);
        label("Employee ID: ", String.valueOf(id), 250);
        label("Name: ", name, 270);
        label("Phone Number: ", phoneNumber, 290);
        label("Email: ", email, 310);
        nameProfile.setFont(new Font("Time news roman", Font.PLAIN, 20));
        nameProfile.setBounds(86, 160, 175, 175);
        bigCover.add(nameProfile, BorderLayout.CENTER);

        centerPanelOfCenter.setBounds(410, 200, 770, 400);
        centerPanelOfCenter.setBackground(Color.WHITE);
        centerPanelOfCenter.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to our Bookstore!");
        welcomeLabel.setFont(font(60, "kh CN Star Regular.ttf"));
        welcomeLabel.setForeground(new Color(43, 111, 255));
        welcomeLabel.setBounds(475, 105, 900, 50);

        searchField = new JTextField();
        searchField.setBounds(410, 165, 770, 30);

        centerPanel.add(welcomeLabel);
        centerPanel.add(searchField);
        centerPanel.add(centerPanelOfCenter);
        centerPanel.add(img);
        centerPanel.add(whitePanel);
        centerPanel.add(coverColor);
        centerPanel.add(bigCover);
    }
    private void label(String name,String value, int y){
        JLabel label = new JLabel(name + value);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        label.setBounds(100, y, 300,175);
        centerPanel.add(label);
    }
    private void bottomLeftPanel(){
        RoundedPanel ButtonActionPanel = new RoundedPanel(25, false);
        ButtonActionPanel.setBackground(Color.white);
        ButtonActionPanel.setBounds(0, 430, 350, 250);
        ButtonActionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.add(ButtonActionPanel);

        homeButton = initializingButton("Home", gbc, ButtonActionPanel, 0, 0, 0, 1);
        bookInfoButton = initializingButton("Book Info", gbc, ButtonActionPanel, 1, 0, 1, 1);
        billButton = initializingButton("Bill", gbc, ButtonActionPanel, 0, 2, 0, 3);
        logOutButton = initializingButton("Log out", gbc, ButtonActionPanel, 1, 2, 1, 3);

        homeButtonAction();
    }
    private JButton initializingButton(String iconName, GridBagConstraints gbc, RoundedPanel panel, int bx, int by, int lx, int ly) {
        JButton button = new JButton();
        Image image = new ImageIcon("D:\\Java\\project-java\\icon\\" + iconName + "-icon.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        button = new JButton(new ImageIcon(image));
        button.setPreferredSize(new Dimension(50, 50));
        if (iconName.equals("Home") || iconName.equals("Bill")) {
            gbc.insets = new Insets(0, 0, 0, 30);
        }else{
            gbc.insets = new Insets(0, 0, 0, 0);
        }
        gbc.gridx = bx;
        gbc.gridy = by;
        panel.add(button, gbc);
        JLabel nameLabel = new JLabel(iconName);
        nameLabel.setFont(font(15, "VintageCanvaRegular.ttf"));
        switch (iconName) {
            case "Home" -> gbc.insets = new Insets(5, 0, 25, 30);
            case "Book Info" -> gbc.insets = new Insets(5, 0, 25, 0);
            case "Bill" -> gbc.insets = new Insets(5, 0, 0, 30);
            default -> gbc.insets = new Insets(5, 0, 0, 0);
        }
        gbc.gridx = lx;
        gbc.gridy = ly;
        panel.add(nameLabel, gbc);
        button.addActionListener(this);
        return button;
    }
    private static Font font(float size, String name){
        Font font;
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\Java\\project-java\\Font\\" + name));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            font = font.deriveFont(Font.TRUETYPE_FONT, size);
        }catch (FontFormatException | IOException f){
            throw new RuntimeException(f);
        }
        return font;
    }
    private void homeButtonAction(){
        if(centerPanelOfCenter != null){
            centerPanelOfCenter.removeAll();
            centerPanelOfCenter.revalidate();
            centerPanelOfCenter.repaint();
        }
        searchField.setVisible(false);
        assert centerPanelOfCenter != null;
        initialisingLabel("Customer Name: ", 50, 50);
        customerTextField = new JTextField();
        customerTextField.setBounds(170,50 ,200, 25);
        centerPanelOfCenter.add(customerTextField);

        initialisingLabel("Book ID: ", 430, 50);
        centerPanelOfCenter.add(comboBox("book_id", "SELECT book_id FROM tbl_book_records", 500,50));

        initialisingLabel("Employee Name: ", 50, 100);
        JTextField employeeNameTextField = new JTextField();
        employeeNameTextField.setBounds(170,100, 200, 25);
        employeeNameTextField.setText(name);
        employeeNameTextField.setEnabled(false);
        centerPanelOfCenter.add(employeeNameTextField);

        initialisingLabel("Price ($):", 430, 100);
        priceTextField = new JTextField();
        priceTextField.setBounds(500,100, 200, 25);
        centerPanelOfCenter.add(priceTextField);

        initialisingLabel("Quantity: ", 50, 150);
        qtyTextField = new JTextField();
        qtyTextField.setBounds(170,150, 200, 25);
        centerPanelOfCenter.add(qtyTextField);

        initialisingLabel("Discount: ", 430, 150);
        _5_Percents = new JCheckBox("5%");
        _5_Percents.setBounds(500, 150, 42, 25);
        _5_Percents.setFocusPainted(false);
        centerPanelOfCenter.add(_5_Percents);
        _5_Percents.addItemListener(this);

        _10_Percents = new JCheckBox("10%");
        _10_Percents.setBounds(574, 150, 47, 25);
        _10_Percents.setFocusPainted(false);
        centerPanelOfCenter.add(_10_Percents);
        _10_Percents.addItemListener(this);

        _20_Percents = new JCheckBox("20%");
        _20_Percents.setBounds(651, 150, 47, 25);
        _20_Percents.setFocusPainted(false);
        centerPanelOfCenter.add(_20_Percents);
        _20_Percents.addItemListener(this);

        JTextField bookIMG = new JTextField();
        bookIMG.setBounds(50, 210, 100, 150);
        centerPanelOfCenter.add(bookIMG);

        addButton = new JButton("Add");
        addButton.setBounds(625, 330, 75, 25);
        centerPanelOfCenter.add(addButton);
        addButton.addActionListener(this);
    }
    private void initialisingLabel(String name, int x, int y){
        JLabel label = new JLabel(name);
        label.setFont(new Font("", Font.PLAIN, 13));
        label.setBounds(x,y ,200, 25);
        centerPanelOfCenter.add(label);
    }
    private JComboBox comboBox(String columnLabelSQL, String sql, int x, int y){
        bookIDComboBox = new JComboBox<>();
        bookIDComboBox.setBounds(x, y, 200,25);
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
        return bookIDComboBox;
    }
    private void bookInfoButtonAction(){
        centerPanelOfCenter.removeAll();
        centerPanelOfCenter.revalidate();
        centerPanelOfCenter.repaint();
        searchField.setVisible(true);
        searchField.setText("Search Here!!!  :)");
        searchField.addFocusListener(this);
        String[] cName = {"ID", "Title", "Author's Name", "Stock", "Adding Date"};
        DefaultTableModel model = new DefaultTableModel(cName, 0);
        bookTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBounds(0, 0, 770, 400);
        bookTable.getColumnModel().getColumn(1).setPreferredWidth(280);
        centerPanelOfCenter.add(scrollPane);
        bookRecordTable();
    }
    private void billButtonAction(){
        centerPanelOfCenter.removeAll();
        centerPanelOfCenter.revalidate();
        centerPanelOfCenter.repaint();
        searchField.setVisible(true);
        searchField.setText("Search Here!!!  :)");
        searchField.addFocusListener(this);
        String[] cName = {"TXN ID", "Customer Name", "Book ID", "Employee ID", "Price", "Qty", "Discount", "Date", "Total"};
        DefaultTableModel model = new DefaultTableModel(cName, 0);
        billTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.setBounds(0, 0, 770, 400);
        centerPanelOfCenter.add(scrollPane);
        transactionRecordTable();
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
    @Override
    public void focusGained(FocusEvent e) {
        if(searchField.getText().equals("Search Here!!!  :)")){
            searchField.setText("");
            JTable table = new JTable();
            if(clickOnWhichButton.equals("bookInfoButton")){
                table = bookTable;
            }else if(clickOnWhichButton.equals("billButton")){
                table = billTable;
            }
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String searchText = searchField.getText();
                    if (searchText.trim().isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + (searchText)));
                    }
                }
            });
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(searchField.getText().isEmpty()){
            searchField.setText("Search Here!!!  :)");
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
        if (e.getSource() == homeButton){
            homeButtonAction();
        }else if(e.getSource() == bookInfoButton){
            bookInfoButtonAction();
            clickOnWhichButton = "bookInfoButton";
        }else if(e.getSource() == billButton){
            billButtonAction();
            clickOnWhichButton = "billButton";
        } else if (e.getSource() == logOutButton) {
            AdminCashierDashboard adminCashierDashboard = new AdminCashierDashboard();
            dispose();
        } else if (e.getSource() == addButton) {
            if(customerTextField.getText().isEmpty() || Objects.equals(bookIDComboBox.getSelectedItem(), "Select") || priceTextField.getText().isEmpty() || qtyTextField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please Complete all REQUIRED Field. OPTIONAL For Discount!!!");
            }else {
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
                    preparedStatement1 = connection.prepareStatement("UPDATE `tbl_book_records` SET `stock`= ? WHERE book_id = ?");
                    preparedStatement1.setInt(1, (stock - QTY));
                    preparedStatement1.setInt(2, ID);
                    preparedStatement1.executeUpdate();
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                } finally {
                    try {
                        if (resultSet != null) resultSet.close();
                        if (statement != null) statement.close();
                        if (preparedStatement != null) preparedStatement.close();
                        if (preparedStatement1 != null) preparedStatement1.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e2) {
                        throw new RuntimeException(e2);
                    }

                }
            }
        }
    }
    private boolean isExists (){
        try {
            connection = DriverManager.getConnection(url, user, password);
            preparedStatement = connection.prepareStatement("SELECT EXISTS ( SELECT 1 FROM tbl_employee_additional_info WHERE id = ?)");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }
            if(exists){
                preparedStatement = connection.prepareStatement("SELECT image, email FROM tbl_employee_additional_info WHERE id = ?");
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    imageIcon = resultSet.getString(1);
                    email = resultSet.getString(2);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return exists;
    }
    private static JPanel getRoundedPanel(ImageIcon imageIcon) {
        return new JPanel() {
            final int size = 175;
            final Image image = imageIcon.getImage();
            {
                setOpaque(false);
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
                g2d.setClip(circle);
                g2d.drawImage(image, 0, 0, size, size, this);
                g2d.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(size, size);
            }
        };
    }
}