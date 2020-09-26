import java.util.Arrays;
import java.util.ArrayList;

import java.awt.*;

import javax.swing.ImageIcon;

import java.util.HashMap;
/**
 * Handles drawing to the canvas
 * visually placing cards etc.
 * Represents the model of the board on the screen.
 * Objects are drawn by [][] array convention with indices
 * starting from 0. The size of the screen and basis for calculations
 * depends on the scaled width of the packaged card files. by default this is 200px by 200px
 * making the view a 5 by 5 tiled representation where each tile is 200px by 200px
 *
 * @author Keagen Thomson
 * @version 1.0.0
 */
public class TripleTriadView extends Canvas
{
    /**
     * The size of the view's square tiles calculated the scaled card images. used
     * for all draw placement calculations
     */
    private int tileSize;
    /**
     * The model the view references objects from to draw
     */
    private TripleTriadModel tripleTriadModel;
    /**
     * A dictionary of monster card images referenced by their name
     */
    HashMap<String, Image> faceImages;
    /**
     * A dictionary of monster card rank images (1 - 10/A) referenced by their name
     */
    HashMap<String, Image> rankImages;
    /**
     * A dictionary of the colors drawn behind a card referenced by their name
     */
    HashMap<String, Image> backgroundImages;
    /**
     * An image representing the 3x3 triple triad board
     */
    Image boardImage;
    /**
     * An array of the two players interacting with the model, used to draw the hands to the screen
     */
    Player[] players;
    /**
     * The board that holds object state (played cards) to be drawn on the screen
     */
    Board board;
    /**
     * A light peachy color coordinating with the board image
     */
    Color backgroundColor = new Color(255, 222, 212);
    /**
     * A string array of all possible filename prefixes for a card's ranks
     */
    final String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    /**
     * A string array of all possible filename prefixes for a card's color
     */
    final String[] cardBG = {"Blue", "Red", "Neutral"};
    /**
     * A string array of all possible filename prefixes for a card's name
     */
    final String[] cardNames ={"Bomb", "Cactuar", "Fat Chocobo", "Flan", "Ifrit", "Imp", "Iron Giant",
        "Lamia", "Lich", "Magic Pot", "Tiamat", "Tonberry"};
    

    /**
     * Constructor of the triple triad object that takes the model, and loads images to represent the model.
     * Sets the window size to 5x5 the square tile size which by default is 1000x1000
     */
    public TripleTriadView(TripleTriadModel tripleTriadModel)
    {
        this.tripleTriadModel = tripleTriadModel;
        this.faceImages = new HashMap<String, Image>();
        this.backgroundImages = new HashMap<String, Image>();
        this.rankImages = new HashMap<String, Image>();
        this.boardImage = boardImage;
        this.players = tripleTriadModel.getPlayers();
        this.board = tripleTriadModel.getBoard();
        loadImages();
        tileSize = this.backgroundImages.get("Blue").getWidth(null);
        this.setBackground(backgroundColor);
        setWindowSize(5, 5);
    }
    /**
     * Loads all the color background files as images into a mapped dictionary by their prefix
     */
    public void loadCardBackgroundImages(){
        for(String cardBG: cardBG){
            String fileName = "Assets/"+cardBG+"CardBG.png";
            Image cardBGImageFile = new ImageIcon(fileName).getImage();
            this.backgroundImages.put(cardBG, cardBGImageFile);
        }
        
    }
     /**
     * Loads all the monster card faces files as images into a mapped dictionary by their prefix
     */
    public void loadCardFaceImages(){
        for(String cardName: cardNames){
            
            String fileName = "Assets/"+cardName+".png";
            Image cardNameImageFile = new ImageIcon(fileName).getImage();
            this.faceImages.put(cardName, cardNameImageFile);
        }
        
    }
    /**
     * Loads all the card ranks files as images into a mapped dictionary by their prefix
     */
    public void loadCardRankImages(){
        for(String rankName: ranks){
            String fileName = "Assets/"+rankName+".png";
            Image rankNameImageFile = new ImageIcon(fileName).getImage();
            this.rankImages.put(rankName, rankNameImageFile);
        }
        
    }
    /**
     * Loads the 3x3 board by its full file name. the board by default is 3x3 the unscaled dimensions of a given card
     */
    public void loadBoardImage(){
        String fileName = "Assets/Triple Triad Board.png";
        this.boardImage = new ImageIcon(fileName).getImage();
        
    }
    /**
     * Calls all loading methods in a wrapper.
     * Used to load images in the constructor so they can be drawn to the canvas
     */
    public void loadImages(){
        loadBoardImage();
        loadCardRankImages();
        loadCardFaceImages();
        loadCardBackgroundImages();
    }
    /**
     * Draws the board to the middle of the screen
     */
    public void drawBoard(Graphics g){
        g.drawImage(this.boardImage, 
        (int) (1 * this.tileSize),
        (int) (1 * this.tileSize),
        null);
        
    }
    /**
     * Draws the cards played to the board at the appropriate
     * index representation which is calculated by another method
     * and depends on the width (in tiles or "indexes") of the board.
     */
    public void drawCardsOnBoard(Graphics g){
        for(Node node: this.board.getPlayedCards()){
            boolean hasACard = node.getHasCard();
            if(hasACard){
                Card cardToDraw = node.getCard();
                Point p = this.calculatePoint(node.getIndex());
                drawCard(g, cardToDraw, p.x, p.y);
            }
        }
    }
    /**
     * Calculates and returns the row column[][] representation of 
     * the board's 1 dimensional index
     */
    public Point calculatePoint(int boardIndex){
        int indexesWide = this.board.getWidth();
        Point p = new Point(0,0);
        p.x = (boardIndex % indexesWide) + 1;
        p.y = (boardIndex / indexesWide) + 1;
        return p;
    }
    /**
     * Draws the current score on the bottom row in the column adjacent to the respective player
     */
    public void drawScore(Graphics g){
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
        g.setColor(Color.BLACK);
        int[] scores = this.tripleTriadModel.getScores();
        String Player1score = ""+scores[0];
        String Player2score = ""+scores[1];
        g.drawString(Player1score,
        (int) (3 * this.tileSize),
        (int) (5 * this.tileSize));
        g.drawString(Player2score,
        (int) (1 * this.tileSize),
        (int) (5 * this.tileSize));
        
    }
    /**
     * If the game isn't complete it draws the current players turn in the top row middle column.
     * Otherwise it draws who won the game
     */
    public void drawCurrentTurn(Graphics g){
        if(!this.tripleTriadModel.getGameFinished()){
            g.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
            g.setColor(Color.BLACK);
            String currentPlayer = this.tripleTriadModel.getActivePlayer().getOwner();
            g.drawString(currentPlayer,
            (int) (2 * this.tileSize),
            (int) (0 * this.tileSize) + 60);
        } else{
            g.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
            g.setColor(Color.BLACK);
            String currentWinner = this.tripleTriadModel.getCurrentWinner();
            g.drawString(currentWinner + " Won",
            (int) (2 * this.tileSize),
            (int) (0 * this.tileSize) + 60);
        }
    }
    /**
     * Draws a card's face at a [][] view index based on its name
     * @param widthIndex also known as the row but starts from 0 as it is an index
     * @param heightIndex also known as the column but starts from 0 as it is an index
     */ 
    public void drawCardFace(Graphics g,Card card, int widthIndex, int heightIndex){
        String cardName = card.getName();
        Image cardFace = this.faceImages.get(cardName);
        g.drawImage(cardFace,
            (int) (widthIndex * this.tileSize),
            (int) (heightIndex * this.tileSize),
            null);
        
    }
    /**
     * Draws the card's background: Red, Blue, or Neutral(Purple) at a row and column
     */
    public void drawCardColorBackground(Graphics g, Card card, int widthIndex, int heightIndex){
        String cardColor = card.getColor();
        Image cardColorBG = this.backgroundImages.get(cardColor);
        g.drawImage(cardColorBG,
            (int) (widthIndex * this.tileSize),
            (int) (heightIndex * this.tileSize),
            null);
    }
    /**
     * Draws the ranks for a card on all 4 sides
     */
    public void drawCardRanks(Graphics g, Card card, int widthIndex, int heightIndex){

        for(int i = 0; i < 4; i++){
            drawRank(g, card, widthIndex, heightIndex, i);
        }
        
    }
    /**
     * Draws the ranks by calculating the midpoint of the card's current position and 
     * offsetting it by 85% of the card's width in the appropriate direction
     * @param side is the direction a rank is supposed to be drawn. Should be 0-3 representing
     * the four sides in clockwise order starting from the top (0)
     */
    public void drawRank(Graphics g, Card card, int widthIndex, int heightIndex, int side){
        int rank = card.getRanks().get(side);
        int midPointX;
        int midPointY;
        Image rankImage = this.rankImages.get(""+rank);
        int rankImageWidth = rankImage.getWidth(null);
        int rankImageHeight = rankImage.getHeight(null);
        //adds the index to the rank's midpoint calculation
        midPointX = (widthIndex * this.tileSize) + ((this.tileSize - rankImageWidth)/2);
        midPointY = (heightIndex * this.tileSize) + ((this.tileSize - rankImageHeight)/2);
        //Offset is 85% a card's midpoint;
        int offset = (85*(this.tileSize/2))/100; 
        //Top
        if(side == 0){
            g.drawImage(rankImage, midPointX, (midPointY - offset), null);
        }
        //Right
        if(side == 1){
            g.drawImage(rankImage, (midPointX + offset), midPointY, null);
        }
        //Bottom
        if(side == 2){
            g.drawImage(rankImage, midPointX , (midPointY + offset), null);
        }
        //Left
        if(side == 3){
            g.drawImage(rankImage, (midPointX - offset), midPointY, null);
        }
    }
    /**
     * Draws a layered card placing the card background below the card's face
     * and the cards ranks above its face.
     * Graphics, Card, X, Y
     */
    public void drawCard(Graphics g, Card card, int widthIndex, int heightIndex){
        drawCardColorBackground(g,card,widthIndex,heightIndex);
        drawCardFace(g,card,widthIndex,heightIndex);
        drawCardRanks(g, card, widthIndex, heightIndex);
        
    }
    /**
     * Calls the draw hand method for both players
     */
    public void drawHands(Graphics g){
        for(Player player: tripleTriadModel.getPlayers()){
            int handWidth = player.getBoardLocation();
            Hand hand = player.getHand();
            drawHand(g, hand, handWidth);
        }
    }
    /**
     * Draws a hand by staying in a column then incrementing its heightIndex by one and drawing the next card
     */
    public void drawHand(Graphics g, Hand hand, int widthIndex){
        int heightIndex = 0;
        for(Card card: hand.getCards()){
            drawCard(g, card, widthIndex, heightIndex);
        
            heightIndex += 1;
        }
    }
    /**
     * calls all draw methods and paints them to the canvasa
     */
    public void paint (Graphics g){
        drawHands(g);
        drawBoard(g);
        drawCardsOnBoard(g);
        drawScore(g);
        drawCurrentTurn(g);
    }
    /**
     * Sets the window size to X by Y based on the scaled width (200px default) of the packaged cards.
     */
    public void setWindowSize(int tilesWide, int tilesHigh){
        setSize((int) (tilesWide * this.tileSize), (int) (tilesHigh * this.tileSize));
    }
    
}
