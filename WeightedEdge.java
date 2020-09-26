
/**
 * A weighted edge in a graph
 * Parameters are immutable as edges
 * in this board structure are constant
 * and indicate direction of adjacency
 * between tiles. This should never change.
 * i.e. The top left tile will always be left adjacent
 * to the top middle tile.
 * Since direction is more than just from and to,
 * weight is added to indicate adjacency in the 4 cardinal directions.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class WeightedEdge
{
    /**
     * A generic weight for the edge drawn between two nodes.
     * Can be reused in other code, but assigned specific values
     * indicating up down left or right here.
     */
    private final int edgeWeight;
    /**
     * The node an edge is being drawn from
     */
    private final Node fromNode;
    /**
     * The node an edge is being drawn to
     */
    private final Node toNode;
    
    /**
     * Creates an edge from one node to the other with an integer weight greater than zero
     * @param edgeWeight 1 is up, 2 right, 3 down, 4 left.
     * For the sake of calculation 1 is subtracted from the weight when determining
     * side adjacency (since card indices start at 0 not 1)
     **/
    public WeightedEdge(Node fromNode, Node toNode, int edgeWeight)
    {
        this.edgeWeight = edgeWeight;
        this.fromNode = fromNode;
        this.toNode = toNode;
        
    }
    /**
     * Gets the edge weight, which is important when comparing two cards to determine
     * which side rank to compare
     */
    public int getEdgeWeight(){
        return this.edgeWeight;
    }
    /**
     * Gets the source node of the edge
     */
    public Node getFromNode(){
        return this.fromNode;
    }
    /** 
     * Gets the destination node of the edge
     */
    public Node getToNode(){
        return this.toNode;
    }
    /**
     * Returns the card on the destination side of the node.
     * Called by a guarded method in board that compares
     */
    public Card getTargetCard(){
        Node targetNode = this.getToNode();
        Card targetCard = targetNode.getCard();
        return targetCard;
    }
    /**
     * Compares this object to another object.
     * If it has the same Source Node, Destination Node and Edge Weight
     * then these two WeightedEdges are the same.
     */
    public boolean equals(WeightedEdge that){
        boolean sameSource = this.getFromNode().equals(that.getFromNode());
        boolean sameDestination = this.getToNode().equals(that.getToNode());
        boolean sameWeight = this.getEdgeWeight() == that.getEdgeWeight();
        if(sameSource && sameDestination && sameWeight){
            return true;
        } else{
            return false;
        }
    }
    /**
     * Prints an edge's two nodes and its weight as a string like "1 --(0.25)--> 3"
     */
    public String toString(){
        String outputString = "";
        outputString = outputString.concat(""+getFromNode()+" --("+getEdgeWeight()+")--> "+getToNode());
        //Outputs in this format "1 --(0.25)--> 3"
        return outputString;
    }

}
