
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * 
 * @author carma
 */
public class BoardScreen extends JPanel {

/**
     *
     */
        BoardDrawing bd;
    JPanel stats;
    JLabel dieResults;
    JLabel whichPlayer;
    JLabel extraInfo;
    int maxPlayers = 1;
    int currPlayer = 0;
    ArrayList<Portal> portals;
    ArrayList<Player> players;
    int x;
    int y;
    JLabel success;
    JButton roll;

    MainWindow mw;

    JButton go;
    JButton quit;
/**
 * Este método se ejecuta cuando se hace clic en el botón "Quit".
 * 
 */
    public void quitButtonActionListener() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
/**
 * Este método se ejecuta cuando se hace clic en el botón "Go".
 * 
 */
    public void goButtonActionListener() {
        mw.showCard("Two");
        //mw.setBoard();
        mw.resetAll();
    }
/**
 * 
 * @param m 
 */
    public void setMaxPlayers(int m) {
        maxPlayers = m;
    }
/**
 * 
 * @return 
 */
    public int returnMaxPlayers() {
        return maxPlayers;
    }
/**
 * Este método inicializa la lista de jugadores y establece sus colores.
 * 
 */
    public void setUpPlayers() {
        players = new ArrayList<>();
        for (int i = 0; i < returnMaxPlayers(); i++) {
            players.add(new Player(i));
        }
        //get and add player(s) names

        //manual color entry - automate later
        if (0 < returnMaxPlayers()) {
            players.get(0).setPlayerColor(Color.green);
        }
        if (1 < returnMaxPlayers()) {
            players.get(1).setPlayerColor(Color.blue);
        }
        if (2 < returnMaxPlayers()) {
            players.get(2).setPlayerColor(Color.red);
        }

    }
/**
 * 
 * @param mw 
 */
    public BoardScreen(MainWindow mw) {
        this.mw = mw;

        currPlayer = 0;

        go = new JButton("New Game");
        quit = new JButton("Quit");

        go.addActionListener((ActionEvent event) -> {
            goButtonActionListener();
        });

        quit.addActionListener((ActionEvent event) -> {
            quitButtonActionListener();
        });

        players = new ArrayList<>();
        players.add(new Player(currPlayer));
        //for(int i = 0;i < returnMaxPlayers();i++)
        //    players.add(new Player(i));
        //get and add player(s) names

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        x = y = 8;

        bd = new BoardDrawing(x, y, this);
        bd.setVisible(true);
        //bd.setSize(getSize());

        int sw = getSize().width;
        int sh = getSize().height;
        int a = (int) (0.75 * ((sw > sh) ? sh : sw));

        bd.setSize(a, a);

        add(bd);

        stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.X_AXIS));
        add(stats);

        stats.add(go);
        stats.add(quit);

        //String playername = "Player 1";
        //currPlayer = 0;
        whichPlayer = new JLabel();
        whichPlayer.setText(players.get(currPlayer).getName());
        stats.add(whichPlayer);

        extraInfo = new JLabel();

        success = new JLabel("");

        //modify action listener to move between the n players 
        //outside needs to know some amount of player data which may be got be asking and passing to inside
        //no need to create separate stores outside
        //may need more functions inside to communicate for this reason
        roll = new JButton("Roll the die!");
        roll.addActionListener((ActionEvent e) -> {
            Random die = new Random();
            int a1 = die.nextInt(6) + 1;
            dieResults.setText("You rolled a " + a1);
            //bd.setPlayer(player);
            bd.setPlayer(a1, currPlayer);
            //bd.ensurePlayerPosition();
            extraInfo.setText(bd.ensurePlayerPosition(currPlayer));
            bd.repaint();
            players.get(currPlayer).incPlayerScore(1);
            players.stream().filter(p -> (p.getPosition() >= x * y - 1)).map(p -> {
                success.setText("And the winner is: " + p.getName() + "\nYour score: " + p.getPlayerScore());
                return p;
            }).forEachOrdered(_item -> {
                roll.setVisible(false);
            });
            if (currPlayer == maxPlayers - 1) {
                currPlayer = 0;
            } else {
                currPlayer += 1;
            }
            //currPlayer = players.size() - 1;
            whichPlayer.setText(players.get(currPlayer).getName());
        });
        roll.setVisible(true);

        stats.add(roll);

        dieResults = new JLabel();
        stats.add(dieResults);

        stats.add(extraInfo);
        stats.add(success);

    }

}
