import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.sql.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ViewProfile extends JFrame {
    private RoundedPanel editButton0;
    private RoundedPanel editButton1;
    private JLabel button;
    JPanel p3Image;
    private JTextField nameT, genderT, phoneNumberT, emailT, birthDayT;
    private final int id;  private final AdminPage adminPageInstance;
    String name, gender, phoneNumber; Date birthday;
    boolean exists, isExistId;
    private RoundedPanel r1, r2, r3, r4, r5;
    private String imageIconProfile, getEmail, imageAbsolutePath;
    String strImagePath;
    String url = "jdbc:mariadb://localhost:3306/Bookstore_Managements";
    String user = "root";
    String password = "";
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null, rs1 = null;

    public ViewProfile(int id, AdminPage adminPageInstance) {
        super("View Profile");

        this.id = id;
        this.adminPageInstance = adminPageInstance;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 690);
        setLocationRelativeTo(null);
        setResizable(false);
        panel();
        setVisible(true);
    }
    private void panel(){
        JLabel profileLabel = new JLabel("My Profile");
        profileLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(profileLabel, BorderLayout.NORTH);

        JPanel south_panel = new JPanel(new GridBagLayout());
        south_panel.setBorder(new EmptyBorder(0, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        editButton0 = new RoundedPanel(40, false);
        editButton0.setBackground(new Color(4, 153, 216));
        editButton0.setPreferredSize(new Dimension(256, 40));
        editButton1 = new RoundedPanel(40, false);
        editButton1.setPreferredSize(new Dimension(252, 36));
        editButton1.setBackground(new Color(4, 153, 216));
        button = new JLabel("Update Profile");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(3,0,0,0));
        editButton1.add(button);
        setEditButton1Effect();
        south_panel.add(editButton1, gbc);
        south_panel.add(editButton0, gbc);
        add(south_panel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        RoundedPanel p1 = new RoundedPanel(200, false);
        p1.setBackground(new Color(4, 153, 216));
        p1.setPreferredSize(new Dimension(200, 200));
        RoundedPanel p2 = new RoundedPanel(196, false);
        p2.setBackground(new Color(240, 240, 240));
        p2.setPreferredSize(new Dimension(196, 196));
        ImageIcon image;
        isExists();
        if (!isExists()){
            image = new ImageIcon(new ImageIcon("D:\\Java\\project-java\\icon\\profile.png").getImage().getScaledInstance(1080, 1080, Image.SCALE_SMOOTH));
        }else {
            image = new ImageIcon(new ImageIcon(imageIconProfile).getImage().getScaledInstance(1080, 1080, Image.SCALE_SMOOTH));
        }
        p3Image = getRoundedPanel(image);
        p3Image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser imageChooser = new JFileChooser();
                imageChooser.setDialogTitle("Select a Image");
                int result = imageChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    imageAbsolutePath = file.getAbsolutePath();
                    profilePanel.remove(p3Image);
                    p3Image = getRoundedPanel(new ImageIcon(new ImageIcon(imageAbsolutePath).getImage().getScaledInstance(1080, 1080, Image.SCALE_SMOOTH)));
                    profilePanel.add(p3Image, c1);
                }
            }
        });
        profilePanel.add(p3Image, c1);
        profilePanel.add(p2, c1);
        profilePanel.add(p1, c1);
        centerPanel.add(profilePanel, c);

        c.gridy = 1;
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();

        getData();

        r1 = new RoundedPanel(38, false);
        nameT = textBox(c2, infoPanel,0,1, "Name", r1);
        nameT.setText(name);
        r2 = new RoundedPanel(38, false);
        genderT = textBox(c2, infoPanel,2,3, "Gender", r2);
        genderT.setText(gender);
        r3 = new RoundedPanel(38, false);
        phoneNumberT = textBox(c2, infoPanel,4,5, "Phone Number", r3);
        phoneNumberT.setText(phoneNumber);
        r4 = new RoundedPanel(38, false);
        emailT = textBox(c2, infoPanel,6,7, "Email", r4);
        emailT.setText(getEmail);
        r5 = new RoundedPanel(38, false);
        birthDayT = textBox(c2, infoPanel,8,9, "BirthDay", r5);
        birthDayT.setText(birthday.toString());

        centerPanel.add(infoPanel, c);
        add(centerPanel, BorderLayout.CENTER);
    }
    private JTextField textBox (GridBagConstraints c2, JPanel infoPanel, int yLabel, int yTextField, String name, RoundedPanel i2){
        c2.gridx = 0;
        c2.gridy = yLabel;
        c2.anchor = GridBagConstraints.WEST;
        JLabel infoLabel = new JLabel("      " + name);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoPanel.add(infoLabel, c2);
        c2.gridy = yTextField;
        RoundedPanel i1 = new RoundedPanel(40, false);
        i1.setBackground(Color.BLACK);
        i1.setPreferredSize(new Dimension(256, 40));
        i2.setBackground(new Color(240,240,240));
        i2.setPreferredSize(new Dimension(254, 38));
        JTextField infoField = new JTextField();
        infoField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoField.setBorder(null);
        infoField.setPreferredSize(new Dimension(225, 30));
        infoField.setEnabled(false);
        UIManager.put("infoField.disabledBackground",new ColorUIResource(Color.WHITE));
        c2.anchor = GridBagConstraints.CENTER;
        infoPanel.add(infoField, c2);
        infoPanel.add(i2, c2);
        infoPanel.add(i1, c2);
        return infoField;
    }
    private void setEditButton1Effect (){
        editButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(button.getText().equals("Update Profile")) {
                    editButton1.setBackground(Color.WHITE);
                    button.setForeground(new Color(4, 153, 216));
                }else if(button.getText().equals("Okay")) {
                    editButton1.setBackground(Color.WHITE);
                    button.setForeground(new Color(5, 227, 91));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(button.getText().equals("Update Profile")) {
                    editButton1.setBackground(new Color(4, 153, 216));
                    button.setForeground(Color.WHITE);
                }else if(button.getText().equals("Okay")) {
                    editButton1.setBackground(new Color(5, 227, 91));
                    button.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(button.getText().equals("Update Profile")) {
                    button.setText("Okay");
                    editButton0.setBackground(new Color(5, 227, 91));
                    button.setForeground(new Color(5, 227, 91));
                    r1.setBackground(Color.WHITE);
                    r2.setBackground(Color.WHITE);
                    r3.setBackground(Color.WHITE);
                    r4.setBackground(Color.WHITE);
                    r5.setBackground(Color.WHITE);
                    nameT.setEnabled(true);
                    genderT.setEnabled(true);
                    phoneNumberT.setEnabled(true);
                    emailT.setEnabled(true);
                    birthDayT.setEnabled(true);
                }else if(button.getText().equals("Okay")) {
                    try{
                        conn = DriverManager.getConnection(url, user, password);
                        pStmt = conn.prepareStatement("UPDATE tbl_employee_records SET name = ?, gender = ? , phone_number = ?, birthday = ? WHERE tbl_employee_records.employee_id = ?");
                        pStmt.setString(1, nameT.getText());
                        pStmt.setString(2, genderT.getText());
                        pStmt.setString(3, phoneNumberT.getText());
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date utilDate = formatter.parse(birthDayT.getText());
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            pStmt.setDate(4, sqlDate);
                        }catch (ParseException e2){
                            throw new RuntimeException(e2);
                        }
                        pStmt.setInt(5, id);
                        pStmt.executeUpdate();

                        if(isExistId){
                            pStmt = conn.prepareStatement("UPDATE tbl_employee_additional_info SET image = ?, email = ? WHERE id =?");
                        }else{
                            pStmt = conn.prepareStatement("INSERT INTO tbl_employee_additional_info VALUES (?,?,?)");
                        }
                        if(imageAbsolutePath == null){
                            imageAbsolutePath = strImagePath;
                        }
                        pStmt.setString(1, imageAbsolutePath);
                        pStmt.setString(2, emailT.getText());
                        pStmt.setInt(3, id);
                        pStmt.executeUpdate();
                    }catch (SQLException ex){
                        throw new RuntimeException(ex);
                    }finally {
                        try{
                            if(pStmt != null)pStmt.close();
                            if(conn != null)conn.close();
                        }catch (SQLException ex){
                            throw new RuntimeException(ex);
                        }
                    }
                    dispose();
                    adminPageInstance.updateEmployeeRecordTable();
                }
            }
        });
    }
    private void getData(){
        try{
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT `name`, `gender`, `phone_number`, `birthday` FROM `tbl_employee_records` WHERE employee_id = " + id);
            while(rs.next()){
                name = rs.getString("name");
                gender = rs.getString("gender");
                phoneNumber = rs.getString("phone_number");
                birthday = rs.getDate("birthday");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean isExists (){
        try {
            conn = DriverManager.getConnection(url, user, password);
            pStmt = conn.prepareStatement("SELECT EXISTS ( SELECT 1 FROM tbl_employee_additional_info WHERE id = ?)");
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                exists = rs.getBoolean(1);
                System.out.println(exists);
            }
            if(exists){
                isExistId = exists;
                stmt = conn.createStatement();
                rs1 = stmt.executeQuery("SELECT image, email FROM tbl_employee_additional_info WHERE id = " + id);
                if(rs1.next()){
                    strImagePath = rs1.getString(1);
                    getEmail = rs1.getString(2);
                    System.out.println(strImagePath);
                    if(strImagePath == null || strImagePath.isEmpty()){
                        exists = false;
                    }else {
                        imageIconProfile = strImagePath;
                        System.out.println(exists);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(exists);
        return exists;
    }
    private static JPanel getRoundedPanel(ImageIcon imageIcon) {
        return new JPanel() {
            final int size = 186;
            final Image image = imageIcon.getImage();
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
