import java.util.ArrayList;
/**
 * This is the Data structure that keeps track of cards played
 * On the board as well as the direction squares on the board are adjacent
 * Handles Comparisons of cards on the board after plays and modifies the
 * Nodes and WeightedEdges that comprise it. The weight of the edge indicates
 * whether cards are adjacent on their Top, Right, Bottom or Left sides.
 * Nodes are squares on the board that can hold a card, tell what nodes they're adjacent to
 * and whether or not they hold a card. When all nodes hold a card the game is complete.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class Board
{
    // instance variables - replace the example below with your own
    /**
     * The amount of tiles high the board should be
     */
    private final int height;
    /**
     * The amount of tiles wide should be
     */
    private final int width;
    //Array of nodes that are modified and propogated to the board.
    /**
     * An array of nodes referenced by the board's graph structure.
     * Changes and  plays to the board modify this attribute
     * to likewise update the adjacency list
     */
    private Node[] playedCards;
    //Adjacency list representation of a graph used for the board
    /**
     * An Adjacency list of Weighted edges that reference the played card
     * nodes. Each Index abstractly represents an index in the playedCards
     * array, and which nodes are "adjacent" to that same node.
     * Used when calculating whether a card captures what it is placed next to.
     */
    private ArrayList<WeightedEdge>[] board;

    /**
     * Constructor for objects of class BoardDataStructure
     * Builds a board of variable dimensions where each square
     * knows its adjacent squares (technically referred to as nodes)
     */
    public Board(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.board = board;
        this.playedCards = playedCards;
        buildPlayedCards();
        initializeBoard();
        buildBoard();
    }
    /**
     * Getter for all played cards.
     * Used when selecting a specific Node
     * and when determining if the board is filled and
     * the game is over. Also used when drawing the board's
     * contents to the screen.
     */
    public Node[] getPlayedCards(){
        return this.playedCards;
    }
    /**
     * Initializes the playedCards array with length of the board's dimensions
     */
    private void setPlayedCardsSize(int size){
        this.playedCards = new Node[size];
    }
    /**
     * Setter for nodes at a given index
     * used indirectly when playing a card and directly
     * when initializing the reference node array for the board
     */
    private void setPlayedCardNode(int i, Node node){
        this.playedCards[i] = node;
    }
    /**
     * gets the node at index i
     */
    public Node getPlayedCardNode(int i){
        return getPlayedCards()[i];
    }
    /**
     * Sets the card of a node at index i
     */
    private void setNodeCard(int i, Card card){
        Node thisNode = getPlayedCardNode(i);
        thisNode.setCard(card);
        this.setPlayedCardNode(i, thisNode);
    }
    /**
     * Initializes an array of empty nodes matching the dimensions of the board
     **/
    private void buildPlayedCards(){
        this.setPlayedCardsSize(getTotalIndexes());
        for(int i = 0; i < getTotalIndexes(); i++){
            Node node = new Node(i);
            this.setPlayedCardNode(i, node);
        }
    }
    /**
     * Sets the array size of the board. Used when initializing and assigning value to the board attribute.
     */
    private void setBoardSize(int size){
        this.board = new ArrayList[size];
    }
    /**
     * Checks if a card can be played at an index
     * then returns a value indicating if a card can be played there.
     * Used by other objects to test before calling a method to remove
     * a card from their collection.
     * @return returns a boolean indicating if the play was successful
     */
    public boolean attemptPlayCardAt(int boardIndex){
        boolean playSuccessful;
        if(this.getPlayedCardNode(boardIndex).getHasCard()){
            playSuccessful = false;
        } else {
            playSuccessful = true;
        }
        return playSuccessful;
    }
    /**
     * Plays a card on the board at a specified instance,
     * then tries to capture cards at all adjacent indexes
     * if possible
     * @param boardIndex the index calculated after clicking on the view representation of the board
     * @param card the card to be removed from a card collection and played on the board at the given index
     */
    public void playCardAt(int boardIndex, Card card){
        setNodeCard(boardIndex, card);
        captureAdjacentCards(boardIndex, card);
    }
    /**
     * Getter for board height. shouldn't change after construction of board.
     */
    public int getHeight(){
        return this.height;
    }
    /**
     * Iterates through every card on the board and adds to the player count total if it equals the input string
     * @param player the owner attribute that's either "Player 1" or "Player 2"
     * @return the amount of cards a player has on the board
     */
    public int getPlayedCardTotal(String player){
        int playedCardTotal = 0;
        for(Node node: getPlayedCards()){
            if(node.getHasCard()){
                String cardOwner = node.getCard().getOwner();
                if(cardOwner.equals(player)){
                    playedCardTotal += 1;
                }
            }
        }
        return playedCardTotal;
    }
    /**
     * Checks adjacent nodes at an index and captures all possible cards
     * and updates the node in playedCard.
     * @param is the Card passed into play card at by a player.
     */
    private void captureAdjacentCards(int index, Card card){
        Card targetCard;
        for(WeightedEdge adjacentEdge: this.getAdjacencies(index)){
            Node adjacentNode = adjacentEdge.getToNode();
            int adjacentNodeIndex = adjacentNode.getIndex();
            int sideAdjacentIndex = adjacentEdge.getEdgeWeight() - 1;
            if(adjacentNode.getHasCard()){
                targetCard = adjacentNode.getCard();
                boolean capturesCard = card.compareTo(targetCard, sideAdjacentIndex);
                if(capturesCard){
                    Card capturedCard = card.capture(targetCard);
                    this.setNodeCard(adjacentNodeIndex, capturedCard);
                }   
            }
        }
        
    }
    /**
     * Getter for board height. shouldn't change after construction of board.
     */
    public int getWidth(){
        return this.width;
    }
    /**
     * Normalizes board dimensions to a one dimensional array
     */
    private int getTotalIndexes(){
        return this.getHeight()*this.getWidth();
    }
    /**
     * Initializes the adjacency list board by adding i ArrayLists for every node
     */
    private void initializeBoard(){
        this.setBoardSize(this.getTotalIndexes());
        for (int i = 0; i < this.getPlayedCards().length ; i++){
            this.board[i] = new ArrayList<WeightedEdge>();
        }
    }
    /**
     * Creates an edge to be added to the adjacency list
     */
    private WeightedEdge buildEdge(Node source, Node destination, int weight){
        WeightedEdge newEdge = new WeightedEdge(source, destination, weight);
        return newEdge;
    }
    /**
     * Getter for the array of adjacency lists that make up the board
     */
    private ArrayList<WeightedEdge>[] getWholeBoard(){
        return this.board;
    }
    /**
     * Returns the adjacencies of
     */
    public ArrayList<WeightedEdge> getAdjacencies(int index){
        return this.getWholeBoard()[index];
    }
    /**
     * setter for an adjacency list of a node.
     * indexes of the array represent the index of a node
     */
    private void addAdjacency(WeightedEdge edge, int index){
        this.board[index].add(edge);
    }
    /**
     * Builds the adjacency list representation of the directed graph board.
     * The edge weights indicate the direction up: 1, right: 2, down: 3 and left: 4
     * If a node isn't a (direction)Border then adds an edge in that direction to the
     * node in that direction.
     */
    private void buildBoard(){
        int width = this.getWidth();
        for(int i = 0; i < this.getTotalIndexes(); i++){
            //Monolith code I know
            boolean topBorder = i - width < 0;
            int topNode = i - width;
            boolean rightBorder = (i + 1) % width == 0;
            int rightNode = i + 1;
            boolean bottomBorder = i + width >= this.getTotalIndexes();
            int bottomNode = i + width;
            boolean leftBorder = i % width == 0;
            int leftNode = i - 1;
            //If it isn't a (direction) border, draw an edge to the node (direction) it 
            //Then add it to that node's adjacency list
            if(!topBorder){
                WeightedEdge topEdge = this.buildEdge(this.getPlayedCardNode(i), 
                this.getPlayedCardNode(topNode), 1);
                this.addAdjacency(topEdge, i);
            }
            if(!rightBorder){
                WeightedEdge rightEdge = this.buildEdge(this.getPlayedCardNode(i), 
                this.getPlayedCardNode(rightNode), 2);
                this.addAdjacency(rightEdge, i);
            }
            if(!bottomBorder){
                WeightedEdge bottomEdge = this.buildEdge(this.getPlayedCardNode(i), 
                this.getPlayedCardNode(bottomNode), 3);
                this.addAdjacency(bottomEdge, i);
            }
            if(!leftBorder){
                WeightedEdge leftEdge = this.buildEdge(this.getPlayedCardNode(i), 
                this.getPlayedCardNode(leftNode), 4);
                this.addAdjacency(leftEdge, i);
            }
        }
    }
}
