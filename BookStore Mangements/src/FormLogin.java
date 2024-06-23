import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */

public class FormLogin extends JFrame implements ActionListener{
    private final JLabel lblName = new JLabel("User Name: ");
    private final JLabel lblPw = new JLabel("Password: ");
    private final JTextField txtName = new JTextField();
    private final JPasswordField txtPw = new JPasswordField();
    private final Button btnLogin = new Button("Login"); 
    private final Button btnExit = new Button("Exit");
    private final JPanel pnl1;
    private final JPanel pnl2;
    int error = 0;
    public FormLogin(){
        pnl1 = new JPanel();
        pnl1.setLayout(new BorderLayout());
        pnl2 = new JPanel();
        pnl2.setLayout(new GridLayout(3,2));
        pnl2.add(lblName);
        pnl2.add(txtName);
        pnl2.add(lblPw);
        pnl2.add(txtPw);
        pnl2.add(btnLogin);
        pnl2.add(btnExit);
        pnl1.add(pnl2,BorderLayout.CENTER);
        add(pnl1);
        
        txtPw.setEchoChar('*');//all inputs in txtPw shown as "*"
        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnLogin){
            if(txtName.getText().compareTo("group1") == 0 && txtPw.getText().compareTo("123456") == 0){
                AdminCashierDashboard ACD = new AdminCashierDashboard();   
                dispose();
            }else{
                if(error <= 2){
                    error += 1;
                    JOptionPane.showMessageDialog(null, "Wrong Account");
                }else{
                    txtPw.setEnabled(false);
                    txtName.setEnabled(false);
                    btnLogin.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"Max attempts reached");
                }
            }
        }
        if(ae.getSource() == btnExit){
            System.exit(0);
        }
    }
}
