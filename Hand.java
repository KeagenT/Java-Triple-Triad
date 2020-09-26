import java.util.ArrayList;
/**
 * Player hands. Generated from shuffling the cardpool and drawing
 *
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class Hand extends CardCollection
{

    /**
     * Constructor for objects of class Hand
     * Takes string "Player 1" or "Player 2"
     */
    public Hand(String label)
    {
        super(label);
        this.buildHand();
    }
    public Hand(String label, HiddenCardPool playerDeck){
        super(label);
        this.buildHand(playerDeck);
    }
    /**
     * initializes the starting hand with 5 cards
     * sets the card owner equal to the label for coloring purposes
     */
    public void buildHand(HiddenCardPool cardPool){
        cardPool.deal(this, 5);
        for(int i = 0; i<this.size();i++){
            this.showCard(i).setOwner(this.getLabel());
        }
        
        
    }
    public void buildHand(){
        HiddenCardPool cardPool = new HiddenCardPool("cardPool");
        buildHand(cardPool);
    }
    /**
     * Wrapper method for getting a hand size
     */
    public int getHandSize(){
        return this.size();
    }
    /**
     * Gets the cards currently in the hand as a list
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }
    /**
     * Displays the hand to the console
     */
    public void display(){
        System.out.println(getLabel() + ": ");
        for (int i = 0; i < size(); i++) {
            System.out.println(showCard(i));
        }
        System.out.println();
        
    }
}
