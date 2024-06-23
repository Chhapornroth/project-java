
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
public class adminPage extends JFrame {
    private final JPanel centerPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private final JPanel topLeftPanel = new JPanel();
    private final JPanel bottomLeftPanel = new JPanel();
    private final JPanel inputBookInformationPanel = new JPanel();
    private JLabel bookId, title, author, stock, date;
    private JTextField txtBookId, txtTitle, txtAuthor, txtStock;
    private JButton homeButton, BookRecordButton;
    
    public adminPage(){
        setTitle("Admin Page");
        setSize(1250, 750);
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
        
        JLabel textAdmin = new JLabel("WELLCOME TO THE ADMIDPAGE");
        textAdmin.setFont(new Font("Arial",Font.BOLD,14));
        textAdmin.setHorizontalAlignment(SwingConstants.CENTER);
        topLeftPanel.add(textAdmin);
    }

    public void bottomLeftPanel(GridBagConstraints gbc) {
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
        
        //home button
        Image homeIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\home-icon.png").getImage().getScaledInstance(19, 19,Image.SCALE_SMOOTH);
        homeButton = new JButton("Home", new ImageIcon(homeIconImage));
        homeButton.setFont(new Font("Arial", Font.PLAIN, 15));
        homeButton.setPreferredSize(new Dimension(0,38));
        homeButton.setHorizontalAlignment(SwingConstants.LEFT);
        homeButton.setIconTextGap(10);
        homeButton.setBorder(new EmptyBorder(0,18,0,0));
        gbcInOptionPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcInOptionPanel.weightx = 1.0;
        gbcInOptionPanel.weighty = 0.0;
        gbcInOptionPanel.gridx = 0;
        gbcInOptionPanel.gridy = 0;
        option.add(homeButton,gbcInOptionPanel);
        
        //book button     
        Image BookIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\book-icon.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
        BookRecordButton = new JButton("Book Records",new ImageIcon(BookIconImage));
        BookRecordButton.setFont(new Font("Arial", Font.PLAIN, 15));
        gbcInOptionPanel.gridy = 1;
        BookRecordButton.setHorizontalAlignment(SwingConstants.LEFT);
        BookRecordButton.setPreferredSize(new Dimension(0,38));
        BookRecordButton.setIconTextGap(10);
        option.add(BookRecordButton,gbcInOptionPanel);
        BookRecordButton.addActionListener((ActionEvent e) -> {
            ActionListener();
        });
                
        //employee button
        Image EmployeeIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\employee-icon.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JButton EmployeeRecords = new JButton("Employee Records",new ImageIcon(EmployeeIconImage));
        gbcInOptionPanel.gridy = 2;
        EmployeeRecords.setHorizontalAlignment(SwingConstants.LEFT);
        EmployeeRecords.setFont(new Font("Arial", Font.PLAIN, 15));
        EmployeeRecords.setPreferredSize(new Dimension(0,38));
        EmployeeRecords.setIconTextGap(7);
        option.add(EmployeeRecords,gbcInOptionPanel);
        
        //customer button
        Image CustomerIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\customer-icon.png").getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH);
        JButton CustomerRecords = new JButton("Customer Records",new ImageIcon(CustomerIconImage));
        gbcInOptionPanel.gridy = 3;
        CustomerRecords.setHorizontalAlignment(SwingConstants.LEFT);
        CustomerRecords.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomerRecords.setPreferredSize(new Dimension(0,38));
        CustomerRecords.setIconTextGap(9);
        option.add(CustomerRecords,gbcInOptionPanel);
        gbcInOptionPanel.gridy = 4;
        gbcInOptionPanel.weighty = 1.0; 
        gbcInOptionPanel.fill = GridBagConstraints.BOTH;
        option.add(new JPanel(), gbcInOptionPanel);
        /*---------- Options Panel ----------*/
    }
    
    public void centerRightPanel(String namePanel, String nameTopPanel, String nameBottomPanel){
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
        JPanel BookInformationPanel = new JPanel();
        gbcCenterPanel.weighty = 5.0/6.0;
        gbcCenterPanel.gridy = 1;
        BookInformationPanel.setBorder(new CompoundBorder(new TitledBorder(nameBottomPanel),new EmptyBorder(0,0,0,0)));
        centerPanel.add(BookInformationPanel,gbcCenterPanel);
        /*----- the bottom of the center panel -----*/
    } 
    private void ActionListener() {
        centerRightPanel("BOOK RECORDS", "ADDING BOOK INFORMATIONS", "BOOK INFORMATIONS");
        inputBookInformationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcInputBookInformation = new GridBagConstraints();
        
        gbcInputBookInformation.insets = new Insets(0, 10, 10, 10);
        bookId = new JLabel("Book ID: ");
        bookId.setFont(new Font("", Font.PLAIN, 14));
        gbcInputBookInformation.gridx = 0;
        gbcInputBookInformation.gridy = 0;
        inputBookInformationPanel.add(bookId, gbcInputBookInformation);
        gbcInputBookInformation.gridx = 1;
        gbcInputBookInformation.gridy = 0;
        txtBookId = new JTextField();
        txtBookId.setPreferredSize(new Dimension(250, 30));
        inputBookInformationPanel.add(txtBookId, gbcInputBookInformation);

        author = new JLabel("Author Name:");
        author.setFont(new Font("", Font.PLAIN, 14));
        gbcInputBookInformation.gridx = 2;
        gbcInputBookInformation.gridy = 0;
        inputBookInformationPanel.add(author, gbcInputBookInformation);
        gbcInputBookInformation.gridx = 3;
        gbcInputBookInformation.gridy = 0;
        txtAuthor = new JTextField();
        txtAuthor.setPreferredSize(new Dimension(250, 30));
        inputBookInformationPanel.add(txtAuthor, gbcInputBookInformation);

        gbcInputBookInformation.insets = new Insets(10, 10, 0, 10);
        title = new JLabel("Book Title: ");
        title.setFont(new Font("", Font.PLAIN, 14));
        gbcInputBookInformation.gridx = 0;
        gbcInputBookInformation.gridy = 1;
        inputBookInformationPanel.add(title, gbcInputBookInformation);
        gbcInputBookInformation.gridx = 1;
        gbcInputBookInformation.gridy = 1;
        txtTitle = new JTextField();
        txtTitle.setPreferredSize(new Dimension(250, 30));
        inputBookInformationPanel.add(txtTitle, gbcInputBookInformation);

        stock = new JLabel("Stock: ");
        stock.setFont(new Font("", Font.PLAIN, 14));
        gbcInputBookInformation.gridx = 2;
        gbcInputBookInformation.gridy = 1;
        inputBookInformationPanel.add(stock, gbcInputBookInformation);
        gbcInputBookInformation.gridx = 3;
        gbcInputBookInformation.gridy = 1;
        txtStock = new JTextField();
        txtStock.setPreferredSize(new Dimension(250, 30));
        inputBookInformationPanel.add(txtStock, gbcInputBookInformation);

        JPanel datePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDate = new GridBagConstraints();
        date(datePanel, gbcDate, 0, 0);
        gbcInputBookInformation.gridx = 0;
        gbcInputBookInformation.gridy = 2;
        gbcInputBookInformation.gridwidth = 4;
        inputBookInformationPanel.add(datePanel, gbcInputBookInformation);
        
        JButton submitButton = new JButton("Add");
        submitButton.setFont(new Font("",Font.BOLD,15));
        submitButton.setForeground(Color.blue);
        submitButton.setPreferredSize(new Dimension(80,40));
        gbcInputBookInformation.gridx = 3;
        gbcInputBookInformation.gridy = 3;
        gbcInputBookInformation.gridwidth = 3;
        gbcInputBookInformation.anchor = GridBagConstraints.SOUTHEAST;
        inputBookInformationPanel.add(submitButton,gbcInputBookInformation);
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        });
        
        String[] bookRecordsColumn = {"ID", "Title", "Author's Name", "Stock", "Adding Date"};
        DefaultTableModel bookRecordsModel = new DefaultTableModel(null,bookRecordsColumn);
        JTable bookRecordsTable = new JTable(bookRecordsModel);
        JScrollPane scrollPane = new JScrollPane(bookRecordsTable);
        bookRecordsTable.setFillsViewportHeight(true);
    }

    private void date(JPanel panel, GridBagConstraints gbc, int x, int y) {
        String[] days = IntStream.rangeClosed(1, 31).mapToObj(Integer::toString).toArray(String[]::new);
        JComboBox<String> dayComboBox = new JComboBox<>(days);
        
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        JComboBox<String> monthComboBox = new JComboBox<>(months);
        
        String[] years = IntStream.rangeClosed(2024, 2034).mapToObj(Integer::toString).toArray(String[]::new);
        JComboBox<String> yearComboBox = new JComboBox<>(years);
        
        gbc.insets = new Insets(10, 5, 10, 10);
        date = new JLabel("Adding Date: ");
        date.setFont(new Font("", Font.PLAIN, 14));
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(date, gbc);
        
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
}