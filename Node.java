
/**
 * Node(Vertex) of a Graph Structure.
 * In this context each node represents
 * an index in the board object. Each index
 * of the board represents a tile on the 3x3 playing field.
 * Nodes know their in/outdegrees and by extension their 
 * adjacencies (especially since we're representing the graph
 * as an adjacency list). Every tile on the board knows which tiles it is next to.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class Node
{
    /**
     * The unique index of a node,
     * used specifically when generating a directed graph 
     * with the node
     */
    private int index;
    /**
     * Indicates if a node has a card for an abundance of reasons
     * such as calculating score and if a card comparison needs to be made
     */
    private boolean hasCard;
    /**
     * The card that's being placed into the node.
     */
    private Card card;
    /**
     * Constructor for objects of class Node, takes the index "tile"
     * it represents, which also serves as the nodes ID as you will see
     * in the "equals" method. 
     */
    public Node(int index)
    {
        this.hasCard = false;
        this.index = index;
        this.card = card;
    }
    /**
     * Getter for card stored in node. 
     * This should only be called by the board if hasCard is true
     */
    public Card getCard(){
        return this.card;
    }
    /**
     * Setter for the card stored in node.
     */
    public void setCard(Card card){
        this.card = card;
        this.setHasCard(true);
    }
    /**
     * getter for the hasCard attribute. This attribute is used when testing adjacent nodes 
     * to see if card comparisons need to be done between that node's card stored in the board class.
     */
    public boolean getHasCard(){
        return this.hasCard;
    }
    /**
     * Setter for the hasCard attribute. when a card is played in this index, the hasCard attribute should be true,
     * and false when empty.
     */
    public void setHasCard(boolean hasCard){
        this.hasCard = hasCard;
    }
    /**
     * Toggles the has card attribute to true from false if a card is played
     * though it CAN be used simply to toggle state.
     */
    public void toggleHasCard(){
        this.setHasCard(!getHasCard());
    }
    /**
     * Getter for the node's ID and index. mapped to the equivalent index in the board class (when the graph structure is created)
     */
    public int getIndex(){
        return this.index;
    }
    /**
     * A node is equal to another node if they have the same index value in this context
     */
    public boolean equals(Node that){
        if(this.index == that.index){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Converts a node to a string representation of its index and card if any
     */
    public String toString(){
        String outputString = ""+ getIndex() +": No Card";
        if(this.getHasCard()){
            outputString = ""+ getIndex() + ": " + getCard();
        }
        return outputString;
    }
}
