
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * 
 * @author carma
 */
public class WelcomeScreen extends JPanel {

    JLabel title;
    JButton go;
    JButton quit;

    MainWindow mw;
/**
 * 
 * @param t 
 */
    public void setTitle(String t) {
        title.setText(t);
    }
/**
 * Este método se ejecuta cuando se hace clic en el botón "Quit".
 */
    public void quitButtonActionListener() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
/**
 * Este método se ejecuta cuando se hace clic en el botón "Go".
 */
    public void goButtonActionListener() {
        mw.showCard("Two");
    }
/**
 * 
 * @param mw 
 */
    public WelcomeScreen(MainWindow mw) {
        this.mw = mw;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        title = new JLabel();
        add(title);

        //add formatting here
        go = new JButton(NEW__GAME_TXT);
        quit = new JButton(QUIT_TXT);

        go.addActionListener((ActionEvent event) -> {
            goButtonActionListener();
        });

        quit.addActionListener((ActionEvent event) -> {
            quitButtonActionListener();
        });

        add(go);
        add(quit);
    }
    private static final String QUIT_TXT = "Quit";
    private static final String NEW__GAME_TXT = "New Game";

}
