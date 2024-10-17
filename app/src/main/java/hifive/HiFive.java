package hifive;// LuckyThirteen.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Game of Hi Five
 *
 * @version 2.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
@SuppressWarnings("serial")
public class HiFive extends CardGame
{
    // System settings
    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};
    static public final int seed = 30008;
    static public final Random random = new Random(seed);
    private Properties properties;
    private StringBuilder logResult = new StringBuilder();


    // Graphic settings
    private final String version = "1.0";
    private final int handWidth = 400;
    private final int trickWidth = 40;
    public static final int FIVE_GOAL = 5;
    public static final int FIVE_POINTS = 100;
    public static final int SUM_FIVE_POINTS = 60;
    public static final int DIFFERENCE_FIVE_POINTS = 20;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
    public static List<Card> discardedCards = new ArrayList<>();
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            // new Location(650, 575)
            new Location(575, 575)
    };

    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private int thinkingTime = 2000;
    private int delayTime = 600;


    public static final int nbPlayers = 4;
    public final int nbStartCards = 2;
    public final int nbFaceUpCards = 2;


    private Player[] players = new Player[nbPlayers];
    private PlayerFactory playerBuilder;
    private Hand playingArea;
    private Hand pack;
    private boolean isAuto;

    public static Font bigFont = new Font("Arial", Font.BOLD, 36);

    /**
     * Constructor of HiFive
     * @param properties: properties
     */
    public HiFive(Properties properties)
    {
        super(700, 700, 30);
        this.properties = properties;
        playerBuilder = new PlayerFactory(properties);
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime", "200"));
        delayTime = Integer.parseInt(properties.getProperty("delayTime", "50"));
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
    }

    /** Getters and setters */
    public Location[] getScoreLocations() {return scoreLocations;}
    public Hand getPack() {return pack;}
    public int getDelayTime() {return delayTime;}
    public boolean isAuto() {return isAuto;}
    public int getThinkingTime() {return thinkingTime;}

    /**
     * Initialise Players
     */
    private void initPlayers()
    {
        for (int i = 0; i < nbPlayers; i++)
        {
            players[i] = playerBuilder.createPlayer(properties.getProperty("players." + i), i, deck, this);
            players[i].initGraphics();
        }
    }

    /**
     * update players' score
     */
    private void calculateScoreEndOfRound()
    {
        for (int i = 0; i < players.length; i++)
        {
            players[i].updateScore();
        }
    }

    /**
     * Initialise the game
     */
    private void initGame()
    {
        // Initialise players
        initPlayers();

        // Leave this line here
        playingArea = new Hand(deck);
        dealingOut(players, nbPlayers, nbStartCards, nbFaceUpCards);
        playingArea.setView(this, new RowLayout(trickLocation, (playingArea.getNumberOfCards() + 2) * trickWidth));
        playingArea.draw();

        for (int i = 0; i < nbPlayers; i++)
        {
            players[i].getHand().sort(Hand.SortType.SUITPRIORITY, false);
        }
        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++)
        {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);
            players[i].getHand().setView(this, layouts[i]);
            players[i].getHand().setTargetArea(new TargetArea(trickLocation));
            players[i].getHand().draw();
        }
    }


    /**
     * return random Enum value
     */

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz)
    {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * get a random card for a hand of card
     * @param hand: a hand of card
     * @return Card: a random card from hand
     */

    public Card getRandomCard(Hand hand)
    {
        dealACardToHand(hand);

        delay(thinkingTime);

        int x = random.nextInt(hand.getCardList().size());
        return hand.getCardList().get(x);
    }

    /**
     * Deal cards to hand
     *
     * @param players: players
     * @param nbPlayers: number of players
     * @param nbCardsPerPlayer: number of cards per player
     * @param nbSharedCards: number of shared cards faced up
     */
    private void dealingOut(Player[] players, int nbPlayers, int nbCardsPerPlayer, int nbSharedCards)
    {
        pack = deck.toHand(false);

        for (int i = 0; i < nbPlayers; i++)
        {
            String initialCardsKey = "players." + i + ".initialcards";
            String initialCardsValue = properties.getProperty(initialCardsKey);
            if (initialCardsValue == null)
            {
                continue;
            }
            String[] initialCards = initialCardsValue.split(",");
            for (String initialCard : initialCards)
            {
                if (initialCard.length() <= 1)
                {
                    continue;
                }
                Card card = CardUtils.getCardFromList(pack.getCardList(), initialCard);

                if (card != null)
                {
                    card.removeFromHand(false);
                    players[i].getHand().insert(card, false);
                }
            }
        }

        for (int i = 0; i < nbPlayers; i++)
        {
            int cardsToDealt = nbCardsPerPlayer - players[i].getHand().getNumberOfCards();
            for (int j = 0; j < cardsToDealt; j++)
            {
                if (pack.isEmpty()) return;
                Card dealt = CardUtils.randomCard(pack.getCardList());
                dealt.removeFromHand(false);
                players[i].getHand().insert(dealt, false);
            }
        }
    }

    /**
     * Dealt a random card to hand
     *
     * @param hand: a hand of card
     */

    public void dealACardToHand(Hand hand)
    {
        if (pack.isEmpty()) return;
        Card dealt = CardUtils.randomCard(pack.getCardList());
        dealt.removeFromHand(true);
        hand.insert(dealt, true);
    }

    /**
     * Logs the cards played by a specific player during a turn
     *
     * @param player: the player whose cards are being logged
     * @param cards: a list of Card that were played by the player
     */
    private void addCardPlayedToLog(int player, List<Card> cards)
    {
        if (cards.size() < 2)
        {
            return;
        }
        logResult.append("P" + player + "-");

        for (int i = 0; i < cards.size(); i++)
        {
            Rank cardRank = (Rank) cards.get(i).getRank();
            Suit cardSuit = (Suit) cards.get(i).getSuit();
            logResult.append(cardRank.getRankCardLog() + cardSuit.getSuitShortHand());
            if (i < cards.size() - 1)
            {
                logResult.append("-");
            }
        }
        logResult.append(",");
    }

    private void addRoundInfoToLog(int roundNumber)
    {
        logResult.append("Round" + roundNumber + ":");
    }

    /**
     * Logs the current round number in the game
     */
    private void addEndOfRoundToLog()
    {
        logResult.append("Score:");
        for (int i = 0; i < players.length; i++)
        {
            logResult.append(players[i].getScore() + ",");
        }
        logResult.append("\n");
    }

    /**
     * Logs the scores of all players at the end of a round
     */
    private void addEndOfGameToLog(List<Integer> winners)
    {
        logResult.append("EndGame:");
        for (int i = 0; i < players.length; i++)
        {
            logResult.append(players[i].getScore() + ",");
        }
        logResult.append("\n");
        logResult.append("Winners:" + String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList())));
    }

    /**
     * The engine of the game
     */
    private void playGame()
    {
        // End trump suit
        int roundNumber = 1;
        for (int i = 0; i < nbPlayers; i++) players[i].updateScore();

        List<Card> cardsPlayed = new ArrayList<>();
        addRoundInfoToLog(roundNumber);

        int nextPlayerID = 0;
        while (roundNumber <= 4)
        {
            Card selected = null;

            selected = players[nextPlayerID].discardCard();
            players[nextPlayerID].calculateScore();

            addCardPlayedToLog(nextPlayerID, players[nextPlayerID].getHand().getCardList());
            if (selected != null)
            {
                cardsPlayed.add(selected);
                selected.setVerso(false);  // In case it is upside down
                delay(delayTime);
                // End Follow
            }

            players[nextPlayerID].updateScore();
            nextPlayerID = (nextPlayerID + 1) % nbPlayers;

            if (nextPlayerID == 0)
            {
                roundNumber++;
                addEndOfRoundToLog();

                if (roundNumber <= 4)
                {
                    addRoundInfoToLog(roundNumber);
                }
            }

            if (roundNumber > 4)
            {
                calculateScoreEndOfRound();
            }
            delay(delayTime);
        }
    }

    public String runApp()
    {
        setTitle("LuckyThirteen (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initGame();
        playGame();

        for (int i = 0; i < nbPlayers; i++) players[i].updateScore();
        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (players[i].getScore() > maxScore) maxScore = players[i].getScore();
        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < nbPlayers; i++) if (players[i].getScore() == maxScore) winners.add(players[i].getId());
        String winText;
        if (winners.size() == 1)
        {
            winText = "Game over. Winner is player: " +
                    winners.iterator().next();
        }
        else
        {
            winText = "Game Over. Drawn winners are players: " +
                    String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList()));
        }

        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatusText(winText);
        refresh();
        addEndOfGameToLog(winners);

        return logResult.toString();
    }

    public void setStatus(String string)
    {
        setStatusText(string);
    }
}
