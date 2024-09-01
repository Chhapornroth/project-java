import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashRegister extends JFrame implements ActionListener {
    private JPanel billSectionPanel = new JPanel(new BorderLayout());
    private JPanel registerSectionPanel = new JPanel(new BorderLayout());
    private JTable billTable;
    private RoundedPanel homeButton, bookInfoButton, billButton, logOutButton, addButton;

    public CashRegister(){
        super("Cash Register");
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
        billSectionPanel.setPreferredSize(new Dimension(350,1000));

        JLabel billLabel = new JLabel("All Items");
        billLabel.setHorizontalAlignment(SwingConstants.CENTER);
        billLabel.setPreferredSize(new Dimension(0,50));
        billLabel.setFont(new Font("Arial", Font.BOLD, 20));
        billSectionPanel.add(billLabel, BorderLayout.NORTH);

        String[] billTableHeaders = {"Item Name", "Price"};
        DefaultTableModel billTableModel = new DefaultTableModel(billTableHeaders, 0);
        billTable = new JTable(billTableModel);
        billTable.setGridColor(billTable.getBackground());
        JScrollPane scrollBillTable = new JScrollPane(billTable);
        billSectionPanel.add(scrollBillTable, BorderLayout.CENTER);

        JLabel billLabel2 = new JLabel("TOTAL : $" + "100");
        billLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        billLabel2.setBackground(new Color(255, 225, 112));
        billLabel2.setPreferredSize(new Dimension(0,50));
        billLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        billSectionPanel.add(billLabel2, BorderLayout.SOUTH);

        add(billSectionPanel, BorderLayout.WEST);
    }

    private void registerSection(){
        JPanel registerSectionButtonAction = new JPanel(new GridBagLayout());
        registerSectionButtonAction.setPreferredSize(new Dimension(850, 200));
        registerSectionButtonAction.setBorder(new CompoundBorder(new EmptyBorder(0,10,0,0),new TitledBorder("Action Buttons")));
        GridBagConstraints gbc = new GridBagConstraints();

        homeButton = initializingButton("Home", gbc, registerSectionButtonAction, 0, 0);
        homeButton.setBackground(new Color(50, 194, 214));
        bookInfoButton = initializingButton("Book Info", gbc, registerSectionButtonAction, 1, 1);
        bookInfoButton.setBackground(new Color(181, 211, 145));
        billButton = initializingButton("Bill", gbc, registerSectionButtonAction, 2, 2);
        billButton.setBackground(new Color(251, 221, 92));
        logOutButton = initializingButton("Log out", gbc, registerSectionButtonAction, 3, 3);
        logOutButton.setBackground(new Color(210, 156, 213));

        registerSectionPanel.add(registerSectionButtonAction, BorderLayout.NORTH);

        add(registerSectionPanel, BorderLayout.CENTER);
    }
    private RoundedPanel initializingButton(String iconName, GridBagConstraints gbc, JPanel panel, int bx, int lx) {
        RoundedPanel button = new RoundedPanel(30, false);
        Image image = new ImageIcon("D:\\Java\\project-java\\icon\\" + iconName + "-icon.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        button.setLayout(new GridBagLayout());
        button.setPreferredSize(new Dimension(100, 100));
        gbc.gridx = bx;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20,0, 20);

        button.add(image);

        panel.add(button, gbc);
        JLabel nameLabel = new JLabel(iconName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = lx;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
