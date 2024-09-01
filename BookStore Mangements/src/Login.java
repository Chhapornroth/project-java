
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Login extends JFrame implements FocusListener {
    RoundedPanel roundedPanel,  loginButton0, loginButton1;
    JTextField fullNameTextField;
    JPasswordField passwordTextField;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";
    int error = 0;
    String choice;

    public Login(String choice) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(223, 165, 113));
        roundedPanel();
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            getContentPane().requestFocusInWindow();
        });
        this.choice = choice;
    }
    public void roundedPanel(){
        roundedPanel = new RoundedPanel(20, false);
        roundedPanel.setPreferredSize(new Dimension(800, 450));
        roundedPanel.setBackground(Color.white);
        roundedPanel.setLayout(new GridBagLayout());
        ImageIcon image = new ImageIcon(new ImageIcon("D:\\Java\\project-java\\icon\\enchanted story.jpg").getImage().getScaledInstance(1080, 1080, Image.SCALE_SMOOTH));

        RoundedPanel leftPanel = getRoundedPanel(image);
        leftPanel.setLayout(null);
        JLabel label = new JLabel("A Book is a Dream");
        label.setForeground(Color.white);
        font(label, 35f);
        label.setBounds(70, 310 , 300, 30);
        leftPanel.add(label);
        JLabel label1 = new JLabel("that you hold in your Hands");
        label1.setForeground(Color.white);
        font(label1, 30f);
        label1.setBounds(25, 350 , 500, 30);
        leftPanel.add(label1);

        roundedPanel.add(leftPanel);
        RoundedPanel rightPanel = new RoundedPanel(20, false);
        rightPanel(rightPanel);
        rightPanel.setPreferredSize(new Dimension(400, 450));
        rightPanel.setBackground(Color.WHITE);
        roundedPanel.add(rightPanel);
        add(roundedPanel);
    }

    private void font(JLabel label, float f){
        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\Java\\project-java\\Font\\Melvastype - Baguet Script Regular.otf"));
            label.setFont(font.deriveFont(f));
        }catch(IOException | FontFormatException e){
            throw new RuntimeException(e);
        }
    }
    private RoundedPanel getRoundedPanel(ImageIcon image) {
        RoundedPanel leftPanel = new RoundedPanel(20, false){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                int radius = 13;

                GeneralPath path = new GeneralPath();
                path.moveTo(radius, 0);
                path.quadTo(0, 0, 0, radius);
                path.lineTo(0, height - radius);
                path.quadTo(0, height, radius, height);
                path.lineTo(width, height);
                path.lineTo(width, 0);
                path.closePath();
                g2d.setClip(path);
                g2d.drawImage(image.getImage(), 0, 0, width, height, this);
                g2d.dispose();
            }
        };
        leftPanel.setPreferredSize(new Dimension(400, 450));
        return leftPanel;
    }

    private void rightPanel(RoundedPanel rightPanel){
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label1 = new JLabel("Welcome");
        label1.setFont(new Font("SansSerif Bold", Font.BOLD, 50));
        label1.setForeground(new Color(223, 165, 113));
        rightPanel.add(label1, gbc);

        gbc.gridy++;
        JLabel label2 = new JLabel("Login with Your FullName");
        label2.setFont(new Font("SansSerif", Font.BOLD, 13));
        label2.setForeground(Color.GRAY);
        rightPanel.add(label2, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 0, 0);
        RoundedPanel roundedPanel1 = new RoundedPanel(20, false);
        roundedPanel1.setBackground(new Color(223, 165, 113));
        roundedPanel1.setPreferredSize(new Dimension(260, 40));

        RoundedPanel roundedPanel11 = new RoundedPanel(20, false);
        roundedPanel11.setBackground(Color.WHITE);
        roundedPanel11.setPreferredSize(new Dimension(256, 36));

        fullNameTextField = new JTextField("Enter Your FullName");
        fullNameTextField.setForeground(Color.LIGHT_GRAY);
        fullNameTextField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        fullNameTextField.setBorder(BorderFactory.createEmptyBorder());
        fullNameTextField.setPreferredSize(new Dimension(230, 33));
        fullNameTextField.addFocusListener(this);
        rightPanel.add(fullNameTextField, gbc);
        rightPanel.add(roundedPanel11, gbc);
        rightPanel.add(roundedPanel1, gbc);


        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
        RoundedPanel roundedPanel2 = new RoundedPanel(20, false);
        roundedPanel2.setBackground(new Color(223, 165, 113));
        roundedPanel2.setPreferredSize(new Dimension(260, 40));

        RoundedPanel roundedPanel22 = new RoundedPanel(20, false);
        roundedPanel22.setBackground(Color.WHITE);
        roundedPanel22.setPreferredSize(new Dimension(256, 36));

        passwordTextField = new JPasswordField("Enter Your Password");
        passwordTextField.setForeground(Color.LIGHT_GRAY);
        passwordTextField.setEchoChar('\0');
        passwordTextField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passwordTextField.setBorder(BorderFactory.createEmptyBorder());
        passwordTextField.setPreferredSize(new Dimension(230, 33));
        passwordTextField.addFocusListener(this);
        rightPanel.add(passwordTextField, gbc);
        rightPanel.add(roundedPanel22, gbc);
        rightPanel.add(roundedPanel2, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 0, 0, 0);
        loginButton0 = new RoundedPanel(40, false);
        loginButton0.setBackground(new Color(4, 153, 216));
        loginButton0.setPreferredSize(new Dimension(256, 40));
        loginButton1 = new RoundedPanel(40, false);
        JLabel login = new JLabel("Login");
        login.setFont(new Font("SansSerif", Font.BOLD, 14));
        login.setForeground(Color.WHITE);
        login.setBorder(new EmptyBorder(3,0,0,0));
        loginButton1.setPreferredSize(new Dimension(252, 36));
        loginButton1.setBackground(new Color(4, 153, 216));
        loginButton1.add(login);
        loginButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(error < 2) {
                    loginButton1.setBackground(Color.WHITE);
                    login.setForeground(new Color(4, 153, 216));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(error < 2) {
                    loginButton1.setBackground(new Color(4, 153, 216));
                    login.setForeground(Color.WHITE);
                }
            }
        });
        loginButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionPerformed();
            }
        });
        rightPanel.add(loginButton1, gbc);
        rightPanel.add(loginButton0, gbc);

        gbc.gridy++;
        JLabel signUp = new JLabel("For account recovery, please get in touch with the admin");
        signUp.setFont(new Font("SansSerif", Font.PLAIN, 12));
        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signUp.setForeground(new Color(4, 153, 216));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                signUp.setForeground(Color.BLACK);
            }
        });
        rightPanel.add(signUp, gbc);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == fullNameTextField && fullNameTextField.getText().equals("Enter Your FullName")) {
            fullNameTextField.setText("");
            fullNameTextField.setForeground(Color.BLACK);
        }
        if(e.getSource() == passwordTextField && passwordTextField.getText().equals("Enter Your Password")) {
            passwordTextField.setText("");
            passwordTextField.setEchoChar('⁕');
            passwordTextField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == fullNameTextField && fullNameTextField.getText().isEmpty()) {
            fullNameTextField.setText("Enter Your FullName");
            fullNameTextField.setForeground(Color.LIGHT_GRAY);
        }
        if(e.getSource() == passwordTextField && passwordTextField.getText().isEmpty()) {
            passwordTextField.setText("Enter Your Password");
            passwordTextField.setForeground(Color.LIGHT_GRAY);
            passwordTextField.setEchoChar('\0');
        }
    }

    private void actionPerformed(){
        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT name, employee_id FROM tbl_employee_records WHERE phone_number = ?");
            preparedStatement.setString(1, passwordTextField.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = "";
            int id = 0;
            while(resultSet.next()){
                name = resultSet.getString("name");
                id = resultSet.getInt("employee_id");
            }
            if(fullNameTextField.getText().equals("Enter Your FullName") || passwordTextField.getText().equals("Enter Your Password")){
                JOptionPane.showMessageDialog(this, "Please Complete The Required Fields");
            }else {
                if(fullNameTextField.getText().equals(name)){
                    if(choice.equals("Admin")){
                        AdminPage adminpage = new AdminPage();
                    }else if(choice.equals("Employee")){
                        EmployeePage employeepage = new EmployeePage(id, fullNameTextField.getText(), passwordTextField.getText());
                    }
                    dispose();
                }else{
                    if(error < 2){
                        error ++;
                        JOptionPane.showMessageDialog(null, "Wrong Account");
                    }else{
                        passwordTextField.setEnabled(false);
                        loginButton0.setBackground(Color.LIGHT_GRAY);
                        loginButton1.setBackground(Color.LIGHT_GRAY);
                        JOptionPane.showMessageDialog(null,"Max attempts reached");
                    }
                }
            }
            preparedStatement.close();
            conn.close();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

