import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookStore{
    private static final Logger LOGGER = Logger.getLogger(BookStore.class.getName());
    public BookStore(){}
    public static void main(String[] args) {
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.SEVERE, "An error occurred", e);
        }
//        SwingUtilities.invokeLater(FormLogin::new);
        SwingUtilities.invokeLater(AdminPage::new);
    }
}