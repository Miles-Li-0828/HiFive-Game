package hifive;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;


import java.awt.*;
import java.util.*;
import java.util.List;
/**
 * Player class
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Kylar Khant, Ngoc Thanh Lam Nguyen
 */
public abstract class Player
{
    private Hand hand;
    private int score;
    private int id;
    private FactoryCalculator factoryCalculator = FactoryCalculator.getFactoryCalculator();
    private CompositeCalculator cc;
    private Font bigFont = HiFive.bigFont;
    private Color bgColor = new Color(0, 0, 0, 0);
    private List<String> playerAutoMovements = new ArrayList<>();
    private Actor scoreActor = null;
    private Location scoreLocation;
    private HiFive game;
    private int autoIndex = 0;
    private boolean finishAuto;
    private boolean isAuto;

    /**
     * Constructor of Player
     *
     * @param id: player id
     * @param deck: deck of cards
     */
    public Player(int id, Deck deck, Properties properties, HiFive game)
    {
        this.game = game;
        this.id = id;
        hand = new Hand(deck);
        this.score = 0;
        cc = factoryCalculator.getCompCalculator();
        isAuto = game.isAuto();
        finishAuto = !isAuto;

        // Initialize score
        String text = "[" + String.valueOf(score) + "]";
        scoreActor = new TextActor(text, Color.WHITE, bgColor, bigFont);

        // Setup auto movements
        String playerMovements = properties.getProperty("players." + id + ".cardsPlayed");
        if (playerMovements != null)
            playerAutoMovements = Arrays.asList(playerMovements.split(","));

    }

    /**
     * Getters and setters
     */
    public Hand getHand() {return hand;}
    public int getScore() {return score;}
    public int getId() {return id;}
    public Actor getScoreActor() {return scoreActor;}
    public List<String> getPlayerAutoMovements() {return playerAutoMovements;}
    public int getAutoIndex() {return autoIndex;}
    public HiFive getGame() {return game;}
    public boolean isFinishAuto() {return finishAuto;}
    public CompositeCalculator getCompositeCalculator() {return cc;}

    public void setScore(int score) {this.score = score;}
    public void setScoreActor(Actor scoreActor) {this.scoreActor = scoreActor;}
    public void setAutoIndex(int autoIndex) {this.autoIndex = autoIndex;}
    public void setHand(Hand hand) {this.hand = hand;}
    public void setFinishAuto(boolean finishAuto) {this.finishAuto = finishAuto;}

    /**
     * Calculate score for player
     * ------Need to modify after WildCard is implemented------
     *
     * @return score
     */
    public void calculateScore()
    {
        score = cc.calculateScore(this.hand.getCardList());
    }

    /** Play the game based on the mode
     */
    public abstract Card play();

    /** Discard a card */
    public abstract Card discardCard();

    /**
     * Initialise graphics for player
     */
    public void initGraphics()
    {
        // Init the score graphics
        scoreLocation = game.getScoreLocations()[id];
        game.addActor(scoreActor, scoreLocation);
    }

    /**
     * Update the score of the player
     */
    public void updateScore()
    {
        game.removeActor(scoreActor);
        int displayScore = Math.max(score, 0);
        String text = "P" + id + "[" + displayScore + "]";
        scoreActor = new TextActor(text, Color.WHITE, bgColor, bigFont);
        game.addActor(scoreActor, scoreLocation);
    }

    /**
     * Apply auto movement for the player
     *
     * @param nextMovement: next movement
     * @param hand: a hand of cards
     * @return the card to discard
     */
    protected Card applyAutoMovement(Hand hand, String nextMovement)
    {
        String[] cardStrings = nextMovement.split("-");
        String cardDealtString = cardStrings[0];

        if (game.getPack().isEmpty()) return null;
        Card dealt = CardBase.getCardFromList(game.getPack().getCardList(), cardDealtString);
        if (dealt != null)
        {
            dealt.removeFromHand(false);
            hand.insert(dealt, true);
        }
        else
        {
            System.out.println("cannot draw card: " + cardDealtString + " - hand: " + hand);
        }

        if (cardStrings.length > 1)
        {
            String cardDiscardString = cardStrings[1];
            return CardBase.getCardFromList(hand.getCardList(), cardDiscardString);
        }
        else
        {
            return null;
        }
    }

    /**
     * Play the game automatically by the auto movements
     *
     * @return the card to discard
     */
    public Card autoPlay()
    {
        Card selected = null;
        int playerAutoIndex = autoIndex;
        List<String> playerMovements = playerAutoMovements;
        String nextMovement = "";

        if (playerMovements.size() > playerAutoIndex) // Auto movement
        {
            nextMovement = playerMovements.get(playerAutoIndex);
            playerAutoIndex++;

            autoIndex = playerAutoIndex;

            game.setStatusText("Player " + id + " thinking...");
            selected = applyAutoMovement(hand, nextMovement);
            GameGrid.delay(getGame().getDelayTime());
            if (selected != null)
            {
                selected.removeFromHand(true);
            }
            else // The main logic of Random Computer Player
            {
                selected = play();
            }
        }
        return selected;
    }
}
