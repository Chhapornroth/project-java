
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

/*
 * Click infos://hoist/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click infos://hoist/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */

public class AdminCashierDashboard extends JFrame{
    
    JButton adminButton = new JButton();
    JButton cashierButton=new JButton();
    Label userLabel = new Label("Determine User Identity: ");
    Label admin = new Label("Admin");
    Label cashier=new Label("Cashier");
    
    public AdminCashierDashboard(){
        super("Admin and Cashier Dashboard");
        JPanel Panel = new JPanel();
        
        //add action to Admin Button
        adminButton.addActionListener((ActionEvent e) -> {
            AdminPage adminPage = new AdminPage();
            dispose();
        });
        //add action to Cashier Button
        cashierButton.addActionListener((ActionEvent e) -> {
        });
        
        // Resize icons
        adminButton.setIcon(resizeIcon(new ImageIcon("D:\\Java\\project-java\\icon\\Admin.png"), 50, 40));
        cashierButton.setIcon(resizeIcon(new ImageIcon("D:\\Java\\project-java\\icon\\cashier.png"), 60, 50));

        // Set button size
        adminButton.setPreferredSize(new Dimension(100, 50));
        cashierButton.setPreferredSize(new Dimension(100, 50));

        setLayout(new BorderLayout());
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        
        //Label
        admin.setBounds(173,90,50,10);
        admin.setFont(new Font("",Font.BOLD,12));
        add(admin,BorderLayout.CENTER);
        cashier.setBounds(273,90,50,10);
        cashier.setFont(new Font("",Font.BOLD,12));
        add(cashier,BorderLayout.CENTER);
        Panel.add(adminButton);
        Panel.add(cashierButton);
        add(Panel, BorderLayout.CENTER);
        
        userLabel.setAlignment(Label.CENTER); // Center label
        userLabel.setFont(new Font("", Font.BOLD, 16));
        add(userLabel, BorderLayout.NORTH);

        validate();
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
