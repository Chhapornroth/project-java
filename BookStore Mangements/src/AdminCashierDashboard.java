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
    JLabel userLabel = new JLabel("Determine User Identity: ");
    JLabel admin = new JLabel("Admin");
    JLabel cashier =new JLabel("Cashier");
    
    public AdminCashierDashboard(){
        super("Admin and Cashier Dashboard");
        //add action to Admin Button
        adminButton.addActionListener((ActionEvent e) -> {
            Login formLogin = new Login("Admin");
            dispose();
        });
        //add action to Cashier Button
        cashierButton.addActionListener((ActionEvent e) -> {
            Login formLogin = new Login("Employee");
            dispose();
        });
        
        // Resize icons
        adminButton.setIcon(resizeIcon(new ImageIcon("D:\\Java\\project-java\\icon\\admin.png"), 50));
        cashierButton.setIcon(resizeIcon(new ImageIcon("D:\\Java\\project-java\\icon\\guy-icon.png"), 50));

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
        admin.setBounds(173,85,50,10);
        admin.setFont(new Font("",Font.BOLD,12));
//        admin.setForeground(new Color(50, 194, 214));
        add(admin,BorderLayout.CENTER);
        cashier.setBounds(273,85,50,10);
        cashier.setFont(new Font("",Font.BOLD,12));
        add(cashier,BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(adminButton);
        panel.add(cashierButton);
        add(panel, BorderLayout.CENTER);
        
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("", Font.BOLD, 16));
//        userLabel.setForeground(new Color(50, 194, 214));
        add(userLabel, BorderLayout.NORTH);

        validate();
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
