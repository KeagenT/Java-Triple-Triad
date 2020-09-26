import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Creates a triple triad card object.
 * Used in decks cards hands etc. Modeled after the real life card.
 * Triple Triad cards are square cards with "Ranks" on each edge of the card.
 * Ranks of cards can range from 1 to 10 with 10 being represented by A.
 * For gameplay purposes - not Collector's purposes - only the ranks of a card are important.
 * In normal cases, cards have unique non-randomly generated ranks, however these ranks
 * are randomly generated for demonstration purposes.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class Card
{
    /**
     * The card's name, based on the final fantasy monster it's derived from
     */
    private String name;
    /**
     * The number on the top side of the card indicating its directional strength
     */
    private int rankTop;
    /**
     * the number on the right side of the card indicating its directional strength
     */
    private int rankRight;
    /**
     * the number on the bottom of the card indicating its directional strength
     */
    private int rankBottom;
    /**
     * the number on the left of the card indicating its directional strength
     */
    private int rankLeft;
    /**
     * An unused variable indicating a card's uniqueID in the event a card needs a natural order
     * or its name no longer becomes unique
     */
    private int uniqueID;
    /**
     * A list built from the integer ranks in the clockwise order of Top, right, bottom and left
     */
    private ArrayList<Integer> ranks;
    /**
     * The color of a card, which indicates the player that currently has control over it
     */
    private String color;
    /**
     * The owner of a card which indicates player of this card. Currently only "Player 1" or "Player 2"
     * but future functionality will replace these with variables dependant on user input for card names.
     */
    private String owner;
    //used when generating random card ranks
    
    /**
     * Constructor for objects of class Card
     * @param name the card's unique name also used for its image
     * @param rankTop the rank at the top edge of the card. True for each respective rank
     * @param owner the owner of the card being played. changes when captured and determines color background
     */
    public Card(String name, int rankTop, int rankRight, int rankBottom, int rankLeft,
    String owner)
    {
        this.name = name;
        this.rankTop = rankTop;
        this.rankRight = rankRight;
        this.rankBottom = rankBottom;
        this.rankLeft = rankLeft;
        //Builds a mutable list of ranks in clockwise order.
        //Used mostly so we can rotate the ranks in a given direction
        this.ranks = new ArrayList<Integer>(Arrays.asList(this.rankTop, this.rankRight, this.rankBottom
        ,this.rankLeft));
        this.owner = owner;
        this.color = "Neutral";
    }
    /**
     * Overloaded constructor used when a card's ranks are randomly generated
     */
    public Card(String name, ArrayList<Integer> ranks, String owner)
    {
        this.name = name;
        this.owner = owner;
        this.rankTop = ranks.get(0);
        this.rankRight = ranks.get(1);
        this.rankBottom = ranks.get(2);
        this.rankLeft = ranks.get(3);
        this.ranks = ranks;
        this.color = "Neutral";
    }
    /**
     * Rotates ranks Clockwise by 1-Step.
     * I may add "Hero power" functionality later that would make this useful.
     * works by popping the top of a stack and unshifting the same value to the first index
     */
    public void rotate(ArrayList<Integer> ranks){
        //inserts the last index value at the initial index and shifts items up one index
        ranks.add(0, ranks.get(ranks.size()-1));
        //removes the last index so ranks still has 4 values.
        ranks.remove(ranks.size()-1);
        //updates the ranks with the overloaded setter
        setRanks(ranks);
    }
    /**
     * Card Ranks Getter
     */
    public ArrayList<Integer> getRanks(){
        return this.ranks;
    }
    /**
     * Return ranks as a String
     */
    public String stringRanks(){
        String stringRanks = "";
        int size = this.getRanks().size();
        for(int i = 0; i < size; i++)
        {
            stringRanks = stringRanks.concat(this.getRanks().get(i) + " ");
        }
        return stringRanks;
    }
    /**
     * Card Ranks Setter. Takes Integers in Clockwise order
     * @param up the Top edge's rank etc.
     */
    public void setRanks(int up, int right, int down, int left){
        this.ranks = new ArrayList<Integer>(Arrays.asList(up, right, down , left));
    }
    public void setRanks(ArrayList<Integer> ranks){
        this.ranks = ranks;
    }
    /**
     * Card name Getter
     * Used partly to draw the card's image
     */
    public String getName(){
        return this.name;
    }
    /**
     * Card color Getter. Visually Indicates who currently controls a card.
     * Also used partly to draw the card's color
     */
    public String getColor(){
        return this.color;
    }
    /**
     * Card owner Getter
     * used when determining a card's color
     */
    public String getOwner(){
        return this.owner;
    }
    /**
     * Card  owner Setter
     * used when a card is captured
     */
    public void setOwner(String newOwner){
        this.owner = newOwner;
        this.updateColor();
    }
    /**
     * used to update a color if a card has a new owner
     */
    public void updateColor(){
        if(this.getOwner() == "Player 1"){
            this.color = "Blue";
        } else if(this.getOwner() == "Player 2"){
            this.color = "Red";
        } else {
            this.color = "Neutral"; 
        }
    }
    /**
     * Overrided equals method used to get a card with a specific name input as a string
     */
    public boolean equals(String cardName){
        //A card is equal to another card if it has the same name
        if(cardName == this.getName())
        {
            return true;
        }
        return false;
    }
    /**
     * Takes a card object as input and gets the string of its name
     * then returns true if they have the same name.
     */
    public boolean equals(Card that){
        if(that.getName() == this.getName()){
            return true;
        }
        return false;
    }
    /**
     * Creates a string representation of a card by its name and ranks with ranks being
     * in order from top to left clockwise
     */
    public String toString() {
        String outputString = "";
        outputString = outputString.concat("Card name: " + this.getName()+"\n");
        outputString = outputString.concat("Ranks: " + this.stringRanks()+ "\n");
        return outputString;
    }
    /**
     * Compares this card's side to target card's adjacent side
     * and returns a boolean 
     */
    public boolean compareTo(Card that, int side){
        boolean captures = false;
        ArrayList<Integer> thisRanks = this.getRanks();
        ArrayList<Integer> thatRanks = that.getRanks();
        if(thisRanks.get(side) > thatRanks.get(opposite(side))){
            captures = true;
        }
        return captures;
    }
    /**
     * Returns the index for the opposite side of a card.
     * Used when comparing adjacent cards to get the correct side value.
     * @param SideIndex should be a value 0 - 3
     */
    private int opposite(int sideIndex){
        int opposite;
        if(sideIndex < 2){
            opposite = sideIndex + 2;
        } else if(sideIndex >= 2){
            opposite = sideIndex - 2;
        } else {
            opposite = -1;
        }
        return opposite;
    }
    /**
     * Changes the target card's owner to the capturing card's owner
     * which updates its color and returns the captured card
     */
    public Card capture(Card that){
        String targetNewOwner = this.getOwner();
        that.setOwner(targetNewOwner);
        return that;
    }
}
    
   
