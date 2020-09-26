import java.awt.Point;

/**
 * Keeps track of the index of clicks on the board.
 * Checks if a click is either on the board or on the active player's
 * hand and converts the coordinates accordingly.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class ClickState
{
    /**
     * The saved index of the last clicked card in hand
     */
    private int handIndex;
    /**
     * The saved index of the last selected 
     */
    private int boardIndex;
    /**
     * Allows a play if the player has clicked two valid board and hand positions
     * as well as the board being the last clicked position.
     */
    private boolean playPossible;
    /**
     * Indicates if the Click State object has a hand index stored
     */
    private boolean hasHandIndex;
    /**
     * Indicates if the Click State object has a board index stored
     */
    private boolean hasBoardIndex;
    /**
     * Keeps track of last clicked state. Prevents bugs of previous clicks influencing next player's play
     */
    private boolean lastClickedOnBoard;
    /**
     * Based on the pixel width of the cards, used to calculate the index a click is in.
     */
    int tileSize;
    /**
     * Constructor for objects of class ClickState
     */
    public ClickState()
    {
        this.handIndex = handIndex;
        this.boardIndex = boardIndex;
        this.hasHandIndex = false;
        this.hasBoardIndex = false;
        this.playPossible = false;
        this.lastClickedOnBoard = false;
        this.tileSize = 200;
    }
    /**
     * Calculates if a new click is valid for the current turn player or if it is a click to the board.
     * Sets the respective types of clicks and returns if a play is possible after the new click.
     * 
     */
    public boolean handleNewClick(Player activePlayer, Point p){
        int playerBoardLocation = activePlayer.getBoardLocation();
        int maxHand = getHandMaxIndex(activePlayer);
        int widthIndex = p.x/ 200;
        int heightIndex = p.y/ 200;
        //If it's in the active hand column and clicked on a card
        boolean validHandClick = widthIndex == playerBoardLocation && heightIndex <= maxHand;
        boolean isBoardClick = (widthIndex > 0 && widthIndex < 4) && (heightIndex > 0 && heightIndex < 4);
        if(isBoardClick){
            int boardIndex = calculateBoardIndex(widthIndex, heightIndex);
            setBoardIndex(boardIndex);
        }
        if(validHandClick){
            setHandIndex(heightIndex);
        }
        this.updatePlayPossible();
        return this.getPlayPossible();
    }
    /**
     * Calculates the one dimensional index clicked on the board.
     * The board is one dimensional for convention when drawing direction
     * of edges.
     */
    private int calculateBoardIndex(int widthIndex, int heightIndex){
        return (((heightIndex - 1) * 3) + widthIndex - 1);
    }
    /**
     * Getter for that hand index clicked on
     * @return The index of the last card clicked in the player's hand
     */
    public int getHandIndex(){
        return this.handIndex;
    }
    /**
     * Getter for the board index clicked on
     * @return The index of the last clicked spot on the board by the current player
     */
    public int getBoardIndex(){
        return this.boardIndex;
    }
    /**
     * Getter for whether or not the 3x3 board was the last thing to be clicked
     * @return a boolean indicating if board was last clicked. False if hand was clicked prior.
     */
    public boolean getLastClickedOnBoard(){
        return this.lastClickedOnBoard;
    }
    /**
     * Setter for last clicked on state
     * Set to false when setHandIndex is called
     */
    public void setLastClickedOnBoard(boolean state){
        this.lastClickedOnBoard = state;
    }
    /**
     * Setter for the handIndex attribute which keeps the last clicked state.
     * Confirms that the object has a handIndex.
     */
    private void setHandIndex(int handIndex){
        this.handIndex = handIndex;
        this.setHasHandIndex(true);
        assert(getHasHandIndex() == true);
        this.setLastClickedOnBoard(false);
    }
    /**
     * Gets the max size of a hand.
     * Used when calculating the index clicked on.
     */
    private int getHandMaxIndex(Player player){
        return player.getHand().getHandSize();
    }
    /**
     * Setter for the board index if clicked on the board.
     * Confirms that the object has a boardIndex;
     */
    public void setBoardIndex(int boardIndex){
        this.boardIndex = boardIndex;
        this.setHasBoardIndex(true);
        assert(getHasHandIndex() == true);
        this.setLastClickedOnBoard(true);
    }/**
     * Used by the play made method to set to false,
     * requiring a new player to have clicked their own spot on the first.
     */
    private void setHasBoardIndex(boolean hasBoardIndex){
        this.hasBoardIndex = hasBoardIndex;
    }
    /**
     * Checks whether or not a spot on the board has been clicked yet
     * and returns the boolean  value
     * @return True if a spot on the 3x3 board has been clicked
     */
    public boolean getHasBoardIndex(){
        return this.hasBoardIndex;
    }
    /**
     * Used by the play made method to set to false,
     * requiring a new player to have clicked their own Hand Index first.
     */
    private void setHasHandIndex(boolean hasHandIndex){
        this.hasHandIndex = hasHandIndex;
    }
    /**
     * Checks whether or not a card in the hand has been clicked yet
     * and returns the boolean value
     * @return True if the turn player has clicked their card.
     */
    public boolean getHasHandIndex(){
        return this.hasHandIndex;
    }
    /**
     * Checks if a spot on the board has been clicked and a card in hand has been clicked, then functions
     * as a setter method for the playPossible attribute.
     * Sets to True if this object knows that a card has been clicked and a board index has been clicked
     */
    public void updatePlayPossible(){
        boolean playPossible = getHasHandIndex() && getHasBoardIndex() && getLastClickedOnBoard();
        if(playPossible){
            this.playPossible = true;
        }
    }
    /**
     * Getter for the boolean attribute playPossible
     * @return True if it is possible to make a play with this object's index attributes
     */
    public boolean getPlayPossible(){
        return this.playPossible;
    }
    /**
     * Sets the state of the clicked locations to false
     * in preparation of a different player using the object to make a play.
     * This is so a play attempt won't be made with the last hand index clicked
     * if the next turn player clicks the board first.
     */
    public void playMade(){
        this.setHasBoardIndex(false);
        this.setHasHandIndex(false);
        this.updatePlayPossible();
    }
}
