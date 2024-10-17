package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;

import java.util.*;

import static hifive.HiFive.FIVE_GOAL;

/**
 * CleverComputerPlayer class represents the logic of implementation of a clever computer player mode in the game
 *
 * @version 1.0
 * @since 2024-10-01
 * @author Miles Li, Skylar Khant, Ngoc Thanh Lam Nguyen
 */
public class CleverComputerPlayer extends ComputerPlayer
{
    /**
     * Constructor of Player
     *
     * @param id         : player id
     * @param deck       : deck of cards
     * @param properties : properties
     */
    private final double THREADSHOLD = 0.25;
    public CleverComputerPlayer(int id, Deck deck, Properties properties, HiFive game)
    {
        super(id, deck, properties, game);
    }

    /**
     * Main logic of clever computer player
     *
     * @return Card : card to play
     */
    @Override
    public Card play()
    {
        // Deal a card to the player's hand
        super.getGame().dealACardToHand(super.getHand());
        HiFive.delay(super.getGame().getThinkingTime());


        List<Card> handCards = super.getHand().getCardList();
        Card selectedCard = null;

        // Find the card that maximizes the score with 5, sum5, difference5 calculator
        selectedCard = findBestScoringCard(handCards);

        if (selectedCard != null)
        {
            // Play the selected card and refresh the game state
            selectedCard.removeFromHand(true);
            super.getGame().refresh();
            return selectedCard;
        }

        // Handle cases where no card was selected based on score
        selectedCard = selectCardBasedOnProbabilities(handCards);
        selectedCard.removeFromHand(true);
        System.out.println(selectedCard);

        return selectedCard;
    }

    @Override
    public Card discardCard()
    {
        Card selected = null;

        if (super.getGame().isAuto())
        {
            selected = autoPlay();
        }
        else
        {
            setFinishAuto(true);
        }

        if (!super.getGame().isAuto() || super.isFinishAuto()) // The main logic of Random Computer Player
        {
            super.getGame().setStatusText("Player " + super.getId() + " thinking...");
            selected = play();
        }

        HiFive.discardedCards.add(selected);
        return selected;
    }

    private Card findBestScoringCard(List<Card> handCards)
    {
        CalculatorFactory fc = CalculatorFactory.getFactoryCalculator();
        Card worstCard = null;
        int bestScore = 0;

        for (Card card : handCards)
        {
            // Simulate the play and calculate the score
            List<Card> tempHand = new ArrayList<>(handCards);
            tempHand.remove(card);
            int score = fc.getCompCalculator().calculateHighestScoreOnFive(tempHand);

            // Keep track of the card with the best score
            if (score > bestScore)
            {
                bestScore = score;
                worstCard = card;
            }
        }

        return worstCard;
    }

    private Card selectCardBasedOnProbabilities(List<Card> handCards)
    {
        int countPictureCard = 0;
        List<Card> potentialDiscards = new ArrayList<>();
        System.out.println(handCards + " hand cards\n");
        for (Card card : handCards)
        {
            Rank rank = (Rank) card.getRank();
            if (isPictureCard(rank))
            {
                countPictureCard++;
            }
            else
            {
                potentialDiscards = evaluateCardProbability(card, rank);
            }
        }

        // Select based on picture card count or probability
        if (countPictureCard == handCards.size())
        {
            return CardUtils.getCardWithLowestSuit(handCards);
        }
        else if (!potentialDiscards.isEmpty())
        {
            return CardUtils.getCardWithLowestSuit(potentialDiscards);
        }
        else
        {
            return CardUtils.getCardWithHighestRank(handCards);
        }
    }

    private boolean isPictureCard(Rank rank)
    {
        return rank == Rank.ACE || rank == Rank.JACK || rank == Rank.QUEEN || rank == Rank.KING;
    }

    private List<Card> evaluateCardProbability(Card card, Rank rank)
    {
        // Count ranks of discarded cards by their rank value
        Map<Integer, Integer> discardedCardsCount = CardUtils.getRankValueCountMap(HiFive.discardedCards);

        // Create a deck and get the count of cards in the original deck by rank
        Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
        Map<Integer, Integer> originalCardCount = CardUtils.getRankValueCountMap(deck.toHand().getCardList());
        List<Card> potentialDiscards = new ArrayList<>();
        int rankValue = rank.getRankCardValue();

        // Case when rank value is less than the goal (FIVE_GOAL)
        if (rankValue < FIVE_GOAL)
        {
            int sumComplement = FIVE_GOAL - rankValue;
            int difComplement = FIVE_GOAL + rankValue;

            // Calculate discard probability using the complement values
            double probability = CardUtils.calculateDiscardProbability(
                    discardedCardsCount.getOrDefault(sumComplement, 0)
                    + discardedCardsCount.getOrDefault(difComplement, 0),
                    originalCardCount.get(sumComplement) + originalCardCount.get(difComplement));

            // Add card to potential discards if probability exceeds the threshold
            if (probability >= THREADSHOLD)
            {
                potentialDiscards.add(card);
            }

        // Case when rank value is greater than the goal (FIVE_GOAL)
        }
        else if (rankValue > FIVE_GOAL)
        {
            int difComplement = rankValue - FIVE_GOAL; // Find the difference complement for the discard probability

            // Calculate discard probability based on the complement difference
            double probability = CardUtils.calculateDiscardProbability(
                    discardedCardsCount.getOrDefault(difComplement, 0),
                    originalCardCount.get(difComplement));

            // Add card to potential discards if probability exceeds the threshold
            if (probability >= THREADSHOLD)
            {
                potentialDiscards.add(card);
            }
        }

        // Return the list of cards likely to be discarded
        return potentialDiscards;
    }

}
