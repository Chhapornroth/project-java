
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
public class adminPage extends JFrame {
    JPanel centerPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JLabel topLeft = new JLabel();
    JPanel bottomLeft = new JPanel();
    JPanel inputBookInformationPanel = new JPanel();
    
    private JLabel bookId, title, author, stock, date, inStock;
    private JTextField txtBookId, txtTitle, txtAuthor, txtStock;
    
    public adminPage() {
        setTitle("Admin Page");
        setSize(1250, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
/*------------------- Left Panel -------------------*/
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcLeftPanel = new GridBagConstraints();
        gbcLeftPanel.fill = GridBagConstraints.BOTH;
        
    /*---------- Top Left Panel ----------*/
        //add topLeft to the  leftPanel
        topLeft.setLayout(new BorderLayout());
        topLeft.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(20,0,10,0)));
        gbcLeftPanel.weightx = 1;
        gbcLeftPanel.weighty = 0.8/3.0;
        gbcLeftPanel.gridx = 0;
        gbcLeftPanel.gridy = 0;
        leftPanel.add(topLeft, gbcLeftPanel);
        
        //add admin icon
        ImageIcon img = new ImageIcon("D:\\Java\\project-java\\icon\\Admin.png");
        Image image = img.getImage().getScaledInstance(100, 100,Image.SCALE_SMOOTH);
        ImageIcon iconImage = new ImageIcon(image);
        JLabel adminIcon = new JLabel(iconImage);
        topLeft.add(adminIcon,BorderLayout.NORTH);
        
        JLabel textAdmin = new JLabel("WELLCOME TO THE ADMIDPAGE");
        textAdmin.setFont(new Font("Arial",Font.BOLD,14));
        textAdmin.setHorizontalAlignment(SwingConstants.CENTER);
        topLeft.add(textAdmin);
    /*---------- Top Left Panel ----------*/
        
    /*---------- Bottom Left Panel ----------*/
        //add bottomLeft to the  leftPanel
        bottomLeft.setLayout(new GridBagLayout());
        bottomLeft.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(12,12,10,12)));
        gbcLeftPanel.weighty = 2.20/3.0;
        gbcLeftPanel.gridx = 0;
        gbcLeftPanel.gridy = 2;
        leftPanel.add(bottomLeft, gbcLeftPanel);
        
        //create a label and drop it into bottomLeft panel
        bottomLeft.setLayout(new BorderLayout());
        JLabel moreOption = new JLabel("More Options Below Here!");
        moreOption.setFont(new Font("Arial", Font.PLAIN, 15));
        moreOption.setHorizontalAlignment(SwingConstants.CENTER);
        moreOption.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(5,0,5,0)));
        bottomLeft.add(moreOption,BorderLayout.NORTH);
        
        //create option panel and drop it into bottomLeft panel
        JPanel option = new JPanel();
        option.setLayout(new GridBagLayout());
        option.setBorder(new CompoundBorder(new TitledBorder(""),new EmptyBorder(0,0,0,0)));
        GridBagConstraints gbcInOptionPanel = new GridBagConstraints();
        bottomLeft.add(option,BorderLayout.CENTER);
        
        //home button
        Image homeIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\home-icon.png").getImage().getScaledInstance(19, 19,Image.SCALE_SMOOTH);
        JButton homeButton = new JButton("Home", new ImageIcon(homeIconImage));
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
        JButton BookRecords = new JButton("Book Records",new ImageIcon(BookIconImage));
        BookRecords.setFont(new Font("Arial", Font.PLAIN, 15));
        gbcInOptionPanel.gridx = 0;
        gbcInOptionPanel.gridy = 1;
        BookRecords.setHorizontalAlignment(SwingConstants.LEFT);
        BookRecords.setPreferredSize(new Dimension(0,38));
        BookRecords.setIconTextGap(10);
        option.add(BookRecords,gbcInOptionPanel);
                
        //employee button
        Image EmployeeIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\employee-icon.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JButton EmployeeRecords = new JButton("Employee Records",new ImageIcon(EmployeeIconImage));
        gbcInOptionPanel.gridx = 0;
        gbcInOptionPanel.gridy = 2;
        EmployeeRecords.setHorizontalAlignment(SwingConstants.LEFT);
        EmployeeRecords.setFont(new Font("Arial", Font.PLAIN, 15));
        EmployeeRecords.setPreferredSize(new Dimension(0,38));
        EmployeeRecords.setIconTextGap(7);
        option.add(EmployeeRecords,gbcInOptionPanel);
        
        //customer button
        Image CustomerIconImage = new ImageIcon("D:\\Java\\project-java\\icon\\customer-icon.png").getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH);
        JButton CustomerRecords = new JButton("Customer Records",new ImageIcon(CustomerIconImage));
        gbcInOptionPanel.gridx = 0;
        gbcInOptionPanel.gridy = 3;
        CustomerRecords.setHorizontalAlignment(SwingConstants.LEFT);
        CustomerRecords.setFont(new Font("Arial", Font.PLAIN, 15));
        CustomerRecords.setPreferredSize(new Dimension(0,38));
        CustomerRecords.setIconTextGap(9);
        option.add(CustomerRecords,gbcInOptionPanel);
        gbcInOptionPanel.gridx = 0;
        gbcInOptionPanel.gridy = 4; // Place this component below the button
        gbcInOptionPanel.weighty = 1.0; // Make this component take the remaining vertical space
        gbcInOptionPanel.fill = GridBagConstraints.BOTH;
        option.add(new JPanel(), gbcInOptionPanel); // Adding an empty panel
    /*---------- Bottom Left Panel ----------*/
/*------------------- Left Panel -------------------*/
        
        add(leftPanel, BorderLayout.WEST);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);
        splitPane.setResizeWeight(0.12); // Set the initial split ratio to 12% for the left panel
        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
        validate();
        
        //add action to A BookRecords Button
        BookRecords.addActionListener((ActionEvent e) -> {
            dataFrame("BOOK RECORDS", "ADDING BOOK INFORMATIONS", "BOOK INFORMATIONS");
            inputBookInformationPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbcInputBookInformation = new GridBagConstraints();
            gbcInputBookInformation.insets = new Insets(10, 10, 10, 10); // Set top, left, bottom, right gaps
      
            bookId = new JLabel("Book ID: ");
            bookId.setFont(new Font("", Font.PLAIN,14));
            gbcInputBookInformation.gridx = 0;
            gbcInputBookInformation.gridy = 0;
            inputBookInformationPanel.add(bookId,gbcInputBookInformation);
            gbcInputBookInformation.gridx = 1;
            gbcInputBookInformation.gridy = 0;
            txtBookId = new JTextField();
            txtBookId.setPreferredSize(new Dimension(250, 30));
            txtBookId.setBackground(Color.white);
            inputBookInformationPanel.add(txtBookId, gbcInputBookInformation);
      
            author = new JLabel("Author Name:");
            author.setFont(new Font("", Font.PLAIN,14));
            gbcInputBookInformation.gridx = 2;
            gbcInputBookInformation.gridy = 0;
            inputBookInformationPanel.add(author,gbcInputBookInformation);
            gbcInputBookInformation.gridx = 3;
            gbcInputBookInformation.gridy = 0;
            txtAuthor = new JTextField();
            txtAuthor.setPreferredSize(new Dimension(250,30));
            inputBookInformationPanel.add(txtAuthor,gbcInputBookInformation);
            
            title = new JLabel("Book Title: ");
            title.setFont(new Font("", Font.PLAIN,14));
            gbcInputBookInformation.gridx = 0;
            gbcInputBookInformation.gridy = 1;
            inputBookInformationPanel.add(title,gbcInputBookInformation);
            txtTitle = new JTextField();
            txtTitle.setPreferredSize(new Dimension(250,30));
            gbcInputBookInformation.gridx = 1;
            gbcInputBookInformation.gridy = 1;
            inputBookInformationPanel.add(txtTitle,gbcInputBookInformation);
            
            stock = new JLabel("Stock: ");
            stock.setFont(new Font("", Font.PLAIN,14));
            gbcInputBookInformation.gridx = 2;
            gbcInputBookInformation.gridy = 1;
            inputBookInformationPanel.add(stock,gbcInputBookInformation);
            txtStock = new JTextField();
            txtStock.setPreferredSize(new Dimension(250,30));
            gbcInputBookInformation.gridx = 3;
            gbcInputBookInformation.gridy = 1;
            inputBookInformationPanel.add(txtStock,gbcInputBookInformation);
            
            date = new JLabel("Adding Date: ");
            date.setFont(new Font("", Font.PLAIN,14));
            gbcInputBookInformation.gridx = 0;
            gbcInputBookInformation.gridy = 2;
            inputBookInformationPanel.add(date,gbcInputBookInformation);
        });
    }
 
    public void dataFrame(String namePanel, String nameTopPanel, String nameBottomPanel){
        /*---------- a big Center Panel----------*/
            centerPanel.setBorder(new CompoundBorder(new TitledBorder(namePanel),new EmptyBorder(0,10,9,10)));
            centerPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbcCenterPanel = new GridBagConstraints();
            
            /*----- the top of the center panel -----*/
            gbcCenterPanel.weightx = 1;
            gbcCenterPanel.weighty = 1.0/6.0;
            gbcCenterPanel.fill = GridBagConstraints.BOTH;
            gbcCenterPanel.gridx = 0;
            gbcCenterPanel.gridy = 0;
            inputBookInformationPanel.setBorder(new CompoundBorder(new TitledBorder(nameTopPanel),new EmptyBorder(0,0,0,0)));
            centerPanel.add(inputBookInformationPanel,gbcCenterPanel);
            /*----- the top of the center panel -----*/
            
            /*----- the bottom of the center panel -----*/
            JPanel BookInformationPanel = new JPanel();
            gbcCenterPanel.weighty = 5.0/6.0;
            gbcCenterPanel.gridy = 1;
            BookInformationPanel.setBorder(new CompoundBorder(new TitledBorder(nameBottomPanel),new EmptyBorder(0,0,0,0)));
            centerPanel.add(BookInformationPanel,gbcCenterPanel);
            /*----- the bottom of the center panel -----*/
        /*---------- a big Center Panel----------*/
    }
}
