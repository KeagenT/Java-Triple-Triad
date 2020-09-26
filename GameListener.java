import java.awt.event.*;
import java.awt.*;
/**
 * Listens to mouse clicks and passes the point to the
 * Controller class
 * @author Keagen Thomson
 * @version (a version number or a date)
 */
public class GameListener extends MouseAdapter
{
    TripleTriadController master;
    /**
     * Constructor for objects of class GameListener
     */
    public GameListener(TripleTriadController master)
    {
       this.master = master;
    }
    public void mouseClicked(MouseEvent me) {
        Point p  = me.getPoint();
        master.handleMouseClick(p);
    }

    
}
