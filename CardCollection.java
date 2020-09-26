import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;
import java.util.Collections;
/**
 * A Generic Collection of cards
 * modified by Keagen Thomson
 * @author Jason Miller
 * @version 1.0.1
 */
public class CardCollection
{
    private String label;
    public ArrayList<Card> cards;

    /**
     * Constructs an empty collection.
     * @param label the name of the card collection (deck, Player 1 hand, etc.)
     */
    public CardCollection(String label) {
        this.label = label;
        this.cards = new ArrayList<Card>();
    }
    public String getLabel() {
        return this.label;
    }
    /**
     * Moves a specific card from one collection to the next
     * (i.e. from the hand to the board) and specifies where it's going.
     */
    public void moveCard(CardCollection that, int position, Card card){
        that.addCard(popCard(card), position);
    }
    /**
     * Perhaps your card collection is an array or matrix and needs inserting that way?
     * 
     * 
     */
    public void moveCard(CardCollection that, Point p, Card card){
        
    }
    
    /**
     * Removes and returns the card with the given index.
     */
    public Card popCard(int i) {
        return this.cards.remove(i);
    }

    /**
     * Removes and returns the last card.
     */
    public Card popCard() {
        int i = size() - 1;
        return popCard(i);
    }
    
    /**
     * removes and returns the first card with the specific name
     * works by Overriding the equals method
     * in the future this will use card IDs so multiple of the same name can be used
     */
    public Card popCard(String name) {
        int i = this.cards.indexOf(name);
        return popCard(i);
    }
    
    /**
     * Uses the overrided equals method that takes a card object
     * This will be used when Unique IDS are added and the equals method
     * is overrided to use Card.id. for now it does the same thing as the above method
     */
    public Card popCard(Card card) {
        int i = this.cards.indexOf(card);
        return popCard(i);
    }
    
    /**
     * Returns the card with the given index. Without removing
     */
    public Card showCard(int i) {
        return this.cards.get(i);
    }
    
    /**
     * returns the first card with the specific name
     * works by Overriding the equals method
     * in the future this will use card IDs so multiple of the same name can be used
     */
    public Card showCard(String name) {
        int i = this.cards.indexOf(name);
        return this.cards.get(i);
    }
    
    /**
     * returns the first card with the specific name
     * works by Overriding the equals method
     * in the future this will use card IDs so multiple of the same name can be used
     */
    public Card showCard(Card card) {
        int i = this.cards.indexOf(card);
        return this.cards.get(i);
    }
    
    public int size(){
        return this.cards.size();
    }
    
    /**
     * Adds the given card to the collection
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    /**
     * Adds the given card to the collection at a position
     */
    public void addCard(Card card, int position)
    {
        this.cards.add(position, card);     
    }
    
    /**
     * Moves n cards from this collection to the given collection.
     */
    public void deal(CardCollection that, int n) {
        for (int i = 0; i < n; i++) {
            Card card = popCard();
            that.addCard(card);
        }
    }
    
    /**
     * Shuffles the Card Collection instance
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
    
    /**
     * Swaps the cards at indexes i and j.
     */
    public void swapCards(int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }
    
    /**
     * Returns a string representation of the card collection.
     */
    public String toString() {
        return this.label + ":" + cards.toString();
    }
    /**
     * Returns the array of cards that make the card collection
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }
}
