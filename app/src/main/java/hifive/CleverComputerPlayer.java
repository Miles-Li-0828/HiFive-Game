package hifive;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CleverComputerPlayer extends ComputerPlayer
{
    /**
     * Constructor of Player
     *
     * @param id         : player id
     * @param deck       : deck of cards
     * @param properties : properties
     */
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
        super.getGame().dealACardToHand(super.getHand());
        HiFive.delay(super.getGame().getThinkingTime());

        List<Card> handCards = super.getHand().getCardList(); // get hand cards
        Card selectedCard = null;
        int bestScore = -1;

        // iterate through hand cards
        for (Card card : handCards)
        {
            // Create a temporary hand to simulate playing the card, but remove the card for discarding
            List<Card> tempHand = new ArrayList<>(handCards);
            tempHand.remove(card);

            // Calculate the highest score for the current cards
            int score = calculateWorstScore(tempHand);

            // If the score is higher than the current best score, update the best score and selected card
            if (score > bestScore)
            {
                bestScore = score;
                selectedCard = card;
            }
        }

        // If the selected card is not null, remove it from the hand and return it
        if (selectedCard != null)
        {
            selectedCard.removeFromHand(true);
            super.getGame().refresh();
            return selectedCard;
        }

        return selectedCard;
    }

    /**
     * calculate the highest score for the current deck in hand
     * @param handCards : cards in hand
     * @return int score
     */
    private int calculateWorstScore(List<Card> handCards)
    {
       CompositeCalculator cc = super.getCompositeCalculator();
       return cc.calculateScore(handCards);
    }
}
