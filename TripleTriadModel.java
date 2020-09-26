import java.util.Random;
import java.awt.*;
/**
 * Keeps track of the state of the game.
 * Keeps track of all objects used to play and their attributes such as:
 * The players scores, the players themselves, and the 3x3 Board which
 * contains cards played by the player.
 * The game is drawn from this model.
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class TripleTriadModel
{   
    /**
     * The player's of the game. In a standard unmodified game
     * this is two players Respectively "Player 1 and Player 2"
     */
    public Player[] players;
    /**
     * The 3x3 Board that player's play cards on and attempt to capture other cards on.
     * The board can hold up to 9 cards and knows which cards are adjacent to eachother
     */
    public Board board;
    /**
     * Scores is the scores of both players stored in 
     * an array where the 0th index is Player 1's Score and the 1st is Player 2's.
     */
    public int[] scores;
    /**
     * Indicates if the game is finished based on the amount of cards that have
     * been played to the board.
     */
    public boolean gameFinished;
    /**
     * Calculated based on score.
     * Can be "Player 1 ", "Player 2 " or "Draw, nobody "
     */
    public String currentWinner;
    /**
     * An object that calculates if the clicks on a board are within the active player's hand
     * and on a valid spot on the board. This is used when playing a card from the hand
     * to the board by clicking both spots.
     */
    ClickState clickstate;
    

    /**
     * Constructs the model representing the state of the game including the players and their relative attributes,
     * the board and its played cards, whose currently winning, and if the game is finished already. Contains
     * methods to update the board's state after ever turn
     */
    public TripleTriadModel()
    {
        this.players = players;
        this.board = board;
        this.scores = scores;
        this.currentWinner = currentWinner;
        this.gameFinished = false;
        buildTripleTriad();
        updateGameState();
        randomizeFirstTurn();
        clickstate = new ClickState();
       
    }
    /**
     * Getter for the board which contains
     * all cards played and what spots a card played is adjacent to
     */
    public Board getBoard(){
        return this.board;
    }
    /**
     * Getter for the player array
     * which should contain two players
     */
    public Player[] getPlayers(){
        return this.players;
    }
    /**
     * Setter for the player array
     * which should contain two players
     */
    private void setPlayers(Player[] players){
        this.players = players;
    }
    /**
     * Getter for an individual player at index
     * @param i Can be either 0 or 1 for Player 1 and Player 2;
     */
    private Player getPlayer(int i){
        return getPlayers()[i];
    }
    /**
     * Setter for individual player at index
     * Used when updating an attribute of a specific player
     * and it is necessary to replace a Player.
     *
     */
    private void setPlayer(Player player, int i){
        this.players[i] = player;
    }
    /**
     * Returns the player object whose turn attribute is true.
     * Since only one player can have a true turn attribute, that
     * player is the one with an active turn.
     */
    public Player getActivePlayer(){
        Player activePlayer = getPlayer(1);
        for(Player player: this.getPlayers()){
            if(player.getTurn()){
                activePlayer = player;
                return activePlayer;
            }
        }
        return activePlayer;
    }
    /**
     * Randomly Picks who plays first by toggling the player object turn attribute to true.
     * Then sets their position on the board based on whether they go first or second.
     */
    private void randomizeFirstTurn(){
        Random r = new Random();
        //Generates a random integer 1 or 2
        int firstPlayer = r.nextInt(2) + 1;
        for(Player player: this.getPlayers()){
            if(player.getOwner().contains(""+firstPlayer)){
                player.setTurn(true);
            }
        }
    }
    /**
     * Toggles the active player turn. Meant to be used after a play is successfully made
     */
    public void toggleActivePlayer(){
        for(Player player: this.getPlayers()){
            player.toggleTurn();
        }
    }
    /**
     * Shows the players turns
     */
    public void printTurns(){
        for(Player player: this.getPlayers()){
            System.out.println(player.getOwner()+"'s turn: "+player.getTurn()+ " ");
        }
    }
    /**
     * Assigns players to the player array.
     * Used when the game is generated normally.
     */
    private void buildPlayers(){
        Player player1 = new Player("Player 1");
        player1.setBoardLocation(4);
        Player player2 = new Player("Player 2");
        player2.setBoardLocation(0);
        Player[] players = {player1, player2};
        this.setPlayers(players);
    }
    /**
     * Creates a standard board size of 3x3 and sets the game's board to that size
     */
    private void buildStandardBoard(){
        Board standardGame = new Board(3, 3);
        this.board = standardGame;
    }
    /**
     * Instantiates scores AND updates scores. called after a play before repaint.
     */
    public void updateScores(){
        int[] scores = new int[2];
        scores[0] = this.board.getPlayedCardTotal("Player 1") + this.getPlayer(0).getCardsInHand();
        scores[1] = this.board.getPlayedCardTotal("Player 2") + this.getPlayer(1).getCardsInHand();
        this.scores = scores;
    }
    /**
     * Checks the player score and sets the currentWinner
     * attribute based on which is higher. this is called after every play
     * and used when the game is complete.
     * can possibly be used before the game is complete later.
     */
    public void updateCurrentWinner(){
        int player1Score = getScores()[0];
        int player2Score = getScores()[1];
        if(player1Score > player2Score){
            this.currentWinner = "Player 1";
        }
        if(player2Score > player1Score){
            this.currentWinner = "Player 2";
        }
        if(player1Score == player2Score){
            this.currentWinner = "Draw, Nobody";
        }
    }
    /**
     * Checks every node in the board and if any node doesn't have a card
     * then the game isn't finished. Updates after every play
     */
    public void updateGameFinished(){
        boolean gameFinished = true;
        for(Node node: this.board.getPlayedCards()){
            if(!node.getHasCard()){
                gameFinished = false;
            }
        }
        this.gameFinished = gameFinished;
    }
    /**
     * Wrapper for all the play sensitive updater methods to be called after the board's state has changed.
     */
    public void updateGameState(){
        this.updateScores();
        this.updateCurrentWinner();
        this.updateGameFinished();
    }
    
    /**
     * Getter for the scores array where the 0th index is player 1's score and the 1st is player 2
     * @return The array of scores for each player which is updated after every play
     */
    public int[] getScores(){
        return this.scores;
    }
    /**
     * Getter for the game finished boolean.
     * Remains false unless all possible cards from both hands have been played.
     * (Calculated by checking if every board square has a card)
     * Used to trigger a game over message.
     */
    public boolean getGameFinished(){
        return this.gameFinished;
    }
    /**
     * Getter for the current winner String
     * Calculated based on score, and can either be Player 1, Player 2, or Draw, Nobody.
     * Updated by its updater function after every play
     */
    public String getCurrentWinner(){
        return this.currentWinner;
    }
    /**
     * Builds the game and randomly selects the first Player
     */
    private void buildTripleTriad(){
        this.buildStandardBoard();
        this.buildPlayers();
        this.randomizeFirstTurn();
    }
    /**
     * This method takes a mouse click location
     * and tests (abstractly with the clickState class) if
     * it is possible to make a valid play with the click.
     */
    public boolean attemptPlay(Point p){
        Player player = this.getActivePlayer();
        this.clickstate.handleNewClick(player, p); 
        boolean playPossible = false;
        if(this.clickstate.getPlayPossible()){
            int validBoardIndex =  this.clickstate.getBoardIndex();
            int validHandIndex = this.clickstate.getHandIndex();
            if(player.attemptPlayCard(validBoardIndex, this.board)){
                /**
                 * Takes hand index before board index
                 */
                playPossible = true;
            }
        }
        return playPossible;
    }
    /**
     * This method makes a play after a click using the attemptPlay method to determine
     * when this method should be called. It also updates the model after a play is successful
     * with information changed by that play such as score or the current winner and whether or not
     * the game is complete
     */
    public boolean play(Point p){
        Player player = this.getActivePlayer();
        this.clickstate.handleNewClick(player, p);
        boolean playMade = false;
        if(this.clickstate.getPlayPossible()){
            int validBoardIndex =  this.clickstate.getBoardIndex();
            int validHandIndex = this.clickstate.getHandIndex();
           
            /**
            * Takes hand index before board index
            */
            player.playCard(validHandIndex, validBoardIndex, board);
            playMade = true;
            this.clickstate.playMade();
        }
        return playMade;
    }
}
