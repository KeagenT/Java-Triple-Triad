import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
/**
 * This is just a hardcoded collection of cards
 * hands are generated from
 * For added fun, card ranks are randomly generated
 */
public class HiddenCardPool extends CardCollection
{
    /**
     * Names of monsters from Final Fantasy used to create these cards
     */
    private final String[] cardNames ={"Bomb", "Cactuar", "Fat Chocobo", "Flan", "Ifrit", "Imp", "Iron Giant",
        "Lamia", "Lich", "Magic Pot", "Tiamat", "Tonberry"};
    /**
     * Creates a hidden deck that players draw from to create their hands.
     */
    public HiddenCardPool(String label)
    {
        super(label);
        generateCardPool();
    }
    /**
     * Creates a card pool from one of every type of monster "card name".
     * Some cards are arbitrarily generated with more "high sides" than others.
     * Works by iterating through card names, generating a card with that name and adding it to the hiddenCardPool,
     * then shuffles it for when hands are dealt from this card pool.
     */
    public void generateCardPool(){
        for (int i = 0; i < this.cardNames.length; i++){
            String currentCard = this.cardNames[i];
            boolean bossMonster = i >= 4 && i <= 10 && i%2 == 0;
            if(bossMonster){
                Card card = new Card(currentCard, generateCardRanks(3), "Neutral");
                this.addCard(card);
            }else {
                Card card = new Card(currentCard, generateCardRanks(randomHighRankSides()),
                "Neutral");
                this.addCard(card);
            }
        }
        this.shuffle();
    }
    /**
     * Randomly generates 4 card ranks then shuffles them to distribute high rank edges
     * @param highSides the amount of High rank sides of 4 to be generated (rank between 6-10)
     * remaining sides are low ranks (rank between 1-5)
     */
    public ArrayList<Integer> generateCardRanks(int highSides)
    {
        int lowSides = 4 - highSides;
        ArrayList<Integer> ranks = new ArrayList<Integer>();
        for(int i = 0; i< highSides; i++){
            ranks.add(generateHighRank());
        }
        for(int i = 0; i< lowSides; i++){
            ranks.add(generateLowRank());
        }
        Collections.shuffle(ranks);
        return ranks;
    }
    /**
     * Generates a rank integer from 6 - 10
     */
    public int generateHighRank(){
        int randHigh = generateRandomIntegerInRange(6, 10);
        //System.out.println(randHigh);
        return randHigh;
    }
    /**
     * Generates a rank integer from 1 - 5
     */
    public int generateLowRank(){
        return generateRandomIntegerInRange(1, 5);
    }
    /**
     * Values are inclusive on both sides of the range
     */
    private int generateRandomIntegerInRange(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    /**
     * Generates a random amount of high rank sides
     */
    private int randomHighRankSides(){
        return generateRandomIntegerInRange(1, 4);
    }

}
