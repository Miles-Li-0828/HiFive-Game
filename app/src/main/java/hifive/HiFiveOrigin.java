package hifive;// LuckyThirteen.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class HiFiveOrigin extends CardGame
{
    final String trumpImage[] = {"bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif"};

    static public final int seed = 30008;
    static final Random random = new Random(seed);
    private Properties properties;
    private StringBuilder logResult = new StringBuilder();
    private List<List<String>> playerAutoMovements = new ArrayList<>();

    public boolean rankGreater(Card card1, Card card2)
    {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }

    private final String version = "1.0";
    public static final int nbPlayers = 4;
    public final int nbStartCards = 2;
    public final int nbFaceUpCards = 2;
    private final int handWidth = 400;
    private final int trickWidth = 40;
    public static final int FIVE_GOAL = 5;
    public static final int FIVE_POINTS = 100;
    public static final int SUM_FIVE_POINTS = 60;
    public static final int DIFFERENCE_FIVE_POINTS = 20;
    private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
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
    private Actor[] scoreActors = {null, null, null, null};
    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private int thinkingTime = 2000;
    private int delayTime = 600;
    private Hand[] hands;

    public void setStatus(String string)
    {
        setStatusText(string);
    }

    private int[] scores = new int[nbPlayers];

    private int[] autoIndexHands = new int[nbPlayers];
    private boolean isAuto = false;
    private Hand playingArea;
    private Hand pack;

    public static Font bigFont = new Font("Arial", Font.BOLD, 36);

    //----------------------------------------------------------------------------//
    // should be in player
    private void initScore()
    {
        for (int i = 0; i < nbPlayers; i++)
        {
            // scores[i] = 0;
            String text = "[" + String.valueOf(scores[i]) + "]";
            scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
            addActor(scoreActors[i], scoreLocations[i]);
        }
    }

    // should be in player
    private int findIndexWithHigestScore(int[] scoreArray)
    {
        int maxScore = -1;
        int maxScoreIndex = 0;
        for (int i = 0; i < scoreArray.length; i++)
        {
            if (maxScore < scoreArray[i])
            {
                maxScoreIndex = i;
                maxScore = scoreArray[i];
            }
        }

        return maxScoreIndex;
    }

    // should be in player
    private void calculateScoreEndOfRound()
    {
        List<Integer> isThirteenChecks = Arrays.asList(0, 0, 0, 0);
        for (int i = 0; i < hands.length; i++)
        {
            scores[i] = scoreForHiFive(i);
        }
    }

    // should be in player
    private void updateScore(int player)
    {
        removeActor(scoreActors[player]);
        int displayScore = Math.max(scores[player], 0);
        String text = "P" + player + "[" + displayScore + "]";
        scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }

    // should be in player
    private void initScores()
    {
        Arrays.fill(scores, 0);
    }

    // should be in player
    private int scoreForHiFive(int playerIndex)
    {
        List<Card> privateCards = hands[playerIndex].getCardList();
        int score1 = new FiveCalculator().calculateScore(privateCards);
        int score2 = new SumFiveCalculator().calculateScore(privateCards);
        int score3 = new DifferenceFiveCalculator().calculateScore(privateCards);
        int score4 = new NoFiveCalculator().calculateScore(privateCards);
        int[] scores = {score1, score2, score3, score4};
        int finalScore = scores[findIndexWithHigestScore(scores)];
        return finalScore;
    }
    //----------------------------------------------------------------------------//
    private Card selected;

    public static Card randomCard(ArrayList<Card> list)
    {
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    public static Card getCardFromList(List<Card> cards, String cardName)
    {
        Rank cardRank = Rank.getRankFromString(cardName);
        Suit cardSuit = Suit.getSuitFromString(cardName);
        for (Card card : cards)
        {
            if (card.getSuit() == cardSuit
                    && card.getRank() == cardRank)
            {
                return card;
            }
        }

        return null;
    }

    private void initGame()
    {
        hands = new Hand[nbPlayers];
        for (int i = 0; i < nbPlayers; i++)
        {
            hands[i] = new Hand(deck);
        }
        playingArea = new Hand(deck);
        dealingOut(hands, nbPlayers, nbStartCards, nbFaceUpCards);
        playingArea.setView(this, new RowLayout(trickLocation, (playingArea.getNumberOfCards() + 2) * trickWidth));
        playingArea.draw();

        for (int i = 0; i < nbPlayers; i++)
        {
            hands[i].sort(Hand.SortType.SUITPRIORITY, false);
        }
        // Set up human player for interaction
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card)
            {
                selected = card;
                hands[0].setTouchEnabled(false);
            }
        };
        hands[0].addCardListener(cardListener);
        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++)
        {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);
            hands[i].setView(this, layouts[i]);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
    }


    // return random Enum value
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz)
    {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }


    public Card getRandomCard(Hand hand)
    {
        dealACardToHand(hand);

        delay(thinkingTime);

        int x = random.nextInt(hand.getCardList().size());
        return hand.getCardList().get(x);
    }

    private Card applyAutoMovement(Hand hand, String nextMovement)
    {
        if (pack.isEmpty()) return null;
        String[] cardStrings = nextMovement.split("-");
        String cardDealtString = cardStrings[0];
        Card dealt = getCardFromList(pack.getCardList(), cardDealtString);
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
            return getCardFromList(hand.getCardList(), cardDiscardString);
        }
        else
        {
            return null;
        }
    }


    private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer, int nbSharedCards)
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
                Card card = getCardFromList(pack.getCardList(), initialCard);

                if (card != null)
                {
                    card.removeFromHand(false);
                    hands[i].insert(card, false);
                }
            }
        }

        for (int i = 0; i < nbPlayers; i++)
        {
            int cardsToDealt = nbCardsPerPlayer - hands[i].getNumberOfCards();
            for (int j = 0; j < cardsToDealt; j++)
            {
                if (pack.isEmpty()) return;
                Card dealt = randomCard(pack.getCardList());
                dealt.removeFromHand(false);
                hands[i].insert(dealt, false);
            }
        }
    }

    private void dealACardToHand(Hand hand)
    {
        if (pack.isEmpty()) return;
        Card dealt = randomCard(pack.getCardList());
        dealt.removeFromHand(false);
        hand.insert(dealt, true);
    }

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

    private void addEndOfRoundToLog()
    {
        logResult.append("Score:");
        for (int i = 0; i < scores.length; i++)
        {
            logResult.append(scores[i] + ",");
        }
        logResult.append("\n");
    }

    private void addEndOfGameToLog(List<Integer> winners)
    {
        logResult.append("EndGame:");
        for (int i = 0; i < scores.length; i++)
        {
            logResult.append(scores[i] + ",");
        }
        logResult.append("\n");
        logResult.append("Winners:" + String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList())));
    }

    private void playGame()
    {
        // End trump suit
        int winner = 0;
        int roundNumber = 1;
        for (int i = 0; i < nbPlayers; i++) updateScore(i);

        List<Card> cardsPlayed = new ArrayList<>();
        addRoundInfoToLog(roundNumber);

        int nextPlayer = 0;
        while (roundNumber <= 4)
        {
            selected = null;
            boolean finishedAuto = false;

            if (isAuto)
            {
                int nextPlayerAutoIndex = autoIndexHands[nextPlayer];
                List<String> nextPlayerMovement = playerAutoMovements.get(nextPlayer);
                String nextMovement = "";

                if (nextPlayerMovement.size() > nextPlayerAutoIndex)
                {
                    nextMovement = nextPlayerMovement.get(nextPlayerAutoIndex);
                    nextPlayerAutoIndex++;

                    autoIndexHands[nextPlayer] = nextPlayerAutoIndex;
                    Hand nextHand = hands[nextPlayer];

                    // Apply movement for player
                    selected = applyAutoMovement(nextHand, nextMovement);
                    delay(delayTime);
                    if (selected != null)
                    {
                        selected.removeFromHand(true);
                    }
                    else
                    {
                        selected = getRandomCard(hands[nextPlayer]);
                        selected.removeFromHand(true);
                    }
                }
                else
                {
                    finishedAuto = true;
                }
            }

            if (!isAuto || finishedAuto)
            {
                if (0 == nextPlayer)
                {
                    hands[0].setTouchEnabled(true);

                    setStatus("Player 0 is playing. Please double click on a card to discard");
                    selected = null;
                    dealACardToHand(hands[0]);
                    while (null == selected) delay(delayTime);
                    selected.removeFromHand(true);
                }
                else
                {
                    setStatusText("Player " + nextPlayer + " thinking...");
                    selected = getRandomCard(hands[nextPlayer]);
                    selected.removeFromHand(true);
                }
            }

            addCardPlayedToLog(nextPlayer, hands[nextPlayer].getCardList());
            if (selected != null)
            {
                cardsPlayed.add(selected);
                selected.setVerso(false);  // In case it is upside down
                delay(delayTime);
                // End Follow
            }

            scores[nextPlayer] = scoreForHiFive(nextPlayer);
            updateScore(nextPlayer);
            nextPlayer = (nextPlayer + 1) % nbPlayers;

            if (nextPlayer == 0)
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

    private void setupPlayerAutoMovements()
    {
        String player0AutoMovement = properties.getProperty("players.0.cardsPlayed");
        String player1AutoMovement = properties.getProperty("players.1.cardsPlayed");
        String player2AutoMovement = properties.getProperty("players.2.cardsPlayed");
        String player3AutoMovement = properties.getProperty("players.3.cardsPlayed");

        String[] playerMovements = new String[]{"", "", "", ""};
        if (player0AutoMovement != null)
        {
            playerMovements[0] = player0AutoMovement;
        }

        if (player1AutoMovement != null)
        {
            playerMovements[1] = player1AutoMovement;
        }

        if (player2AutoMovement != null)
        {
            playerMovements[2] = player2AutoMovement;
        }

        if (player3AutoMovement != null)
        {
            playerMovements[3] = player3AutoMovement;
        }

        for (int i = 0; i < playerMovements.length; i++)
        {
            String movementString = playerMovements[i];
            List<String> movements = Arrays.asList(movementString.split(","));
            playerAutoMovements.add(movements);
        }
    }

    public String runApp()
    {
        setTitle("LuckyThirteen (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initScores();
        initScore();
        setupPlayerAutoMovements();
        initGame();
        playGame();

        for (int i = 0; i < nbPlayers; i++) updateScore(i);
        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (scores[i] > maxScore) maxScore = scores[i];
        List<Integer> winners = new ArrayList<Integer>();
        for (int i = 0; i < nbPlayers; i++) if (scores[i] == maxScore) winners.add(i);
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

    public HiFiveOrigin(Properties properties)
    {
        super(700, 700, 30);
        this.properties = properties;
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime", "200"));
        delayTime = Integer.parseInt(properties.getProperty("delayTime", "50"));
    }

}