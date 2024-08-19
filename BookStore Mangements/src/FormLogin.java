import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class FormLogin extends JFrame implements ActionListener{
    private final JLabel lblName = new JLabel("Full Name: ");
    private final JLabel lblPhoneNumber = new JLabel("Password: ");
    private final JTextField txtName = new JTextField();
    private final JPasswordField txtPhoneNumber = new JPasswordField();
    private final Button btnLogin = new Button("Login");
    private final Button btnExit = new Button("Exit");
    private final JPanel pnl1;
    private final JPanel pnl2;
    private final String choice;
    int error = 0;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";

    public FormLogin(String choice){
        super("Login Window");
        this.choice = choice;
        pnl1 = new JPanel();
        pnl1.setLayout(new BorderLayout());
        pnl2 = new JPanel();
        pnl2.setLayout(new GridLayout(3,2));
        pnl2.add(lblName);
        pnl2.add(txtName);
        pnl2.add(lblPhoneNumber);
        pnl2.add(txtPhoneNumber);
        pnl2.add(btnLogin);
        pnl2.add(btnExit);
        pnl1.add(pnl2,BorderLayout.CENTER);
        add(pnl1);
        
        txtPhoneNumber.setEchoChar('*');
        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnLogin){
            int id = 0;
            try{
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT name, employee_id FROM tbl_employee_records WHERE phone_number = ?");
                preparedStatement.setString(1, txtPhoneNumber.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                String name = "";

                while(resultSet.next()){
                    name = resultSet.getString("name");
                    id = resultSet.getInt("employee_id");
                }
                if(txtName.getText().isEmpty() || txtPhoneNumber.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please Complete The Required Fields");
                }else {
                    if(txtName.getText().equals(name)){
                        if(choice.equals("Admin")){
                            AdminPage adminpage = new AdminPage();
                        }else if(choice.equals("Employee")){
                            EmployeePage employeepage = new EmployeePage(id, txtName.getText(), txtPhoneNumber.getText());
                        }
                        dispose();
                    }else{
                        if(error <= 2){
                            error += 1;
                            JOptionPane.showMessageDialog(null, "Wrong Account");
                        }else{
                            txtPhoneNumber.setEnabled(false);
                            txtPhoneNumber.setEnabled(false);
                            btnLogin.setEnabled(false);
                            JOptionPane.showMessageDialog(null,"Max attempts reached");
                        }
                    }
                }
                preparedStatement.close();
                conn.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        } else if(ae.getSource() == btnExit){
            System.exit(0);
        }
    }
}