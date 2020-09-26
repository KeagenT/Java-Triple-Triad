import java.awt.*;
import javax.swing.JFrame;
/**
 * Instantiates the Model and View classes
 * which instantiate all other classes.
 * Handles toggling turns between players and ending the game
 * when the board is full.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TripleTriadController
{
    // instance variables - replace the example below with your own
    TripleTriadModel tripleTriadModel;
    TripleTriadView tripleTriadView;
    JFrame frame;
    public static void main(String[] args){
        TripleTriadController game = new TripleTriadController();
        

        
    }
    /**
     * Constructor for objects of class GameModel
     */
    public TripleTriadController()
    {
       tripleTriadModel = new TripleTriadModel();
       tripleTriadView = new TripleTriadView(tripleTriadModel);
       frame = new JFrame("Triple Triad");
       frame.getContentPane().add(tripleTriadView);
       frame.setBackground(Color.green);
       GameListener gameListener = new GameListener(this);
       tripleTriadView.addMouseListener(gameListener);
       frame.pack();
       frame.setVisible(true);
       
    }

    public void handleMouseClick(Point p){
        boolean validPlayPossible = this.tripleTriadModel.attemptPlay(p);
        if(validPlayPossible){
            this.tripleTriadModel.play(p);
            this.tripleTriadModel.toggleActivePlayer();
            this.tripleTriadModel.updateGameState();
            this.tripleTriadView.repaint();
        }
    }
}
