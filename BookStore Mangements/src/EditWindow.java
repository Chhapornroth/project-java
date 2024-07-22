import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EditWindow extends JFrame implements ActionListener {
    JTextField jTextField1;
    JTextField jTextField2;
    JTextField jTextField3;
    JTextField jTextField4;
    JButton editButton;
    private final Date date = new Date();

    public EditWindow(AdminPage adminPageInstance){
        super("Manipulating Data");
        setSize(700, 300);
        setVisible(true);
//        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //all components
        panelComponents(adminPageInstance);
    }
    private void panelComponents(AdminPage adminPageInstance){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        TitledBorder titledBorder = new TitledBorder("Edit Data");
        titledBorder.setTitleColor(Color.BLUE);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        panel.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 10, 10), new TitledBorder(titledBorder)));
        add(panel, BorderLayout.CENTER);
        gbc.insets  = new Insets(0, 10, 10, 10);
        int w  = 200;
        jTextField1 = adminPageInstance.itemPosition(gbc, panel, "Book ID:", 0, 0, 1, 0, w);
        jTextField2 = adminPageInstance.itemPosition(gbc, panel, "Book Title:", 2, 0, 3, 0, w);
        jTextField3 = adminPageInstance.itemPosition(gbc, panel, "Author Name:", 0, 1, 1, 1, w);
        jTextField4 = adminPageInstance.itemPosition(gbc, panel, "Stock:", 2, 1, 3, 1, w);
        gbc.insets  = new Insets(0, 10, 10, 10);
        JPanel panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        adminPageInstance.date(panel1, gbc1, "Adding date");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        panel.add(panel1, gbc);
        editButton = new JButton("OK");
        add(editButton, BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
