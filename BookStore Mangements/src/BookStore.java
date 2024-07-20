import javax.swing.*;

public class BookStore{

    public BookStore(){
    
    }
    public static void main(String[] args) {
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
//       FormLogin FormLogin = new FormLogin();
        new adminPage();
    }
}